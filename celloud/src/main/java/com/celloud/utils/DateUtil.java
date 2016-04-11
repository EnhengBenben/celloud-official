package com.celloud.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DateUtil
 * @Description: 时间转化工具类
 * @author han
 * @date 2016年1月4日 上午10:30:32
 */
public class DateUtil {
	/**
	 * 将当前时间格式化为“yyyyMMdd”格式的字符串
	 * 
	 * @return
	 */
	public static String getDateToString() {
		Date date = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMdd");
		return simpleFormat.format(date);
	}

	/**
	 * 自定义格式化当前时间
	 * 
	 * @param format
	 * @return
	 * @author lin
	 * @date 2016年4月8日下午3:18:34
	 */
	public static String getDateToString(String format) {
		Date date = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		return simpleFormat.format(date);
	}

	public static void main(String[] args) {
		System.out.println(getDateToString());
		System.out.println(getDateToString("yyyyMMdd"));
		System.out.println(getDateToString("yyMMdd"));
	}
}