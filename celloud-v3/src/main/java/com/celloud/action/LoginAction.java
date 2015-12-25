package com.celloud.action;

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

import com.celloud.constants.Constants;
import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.RSAKey;
import com.celloud.model.User;
import com.celloud.service.RSAKeyService;
import com.celloud.service.UserService;
import com.celloud.utils.CookieUtils;
import com.celloud.utils.MD5Util;
import com.celloud.utils.RSAUtil;

/**
 * 登录action
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午1:32:22
 */
@Controller
public class LoginAction {
    Logger logger = LoggerFactory.getLogger(LoginAction.class);
    @Resource
    private UserService userService;
    @Resource
    private RSAKeyService rsaKeyService;

    /**
     * 跳转到登录页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
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
                mv.addObject("info", "记住密码已过期，请重新使用密码登录！");
            }
        } else {
            deleteCookies(request, response);
        }
        if (publicKey == null) {
            publicKey = generatePublicKey(request.getSession());
        }
        return mv.addObject("checked", checked).addObject("user", user).addObject("publicKey", publicKey);
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
        ModelAndView mv = new ModelAndView("login");
        HttpSession session = request.getSession();
        String kaptchaExpected = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        // 如果不需要记住密码，则直接删除用户cookie
        if (!checked) {
            deleteCookies(request, response);
            rsaKeyService.deleteByModulus(publicKey.getModulus());
        }
        // 验证码错误，直接返回到登录页面
        if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptchaCode)) {
            return mv.addObject("info", "验证码错误，请重新登录！").addObject("publicKey", generatePublicKey(session));
        }
        PrivateKey privateKey = null;
        RSAKey key = null;
        if (checked) {
            addCookies(request, response, user.getUsername(), user.getPassword(), publicKey.getModulus());
            key = rsaKeyService.getByModulus(publicKey.getModulus());
            if (key == null || key.isExpires()) {
                rsaKeyService.deleteByModulus(publicKey.getModulus());
                privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
            } else {
                privateKey = new PrivateKey(new BigInteger(key.getModulus(), 16),
                        new BigInteger(key.getPriExponent(), 16));
            }
        }
        String password = RSAUtil.decryptStringByJs(privateKey, user.getPassword());
        user.setPassword(MD5Util.getMD5(password));
        user = userService.login(user);
        if (user == null) {
            String msg = "用户名或密码错误，请重新登录！";
            logger.info(msg);
            return mv.addObject("info", msg).addObject("user", user).addObject("publicKey", generatePublicKey(session));
        }
        saveUserToSession(user, session);
        if (checked && key == null) {
            saveRSAKey(publicKey, privateKey, user);
        }
        logger.info("userId:{},username:{}", user.getUserId(), user.getUsername());
        mv.setViewName("loadIndex");
        return mv;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.SESSION_LOGIN_USER);
        @SuppressWarnings("unchecked")
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        deleteCookies(request, response);
        return "redirect:login";
    }

    /**
     * 将已登录的用户信息保存到session中
     * 
     * @param user
     */
    private void saveUserToSession(User user, HttpSession session) {
        session.setAttribute(Constants.SESSION_LOGIN_USER, user);
        // session.setAttribute("userName", user.getUsername());
        // session.setAttribute("userId", user.getUserId());
        // session.setAttribute("userRole", user.getRole());
        // session.setAttribute("userNav", user.getNavigation());
        // session.setAttribute("deptId", user.getDeptId());
        // session.setAttribute("email", user.getEmail());
        // session.setAttribute("companyId", user.getCompanyId());
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
        CookieUtils.setCookie(request, response, Constants.COOKIE_USERNAME, username,
                Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
        CookieUtils.setCookie(request, response, Constants.COOKIE_PASSWORD, password,
                Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
        CookieUtils.setCookie(request, response, Constants.COOKIE_MODULUS, modulus,
                Constants.COOKIE_MAX_AGE_DAY * 24 * 60 * 60);
    }

}
