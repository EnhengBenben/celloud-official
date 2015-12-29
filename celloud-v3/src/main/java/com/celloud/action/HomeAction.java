package com.celloud.action;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.email.EmailService;
import com.celloud.model.User;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;
import com.celloud.utils.ResetPwdUtils;

/**
 * 首页相关的action
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午1:40:56
 */
@Controller
public class HomeAction {
    @Resource
    private UserService userService;

    /**
     * 用户重置密码--跳转到重置密码页面
     * 
     * @return
     */
    @RequestMapping(value = "resetPassword/{username}/{randomCode}.html", method = RequestMethod.GET)
    public ModelAndView resetPassword(HttpSession session, @PathVariable String username,
            @PathVariable String randomCode) {
        // TODO 添加rsa加密
        ModelAndView mv = new ModelAndView("user/resetPassword");
        User user = userService.getUserByFindPwd(username, randomCode);
        if (user == null) {
            return mv.addObject("info", "找回密码的链接错误或已过期");
        }
        session.setAttribute(Constants.RESET_PASSWORD_USER_ID, user.getUserId());
        return mv.addObject("user", user).addObject("randomCode", randomCode);
    }

    /**
     * 用户重置密码--保存
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "resetPassword.html", method = RequestMethod.POST)
    public ModelAndView resetPassword(String username, String password, String randomCode, HttpSession session) {
        // TODO 添加rsa加密
        ModelAndView mv = new ModelAndView("user/resetPassword");
        String userId = String.valueOf(session.getAttribute(Constants.RESET_PASSWORD_USER_ID));
        if (userId == null) {
            return mv.addObject("info", "请求不合法").addObject("forbidden", "forbidden");
        }
        User user = userService.getUserByFindPwd(username, randomCode);
        if (!userId.equals(String.valueOf(user.getUserId()))) {
            return mv.addObject("info", "请求不合法").addObject("forbidden", "forbidden");
        }
        session.removeAttribute(Constants.RESET_PASSWORD_USER_ID);
        userService.updatePassword(user.getUserId(), password);
        userService.cleanFindPwd(user.getUserId(), new Date());
        return mv.addObject("info", "密码重置成功");
    }

    /**
     * 用户找回密码
     * 
     * @param email
     * @param kaptchaCode
     * @param session
     * @return
     */
    @RequestMapping(value = "findPassword.html", method = RequestMethod.POST)
    public ModelAndView findPassword(String email, String kaptchaCode, HttpSession session) {
        ModelAndView mv = new ModelAndView("user/forgot").addObject("sucess", "fail");
        String kaptchaExpected = String
                .valueOf(session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
        // 干掉session中的验证码，避免用户刷新页面重复提交
        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaExpected == null || !kaptchaExpected.equalsIgnoreCase(kaptchaCode)) {
            return mv.addObject("info", "验证码错误，请重新输入！");
        }
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return mv.addObject("info", "邮箱不存在，请重新输入！");
        }
        String randomCode = MD5Util.getMD5(String.valueOf(new Date().getTime()));
        userService.insertFindPwdInfo(user.getUserId(), randomCode);
        EmailService.send(user.getEmail(),
                ResetPwdUtils.content.replaceAll("username", user.getUsername()).replaceAll("url",
                        ResetPwdUtils.celloudPath.replaceAll("resetpwduname", user.getUsername())
                                .replaceAll("resetpwdcode", randomCode)),
                ResetPwdUtils.title, true);
        email = email.substring(0, 1) + "***" + email.substring(email.lastIndexOf("@"));
        String emailAddress = "http://mail." + email.substring(email.lastIndexOf("@") + 1);
        return mv.addObject("sucess", "ok").addObject("email", email).addObject("emailAddress", emailAddress);
    }

    @RequestMapping(value = "findPassword.html", method = RequestMethod.GET)
    public String findPassword() {
        return "redirect:forgot.html";
    }

    @RequestMapping("service.html")
    public String service() {
        return "service";
    }

    @RequestMapping("feedback.html")
    public String feedBack() {
        return "feedBack";
    }

    @RequestMapping("home.html")
    public String home() {
        return "home";
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("download.html")
    public String download() {
        return "download";
    }

    @RequestMapping("joinUs.html")
    public String joinUs() {
        return "joinUs";
    }

    @RequestMapping("forgot.html")
    public String forgot() {
        return "user/forgot";
    }
}
