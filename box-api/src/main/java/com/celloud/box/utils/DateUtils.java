package com.celloud.box.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String formartToday(String pattern) {
		return formart(new Date(), pattern);
	}

	public static String formart(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date parse(String content, String pattern) {
		Date result = null;
		try {
			result = new SimpleDateFormat(pattern).parse(content);
		} catch (ParseException e) {
		}
		return result;
	}
}
