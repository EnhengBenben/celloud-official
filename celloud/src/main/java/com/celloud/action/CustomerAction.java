package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.model.mysql.User;
import com.celloud.service.CustomerService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataUtil;
import com.celloud.utils.PropertiesUtil;

@Controller
@RequestMapping("customer")
public class CustomerAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAction.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @ActionLog(value = "发送登录验证码", button = "发送验证码")
    @RequestMapping("sendCaptcha")
    @ResponseBody
    public String sendCapcha(String cellphone) {
        LOGGER.info("手机号码 {} 的用户获取验证码", cellphone);
        String captcha = DataUtil.getCapchaRandom();
        // String result = AliDayuUtils.sendCaptcha(cellphone, captcha);
        String result = "success";
        // 验证码已发送
        if (!result.equals("error")) {
            customerService.addOrUpdateUserCaptcha(cellphone, captcha);
            LOGGER.info("验证码 {} 发送成功", captcha);
            return "succuss";
        }
        LOGGER.info("验证码 {} 发送失败", captcha);
        return "error";
    }

    @ActionLog(value = "C端用户登录", button = "登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView clientLogin(String cellphone, String captcha, Model model) {
        File f = new File(PropertiesUtil.outputPath + cellphone + "/" + captcha);
        ModelAndView mv = new ModelAndView("client");
        if (f.exists() && new Date().getTime() - f.lastModified() < 1000 * 60 * 5) {
            Integer result = userService.checkAddClientUser(cellphone);
            if (result != 0) {
                Subject subject = SecurityUtils.getSubject();
                User user = userService.findByUsernameOrEmail("cel_" + cellphone.substring(3, cellphone.length()));
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), false);
                try {
                    subject.login(token);
                } catch (IncorrectCredentialsException | UnknownAccountException e) {
                    mv.addObject("info", "用户登录失败，用户名密码错误");
                    LOGGER.info("用户登录失败，用户名密码错误：phone={},captcha={}", cellphone, captcha);
                } catch (Exception e) {
                    mv.addObject("info", "用户登录失败，未知异常");
                    LOGGER.info("用户登录失败，未知异常：phone={},captcha={}", cellphone, captcha);
                }
                if (subject.isAuthenticated()) {
                    mv.setViewName("loading");
                    try {
                        FileUtils.forceDelete(new File(PropertiesUtil.outputPath + cellphone));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return mv;
                }
            }
        } else {
            mv.addObject("info", "验证码错误，请重新输入！");
        }
        return mv;
    }
}
