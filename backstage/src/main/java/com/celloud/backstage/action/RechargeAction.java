package com.celloud.backstage.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.model.RechargeTransfer;
import com.celloud.backstage.model.User;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.RechargeService;
import com.celloud.backstage.service.UserService;

/**
 * 账号充值
 * 
 * @author leamo
 * @date 2016年7月12日 上午10:17:17
 */
@Controller
@RequestMapping("recharge")
public class RechargeAction {
    Logger logger = LoggerFactory.getLogger(RechargeAction.class);
    @Resource
    private UserService userService;
    @Resource
    private RechargeService rechargeService;

    @RequestMapping("main")
    public ModelAndView userBalancesList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size, String condition) {
        ModelAndView mv = new ModelAndView("expense/recharge_main");
        Page page = new Page(currentPage, size);
        PageList<User> pageList = userService.getUserPageList(page, condition);
        mv.addObject("pageList", pageList);
        mv.addObject("condition", condition);
        return mv;
    }

    @RequestMapping("transfer")
    @ResponseBody
    public String transfer(HttpServletRequest request,
            RechargeTransfer transfer, String kaptchaCode) {
        HttpSession session = request.getSession();
        String kaptchaExpected = (String) session.getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        System.out
                .println("kaptchaCode--------------------------" + kaptchaCode);
        System.out.println(
                "kaptchaExpected:-----------------------" + kaptchaExpected);
        System.out.println(kaptchaExpected.equalsIgnoreCase(kaptchaCode));
        System.out.println(kaptchaExpected == null
                || !kaptchaExpected.equalsIgnoreCase(kaptchaCode));
        if (kaptchaExpected == null
                || !kaptchaExpected.equalsIgnoreCase(kaptchaCode)) {
            return "kaptcha error";
        }
        return rechargeService.createRechargeTransfer(transfer) > 0 ? "1" : "0";
    }
}
