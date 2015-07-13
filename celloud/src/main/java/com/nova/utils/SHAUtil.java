package com.nova.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.log4j.Logger;

public class SHAUtil {
	private static final Logger LOGGER = Logger.getLogger(SHAUtil.class);
	private static final String ALGORITHOM = "SHA";
	// 随机数种子
	private static char x36s[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static String encrypt(String source){
		try {
			MessageDigest sha = MessageDigest.getInstance(ALGORITHOM);
			byte[] srcBytes = source.getBytes();
			//更新摘要
			sha.update(srcBytes);
			byte[] resultBytes = sha.digest();
			StringBuffer sb = new StringBuffer();
			for(byte b : resultBytes){
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		} 
		return null;
	}
	
	public static String newId() {
		int len = 10;
		char chs[] = new char[len];
		/**
		 * 生成前3位随机字符(以系统时间为随机池, 以36位数字+英文字母为随机种子)
		 */
		long v = (System.currentTimeMillis() - 936748800000L) >> 1; // 1999-9-9
																	// 开始，起码要横跨102年:)
		for (int i = 2; i >= 0; i--) {
			chs[i] = x36s[(int) (v % 36)];
			v = v / 36;
		}
		/**
		 * 生成后5位随机字符(以UUID为随机池, 以36位数字+英文字母为随机种子)
		 */
		UUID u = UUID.randomUUID();
		v = u.getLeastSignificantBits() ^ u.getMostSignificantBits();
		if (v < 0) {
			v = -v;
		}
		for (int i = 3; i < len; i++) {
			chs[i] = x36s[(int) (v % 36)];
			v = v / 36;
		}

		return new String(chs);
	}
	
	public static void main(String[] args) {
		String source = "zl";
		String result = encrypt(source);
		System.err.println(result);
	}
}
