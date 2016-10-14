package com.celloud.box.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String formartToday(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
}
