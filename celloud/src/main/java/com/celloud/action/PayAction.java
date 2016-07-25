package com.celloud.action;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Recharge;
import com.celloud.model.mysql.RechargeAlipay;
import com.celloud.model.mysql.RechargeJdpay;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.PayService;
import com.celloud.service.RechargeService;
import com.celloud.service.UserService;

/**
 * 支付acion
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年6月27日 下午4:11:05
 */
@Controller
@RequestMapping("pay")
public class PayAction {
	@Resource
	private UserService userService;
	@Resource
	private PayService payService;
	@Resource
	private RechargeService rechargeService;

	@RequestMapping("recharge")
	public String recharge(Model model) {
		BigDecimal balance = userService.selectUserById(ConstantsData.getLoginUser().getUserId()).getBalances();
		if (balance == null) {
			balance = new BigDecimal(0.00);
		}
		model.addAttribute("balance", balance);
		return "expense/expense_recharge";
	}

	@RequestMapping("recharge/list")
	public String rechargeList(Page page, Model model) {
		PageList<Recharge> recharges = rechargeService.listRecharges(page);
		model.addAttribute("recharges", recharges);
		return "expense/expense_recharge_list";
	}

	@RequestMapping("recharge/alipay")
	public String alipay(String money, Model model) {
		model.addAttribute("params", payService.createAlipayOrder(money));
		return "pay/alipay";
	}

	@RequestMapping("alipay/return.html")
	public String alipayReturn(HttpServletRequest request, Model model) throws Exception {
		RechargeAlipay alipay = payService.verifyAlipay(request);
		boolean verify_result = (alipay != null);
		if (verify_result) {
			model.addAttribute("verify_result", verify_result);
			User user = userService.selectUserById(alipay.getUserId());
			model.addAttribute("username", user.getUsername());
			BigDecimal balance = user.getBalances();
			model.addAttribute("balance", balance);
			model.addAttribute("alipay", alipay);
		}
		return "pay/alipay_return";
	}

	@RequestMapping("alipay/notify.html")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean verify_result = payService.verifyAlipay(request) != null;
		response.getWriter().print(verify_result ? "success" : "fail");
	}

	@RequestMapping("recharge/jdpay")
	public String jdpay(String pay_type, String money, Model model) {
		model.addAttribute("params", payService.createJdpayOrder(pay_type, money));
		return "pay/jdpay";
	}

	@RequestMapping("jdpay/receive.html")
	public String jdpayReceive(HttpServletRequest request, Model model) {
		RechargeJdpay pay = payService.verifyJdpay(request);
		boolean verify_result = (pay != null);
		if (verify_result) {
			model.addAttribute("verify_result", verify_result);
			User user = userService.selectUserById(pay.getUserId());
			model.addAttribute("username", user.getUsername());
			BigDecimal balance = user.getBalances();
			model.addAttribute("balance", balance);
			model.addAttribute("alipay", pay);
		}
		return "pay/jdpay_receive";
	}

	@RequestMapping("jdpay/auto_receive.html")
	public void jdpayAutoReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean verify_result = payService.verifyJdpay(request) == null;
		response.getWriter().print(verify_result ? "ok" : "error");
	}

}
