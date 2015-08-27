package com.nova.tools.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @Description:加密算法
 * @author lin
 * @date 2013-12-23 上午11:04:00
 */
public class Encrypt {
    private static Base64 base64 = new Base64();

    public static String encrypt(String context) {
	context = "nova" + context + "cloud";
	return new String(base64.encode(context.getBytes()));
    }

    public static String decrypt(String context) {
	String s = new String(base64.decode(context.getBytes()));
	return s.substring(4, s.length() - 5);
    }
}