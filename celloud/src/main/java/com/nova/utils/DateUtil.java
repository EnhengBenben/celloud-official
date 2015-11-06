package com.nova.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DateUtil
 * @Description: 时间转化工具类
 * @author lin
 * @date 2012-5-2 上午12:02:55
 */
public class DateUtil {
	/**
	 * @Title:nowTimeToString
	 * @Description:将当前时间格式化成"yyyy-MM-dd HH:mm:ss"格式的字符串
	 * @Author: lin
	 * @return: String
	 */
	public static String nowCarefulTimeToString() {
		Date date = new Date();// 得到当前时间
		SimpleDateFormat simpleFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleFormat.format(date);// 将当前时间格式化成String并返回
	}

	public static String nowCarefulTimeToString(Date date) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleFormat.format(date);// 将当前时间格式化成String并返回
	}

	/**
	 * @Title:nowTimeToString
	 * @Description:将当前时间格式化成"yyyy-MM-dd"格式的字符串
	 * @Author: lin
	 * @return: String
	 */
	public static String nowTimeToString() {
		Date date = new Date();// 得到当前时间
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormat.format(date);// 将当前时间格式化成String并返回
	}

	/**
	 * 将当前时间格式化为“yyyyMMdd”格式的字符串
	 * 
	 * @return
	 */
	public static String getDateToString() {
		Date date = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
		return simpleFormat.format(date);
	}

	public static String getTimestamp() {
		return System.currentTimeMillis() + "";
	}
}