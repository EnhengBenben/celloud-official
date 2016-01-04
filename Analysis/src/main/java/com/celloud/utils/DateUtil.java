package com.celloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat();

	/***
	 * 根据年月取下个月,end的时间大于start的时间,取start的下一个月 end 小于start取
	 * 
	 * @param month
	 *            yyyy-MM
	 * @return yyyy-MM
	 * @throws ParseException
	 */
	public static String nextMonth(String month, String start, String end) {
		Calendar cal = Calendar.getInstance();
		sdf.applyPattern("yyyy-MM");
		try {
			Date date = null;
			date = sdf.parse(month);
			cal.setTime(date);
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			if (endDate.after(startDate)) {
				cal.add(Calendar.MONTH, 1);
			} else {
				cal.add(Calendar.MONTH, -1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(cal.getTime());
	}

	/**
	 * 取下周的星期一
	 * 
	 * @param weekStr
	 *            yyyy-MM-dd
	 * @return yyyy-MM-dd
	 */

	public static String nextWeek(String weekStr) {
		sdf.applyPattern("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(weekStr);
			cal.setTime(date);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == 1) {
				cal.add(Calendar.DAY_OF_WEEK, 1);
			} else {
				cal.add(Calendar.DAY_OF_WEEK, 9 - dayOfWeek);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(cal.getTime());
	}

	public static Date getWeekEnd(Date start) {
		sdf.applyPattern("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_WEEK, 7 - dayOfWeek);
		return cal.getTime();
	}

	public static Date getLastMonday(Date start) {
		long l = start.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		int d = cal.get(Calendar.DAY_OF_WEEK);
		if (d == 0) {
			d = 6 + 7;
		} else {
			d = 7 + d;
		}
		return new Date(l - d * 24 * 60 * 60 * 1000);
	}

	public static Date getLastSunday(Date start) {
		long l = start.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		int d = cal.get(Calendar.DAY_OF_WEEK);
		if (d == 0) {
			d = 7;
		} else {
			d = 1 + d;
		}
		return new Date(l - d * 24 * 60 * 60 * 1000);
	}

	public static Date DAY_END_OF_MONTH() {
		sdf.applyPattern("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}
	
	public static Date DAY_START_OF_MONTH() {
		sdf.applyPattern("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	public static void main(String[] args) {
		String m = nextWeek("2015-11-01");
		System.out.println(m);
	}

}
