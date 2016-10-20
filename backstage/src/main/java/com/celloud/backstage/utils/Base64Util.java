package com.celloud.backstage.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    public final static String ENCODING = "UTF-8";
    private static Base64 base64 = new Base64();

    // 解密
    public static String decode(String data)
	    throws UnsupportedEncodingException {
	byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
	return new String(b, ENCODING);
    }

    /**
     * 解密
     * 
     * @param context
     * @return
     */
    public static String decrypt(String context) {
	String s = new String(base64.decode(context.getBytes()));
	return s.substring(4, s.length() - 5);
    }

    /**
     * 加密
     * 
     * @param context
     * @return
     */
    public static String encrypt(String context) {
	context = "nova" + context + "cloud";
	return new String(base64.encode(context.getBytes()));
    }
}