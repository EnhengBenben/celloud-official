package com.celloud.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.alidayu.AlidayuConfig;
import com.celloud.model.mongo.UserCaptcha;
import com.celloud.model.mysql.User;
import com.celloud.service.CustomerService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DataUtil;

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
        // 获取验证码
        String captcha = DataUtil.getCapchaRandom();
        // 新增或更新验证码
        Boolean flag = customerService.addOrUpdateUserCaptcha(cellphone, captcha);
        if (flag) {
            LOGGER.info("验证码 {} 发送成功", captcha);
            return "success";
        }
        LOGGER.info("验证码 {} 发送失败", captcha);
        return "error";
    }

    @ActionLog(value = "C端用户登录", button = "登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(String cellphone, String captcha) {
        ModelAndView mv = new ModelAndView("client");
        // 1. 根据手机号从mongo中查询用户的验证码信息
        UserCaptcha userCaptcha = customerService.getUserCaptchaByCellphone(cellphone);
        if (userCaptcha != null) {
            // 2. 获取创建时间
            DateTime createDate = new DateTime(userCaptcha.getCreateDate());
            // 3. 创建时间 + 1分钟 大于当前时间, 代表没有过期, 并且验证码相等
            if (createDate.plusMinutes(AlidayuConfig.captcha_expire_time).isAfterNow()
                    && userCaptcha.getCaptcha().equals(captcha)) {
                // 4. 执行查询或插入操作
                Integer result = userService.checkAddClientUser(cellphone);
                // 5. 执行登录功能
                if (result != 0) {
                    Subject subject = SecurityUtils.getSubject();
                    User user = userService.findByUsernameOrEmail("cel_" + cellphone.substring(3, cellphone.length()));
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(),
                            false);
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
                        // 清除UserCaptcha信息
                        customerService.removeUserCaptchaByCellphone(cellphone);
                        mv.setViewName("loading");
                        return mv;
                    }
                }
            }
        }
        mv.addObject("info", "验证码错误，请重新输入！");
        return mv;
    }
}
