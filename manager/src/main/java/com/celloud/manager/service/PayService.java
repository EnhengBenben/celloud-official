package com.celloud.manager.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.celloud.manager.model.RechargeAlipay;
import com.celloud.manager.model.RechargeJdpay;

public interface PayService {
	/**
	 * 创建支付宝支付的订单<br>
	 * 1、组装支付宝支付的参数列表并返回<br>
	 * 2、在系统内创建支付宝支付的订单并保存
	 * 
	 * @return
	 */
	public Map<String, String> createAlipayOrder(String money);

	/**
	 * 验证alipay是否支付成功
	 * 
	 * @param request
	 * @return
	 */
	public RechargeAlipay verifyAlipay(HttpServletRequest request) throws Exception;

	/**
	 * 创建京东网关支付的订单
	 * 
	 * @param bank
	 * @param money
	 * @return
	 */
	public Map<String, String> createJdpayOrder(String bank, String money);

	/**
	 * 验证京东网关支付的回调
	 * 
	 * @param request
	 * @return
	 */
	public RechargeJdpay verifyJdpay(HttpServletRequest request);

}
