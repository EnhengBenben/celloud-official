package com.nova.tools.utils;

import java.util.Calendar;

public class StringUtils {
    /**
     * 获取当前的时间戳，返回14位数字
     * 
     * @return
     */
    public static String getTimestamp() {

	String timestamp = "";
	Calendar c = Calendar.getInstance();

	int yyyy = c.get(Calendar.YEAR);
	int mm = c.get(Calendar.MONTH) + 1;
	int dd = c.get(Calendar.DAY_OF_MONTH);
	int hh = c.get(Calendar.HOUR) + 8;
	int MM = c.get(Calendar.MINUTE);
	int SS = c.get(Calendar.SECOND);

	String sMonth = mm < 10 ? "0" + mm : "" + mm;
	String sDay = dd < 10 ? "0" + dd : "" + dd;
	String sHour = hh < 10 ? "0" + hh : "" + hh;
	String sMinute = MM < 10 ? "0" + MM : "" + MM;
	String sSecond = SS < 10 ? "0" + SS : "" + SS;

	timestamp = yyyy + sMonth + sDay + sHour + sMinute + sSecond;

	return timestamp;
    }
}