package com.celloud.pay.jdpay;

import java.util.Properties;

import com.celloud.constants.ConstantsData;

public class JdpayConfig {
	private static final String JDPAY_PROPS = "jdpay.properties";
	private static Properties props = ConstantsData.loadProperties(JDPAY_PROPS);
	/**
	 * 商户号
	 */
	public static String v_mid = props.getProperty("v_mid");
	/**
	 * 商户自定义返回接收支付结果的页面
	 */
	public static String v_url = props.getProperty("receive_url");
	/**
	 * 密钥
	 */
	public static String key = props.getProperty("key");
	/**
	 * 服务器异步通知的接收地址
	 */
	public static String remark2 = "[url:=" + props.getProperty("auto_receive_url") + "]";

	public static void load() {
		Properties props = ConstantsData.loadProperties(JDPAY_PROPS);
		v_mid = props.getProperty("v_mid");
		v_url = props.getProperty("receive_url");
		key = props.getProperty("key");
		remark2 = "[url:=" + props.getProperty("auto_receive_url") + "]";
	}
}
