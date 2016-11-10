package com.celloud.utils;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;

public class APIUtils {

	/**
	 * 24位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:47:03
	 */
	private static String getObjectId() {
		return new ObjectId().toString();
	}

	/**
	 * 32位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:47:14
	 */
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 32位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:48:48
	 */
	public static String getCelLoudId() {
		return getUUID();
	}

	/**
	 * 40位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:48:56
	 */
	public static String getCelLoudSecret() {
		return DigestUtils.sha1Hex(DigestUtils.sha512Hex(getUUID()));
	}

	/**
	 * 64位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:49:03
	 */
	public static String getToken() {
		String base = getUUID() + getCelLoudSecret();
		return DigestUtils.sha256Hex(DigestUtils.sha1Hex(base));
	}

	/**
	 * 40位
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:51:14
	 */
	public static String getRefreshToken() {
		String base = getObjectId() + getCelLoudSecret();
		return DigestUtils.sha1Hex(MD5Util.getMD5(base));
	}
}
