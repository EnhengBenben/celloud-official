package com.celloud.action;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.PrivateKey;
import com.celloud.model.PublicKey;
import com.celloud.model.mysql.User;
import com.celloud.service.ActionLogService;
import com.celloud.service.RSAKeyService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
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
    @Resource
    private ActionLogService logService;

    /**
     * 跳转到登录页面
     * 
     * @param request
     * @param response
     * @return
     */
    @ActionLog(value = "跳转到登录页面", button = "登录")
    @RequestMapping("login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        User user = new User();
        Subject subject = SecurityUtils.getSubject();
        Object isRem = subject.getSession().getAttribute("isRemembered");
        boolean isRemembered = isRem != null ? ((boolean) isRem) : subject.isRemembered();
        if (isRemembered) {
            User temp = userService.findByUsernameOrEmail(String.valueOf(subject.getPrincipal()));
            user.setUsername(temp.getUsername());
            user.setPassword(temp.getPassword());
        }
        return mv.addObject("checked", isRemembered).addObject("user", user)
                .addObject("publicKey", generatePublicKey(subject.getSession()))
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
    @ActionLog(value = "用户登录", button = "登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(User user, String kaptchaCode, String newPassword, boolean checked) {
        logger.info("用户正在登陆：" + user.getUsername());
        Subject subject = SecurityUtils.getSubject();
        String password = user.getPassword();
        user.setPassword("");
        Session session = subject.getSession();
        session.setAttribute("isRemembered", checked);
        PrivateKey privateKey = (PrivateKey) session.getAttribute(Constants.SESSION_RSA_PRIVATEKEY);
        ModelAndView mv = new ModelAndView("login").addObject("user", user).addObject("checked", subject.isRemembered())
                .addObject("publicKey", generatePublicKey(session))
                .addObject("showKaptchaCode", getFailedlogins() >= 3);
        if (!checkKaptcha(kaptchaCode, session)) {
            return mv.addObject("info", "验证码错误，请重新登录！");
        }
        if (newPassword == null || newPassword.trim().length() <= 0) {
            password = RSAUtil.decryptStringByJs(privateKey, password);
        } else {
            password = RSAUtil.decryptStringByJs(privateKey, newPassword);
            password = password == null ? "" : MD5Util.getMD5(password);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), password, checked);
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            String msg = "用户名或密码错误，请重新登录！";
            addFailedlogins();
            logger.warn("用户（{}）登录失败，用户名或密码错误！", user.getUsername());
            return mv.addObject("info", msg).addObject("showKaptchaCode", getFailedlogins() >= 3);
        } catch (Exception e) {
            logger.error("登录失败！", e);
            return mv.addObject("info", "登录失败！");
        }
        if (!subject.isAuthenticated()) {
            return mv.addObject("info", "登录失败！");
        }
        User loginUser = ConstantsData.getLoginUser();
        logger.info("用户({})登录成功！", loginUser.getUsername());
        logService.log("用户登录", "用户" + loginUser.getUsername() + "登录成功");
        session.removeAttribute(Constants.SESSION_RSA_PRIVATEKEY);
        session.removeAttribute(Constants.SESSION_FAILED_LOGIN_TIME);
        // 获取用户所属的大客户，决定是否有统计菜单
        Integer companyId = userService.getCompanyIdByUserId(loginUser.getUserId());
        session.setAttribute("companyId", companyId);
        mv.setViewName("loading");
        return mv;
    }

    /**
     * 获取用户登录的账号密码错误次数
     * 
     * @return
     */
    public int getFailedlogins() {
        Object failedlogins = SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_FAILED_LOGIN_TIME);
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
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_FAILED_LOGIN_TIME, (time + 1) + "");
    }

    /**
     * 校验验证码，在用户错误三次及以上时，需要校验验证码
     * 
     * @param kaptcha
     * @param session
     * @return
     */
    public boolean checkKaptcha(String kaptcha, Session session) {
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
     * 用户退出操作
     * 
     * @param request
     * @param response
     * @return
     */
    @ActionLog(value = "用户退出", button = "退出")
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = ConstantsData.getLoginUser();
        session.removeAttribute(Constants.SESSION_LOGIN_USER);
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        SecurityUtils.getSubject().logout();
        logger.info("用户({})主动退出", user == null ? "null..." : user.getUsername());
        return "redirect:login";
    }

    /**
     * 生成一个rsa公钥私钥对，将私钥存储到session，返回公钥
     * 
     * @param session
     * @return
     */
    private PublicKey generatePublicKey(Session session) {
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

}
