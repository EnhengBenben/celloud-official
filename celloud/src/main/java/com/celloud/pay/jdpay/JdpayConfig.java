package com.celloud.pay.jdpay;

public class JdpayConfig {
	/**
	 * 商户号
	 */
	public static final String v_mid = "110218499";
	/**
	 * 商户自定义返回接收支付结果的页面
	 */
	public static final String v_url = "http://localhost/chinabank/Receive.jsp";
	/**
	 * 密钥
	 */
	public static final String key = "text";
	/**
	 * 服务器异步通知的接收地址
	 */
	public static final String remark2 = "[url:=http://domain/chinabank/AutoReceive.asp]";
}
