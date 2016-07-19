package com.celloud.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.NoticeConstants;
import com.celloud.constants.PayOrderState;
import com.celloud.constants.RechargeType;
import com.celloud.mapper.PayOrderMapper;
import com.celloud.mapper.RechargeAlipayMapper;
import com.celloud.message.MessageUtils;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.PayOrder;
import com.celloud.model.mysql.RechargeAlipay;
import com.celloud.pay.alipay.AlipayConfig;
import com.celloud.pay.alipay.AlipayNotify;
import com.celloud.pay.alipay.AlipaySubmit;
import com.celloud.service.PayService;
import com.celloud.service.RechargeService;

@Service("payServiceImpl")
public class PayServiceImpl implements PayService {
	private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
	@Resource
	private PayOrderMapper payOrderMapper;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private RechargeAlipayMapper rechargeAlipayMapper;
	@Resource
	private MessageCategoryUtils mcu;

	@Override
	public Map<String, String> createAlipayOrder(String money) {
		String tradeNo = "CelLoud-" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String time = new Long(System.currentTimeMillis()).toString();
		time = time.substring(time.length() - 5);
		tradeNo = tradeNo + time;
		String subject = "CelLoud平台账户充值";
		String body = "CelLoud平台账户充值【" + money + "】元";
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", AlipayConfig.service);
		params.put("partner", AlipayConfig.partner);
		params.put("seller_id", AlipayConfig.seller_id);
		params.put("_input_charset", AlipayConfig.input_charset);
		params.put("payment_type", AlipayConfig.payment_type);
		params.put("notify_url", AlipayConfig.notify_url);
		params.put("return_url", AlipayConfig.return_url);
		params.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		params.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		params.put("out_trade_no", tradeNo);
		params.put("subject", subject);
		params.put("total_fee", money);
		params.put("body", body);
		params = AlipaySubmit.buildRequestPara(params);
		PayOrder order = new PayOrder();
		order.setAmount(new BigDecimal(money));
		order.setCreateTime(new Date());
		order.setDescription(body);
		order.setSubject(subject);
		order.setTradeNo(tradeNo);
		order.setType(RechargeType.ALIPAY.type());
		order.setUserId(ConstantsData.getLoginUserId());
		order.setState(PayOrderState.UNPAID);
		payOrderMapper.insertSelective(order);
		return params;
	}

	@Override
	public RechargeAlipay verifyAlipay(HttpServletRequest request) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			logger.debug("+++++++++++\t" + name + "\t=\t" + valueStr);
			params.put(name, valueStr);
		}
		logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		BigDecimal amount = new BigDecimal(total_fee);
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		RechargeAlipay alipay = null;
		if (verify_result) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				PayOrder order = payOrderMapper.selectByTradeNo(out_trade_no);
				if (order.getState() == PayOrderState.UNPAID) {
					order.setState(PayOrderState.PAID);
					payOrderMapper.updateByPrimaryKey(order);
					alipay = new RechargeAlipay();
					alipay.setAliTradeNo(trade_no);
					alipay.setAmount(amount);
					alipay.setBuyerEmail(params.get("buyer_email"));
					alipay.setBuyerId(params.get("buyer_id"));
					alipay.setCreateTime(new Date());
					alipay.setDescription(params.get("body"));
					alipay.setSubject(params.get("subject"));
					alipay.setTradeNo(out_trade_no);
					Integer userId = order.getUserId();
					String username = order.getUsername();
					alipay.setUserId(userId);
					alipay.setUsername(username);
					rechargeAlipayMapper.insert(alipay);
					rechargeService.saveRecharge(amount, userId, RechargeType.ALIPAY, alipay.getId());
					//构造桌面消息
					MessageUtils mu = MessageUtils.get().on(Constants.MESSAGE_USER_CHANNEL)
							.send(NoticeConstants.createMessage("recharge", "充值成功", alipay.getDescription()));
					mcu.sendMessage(userId, MessageCategoryCode.BALANCES, null, null, mu);
				} else {
					alipay = rechargeAlipayMapper.selectByTradeNo(out_trade_no);
				}
			}

			//////////////////////////////////////////////////////////////////////////////////////////
		}
		return alipay;
	}

}
