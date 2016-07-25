package com.celloud.pay.jdpay;

public class JdpayConfig {
	/**
	 * 商户号
	 */
	public static final String v_mid = "110218499002";
	/**
	 * 商户自定义返回接收支付结果的页面
	 */
	public static final String v_url = "https://www.genecode.cn/pay/jdpay/receive.html";
	/**
	 * 密钥
	 */
	public static final String key = "text";
	/**
	 * 服务器异步通知的接收地址
	 */
	public static final String remark2 = "[url:=https://www.genecode.cn/pay/jdpay/auto_receive.html]";
}
