package com.celloud.manager.action;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.Constants;
import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.model.PrivateKey;
import com.celloud.manager.model.PublicKey;
import com.celloud.manager.model.RSAKey;
import com.celloud.manager.model.User;
import com.celloud.manager.service.ActionLogService;
import com.celloud.manager.service.RSAKeyService;
import com.celloud.manager.service.UserService;
import com.celloud.manager.utils.CookieUtils;
import com.celloud.manager.utils.MD5Util;
import com.celloud.manager.utils.RSAUtil;
@Controller
public class LoginAction {
    Logger logger = LoggerFactory.getLogger(LoginAction.class);
    @Resource
    private RSAKeyService rsaKeyService;
    @Resource
    private UserService userService;
    @Resource
    private ActionLogService logService;
    
    /**
     * 跳转到登录页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("login");
        User user = new User();
        String username = CookieUtils.getCookieValue(request, Constants.COOKIE_USERNAME);
        String password = CookieUtils.getCookieValue(request, Constants.COOKIE_PASSWORD);
        String modulus = CookieUtils.getCookieValue(request, Constants.COOKIE_MODULUS);
        boolean checked = modulus != null;
        PublicKey publicKey = null;
        if (checked) {
            RSAKey key = rsaKeyService.getByModulus(modulus);
            if (key != null && !key.isExpires()) {
                publicKey = new PublicKey();
                publicKey.setExponent(key.getPubExponent());
                publicKey.setModulus(key.getModulus());
                user.setUsername(username);
                user.setPassword(password);
            } else {
                rsaKeyService.deleteByModulus(modulus);
                deleteCookies(request, response);
                mv.addObject("info", "记住密码已过期，请重新使用密码登录！");
            }
        } else {
            deleteCookies(request, response);
        }
        if (publicKey == null) {
            publicKey = generatePublicKey(request.getSession());
        }
        return mv.addObject("checked", checked).addObject("user", user).addObject("publicKey", publicKey)
                .addObject("showKaptchaCode", getFailedlogins() >= 3);
    }
    
    /**
     * 用户登录验证
     * 
     * @param model
     * @param user
     * @param kaptchaCode
     * @param publicKey
     * @param checked
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Model model, User user, String kaptchaCode, PublicKey publicKey, boolean checked,
            HttpServletRequest request, HttpServletResponse response) {
        logger.info("用户正在登陆：" + user.getUsername());
        ModelAndView mv = new ModelAndView("login").addObject("checked",
                CookieUtils.getCookieValue(request, Constants.COOKIE_MODULUS) != null && checked)
                .addObject("showKaptchaCode", getFailedlogins() >= 3);
        HttpSession session = request.getSession();
        PrivateKey privateKey = null;
        RSAKey key = null;
        String modulus = CookieUtils.getCookieValue(request, Constants.COOKIE_MODULUS);
        if (!checked) {
            deleteCookies(request, response);
        }
        // 验证码错误，直接返回到登录页面
        if (!checkKaptcha(kaptchaCode, session)) {
            user.setPassword("");
            return mv.addObject("info", "验证码错误，请重新登录！").addObject("publicKey", generatePublicKey(session));
        }
        // 如果cookie中存在公钥且和前台传过来的一致，则从数据库加载私钥，不管是否记住密码
        if (modulus != null && modulus.equals(publicKey.getModulus())) {
            key = rsaKeyService.getByModulus(publicKey.getModulus());
            privateKey = new PrivateKey(new BigInteger(key.getModulus(), 16), new BigInteger(key.getPriExponent(), 16));
        } else {
            privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
        }
        if (checked) {
            addCookies(request, response, user.getUsername(), user.getPassword(), publicKey.getModulus());
        } else if (key != null && key.isExpires()) {
            rsaKeyService.deleteByModulus(publicKey.getModulus());
        }
        String password = RSAUtil.decryptStringByJs(privateKey, user.getPassword());
        user.setPassword(MD5Util.getMD5(password));
        User loginUser = userService.login(user);
        if (loginUser == null) {
            String msg = "用户名或密码错误，请重新登录！";
            addFailedlogins();
            logger.warn("用户（{}）登录失败，用户名或密码错误！", user.getUsername());
            user.setPassword("");
            return mv.addObject("info", msg).addObject("user", user).addObject("publicKey", generatePublicKey(session))
                    .addObject("showKaptchaCode", getFailedlogins() >= 3);
        }
        logger.info("用户({})登录成功！", loginUser.getUsername());
        saveUserToSession(loginUser, session);
        logService.log("用户登录", "用户" + loginUser.getUsername() + "登录成功");
        if (checked && key == null) {
            saveRSAKey(publicKey, privateKey, loginUser);
        }
        session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
        mv.setViewName("loading");
        return mv;
    }
    
    /**
     * 校验验证码，在用户错误三次及以上时，需要校验验证码
     * 
     * @param kaptcha
     * @param session
     * @return
     */
    public boolean checkKaptcha(String kaptcha, HttpSession session) {
        int time = getFailedlogins();
        if (time < 3) {
            return true;
        }
        String kaptchaExpected = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        // 验证码错误，直接返回到登录页面
        if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptcha)) {
            logger.info("用户登陆验证码错误：param : {} \t session : {}", kaptcha, kaptchaExpected);
            return false;
        }
        return true;
    }

    /**
     * 获取用户登录的账号密码错误次数
     * 
     * @return
     */
    public int getFailedlogins() {
        Object failedlogins = ConstantsData.getSession().getAttribute(Constants.SESSION_FAILED_LOGIN_TIME);
        int time = 0;
        try {
            time = Integer.parseInt((String) failedlogins);
        } catch (Exception e) {
        }
        return time;
    }

    /**
     * 增加用户登录的账号密码错误次数
     */
    public void addFailedlogins() {
        int time = getFailedlogins();
        ConstantsData.getSession().setAttribute(Constants.SESSION_FAILED_LOGIN_TIME, (time + 1) + "");
    }

    /**
     * 用户退出操作
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = ConstantsData.getLoginUser();
        session.removeAttribute(Constants.SESSION_LOGIN_USER);
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        deleteCookies(request, response);
        logger.info("用户({})主动退出", user == null ? "null..." : user.getUsername());
        return "redirect:login";
    }

    /**
     * 删除记住密码对应的cookie
     * 
     * @param request
     * @param response
     */
    private void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, Constants.COOKIE_USERNAME);
        CookieUtils.deleteCookie(request, response, Constants.COOKIE_PASSWORD);
        CookieUtils.deleteCookie(request, response, Constants.COOKIE_MODULUS);
    }
    /**
     * 生成一个rsa公钥私钥对，将私钥存储到session，返回公钥
     * 
     * @param session
     * @return
     */
    private PublicKey generatePublicKey(HttpSession session) {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        PrivateKey privateKey = new PrivateKey(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
        session.setAttribute(Constants.SESSION_RSA_PRIVATEKEY, privateKey);
        PublicKey publicKey = new PublicKey();
        publicKey.setModulus(rsaPublicKey.getModulus().toString(16));
        publicKey.setExponent(rsaPublicKey.getPublicExponent().toString(16));
        return publicKey;
    }
    
    /**
     * 添加记住密码对应的cookie
     * 
     * @param request
     * @param response
     * @param username
     * @param password
     * @param modulus
     */
    private void addCookies(HttpServletRequest request, HttpServletResponse response, String username, String password,
            String modulus) {
        // cookie不为空时才会重建cookie，否则会将cookie的过期时间重置
        if (CookieUtils.getCookie(request, Constants.COOKIE_USERNAME) == null) {
            CookieUtils.setCookie(request, response, Constants.COOKIE_USERNAME, username,
                    Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
        }
        if (CookieUtils.getCookie(request, Constants.COOKIE_PASSWORD) == null) {
            CookieUtils.setCookie(request, response, Constants.COOKIE_PASSWORD, password,
                    Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
        }
        if (CookieUtils.getCookie(request, Constants.COOKIE_MODULUS) == null) {
            CookieUtils.setCookie(request, response, Constants.COOKIE_MODULUS, modulus,
                    Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
        }
    }
    
    /**
     * 将已登录的用户信息保存到session中
     * 
     * @param user
     */
    private void saveUserToSession(User user, HttpSession session) {
        session.setAttribute(Constants.SESSION_LOGIN_USER, user);
    }
    /**
     * 创建一个rsaKey
     * 
     * @param publicKey
     * @param privateKey
     * @param user
     */
    private void saveRSAKey(PublicKey publicKey, PrivateKey privateKey, User user) {
        RSAKey key = new RSAKey();
        key.setCreateTime(new Date());
        key.setModulus(privateKey.getModulus().toString(16));
        key.setPriExponent(privateKey.getPrivateExponent().toString(16));
        key.setPubExponent(publicKey.getExponent());
        key.setUserId(user.getUserId());
        key.setState(0);
        rsaKeyService.insert(key);
    }

}
