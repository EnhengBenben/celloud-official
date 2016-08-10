package com.celloud.backstage.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName: DateUtil
 * @Description: 时间转化工具类
 * @author han
 * @date 2016年1月4日 上午10:30:32
 */
public class DateUtil {
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
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
	 * @date 2016年7月19日下午1:59:23
	 */
	public static String getDateToString(String format) {
		Date date = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		return simpleFormat.format(date);
	}

	/**
	 * 自定义格式化给定时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @author lin
	 * @date 2016年7月28日下午1:30:06
	 */
	public static String getDateToString(Date date, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		return simpleFormat.format(date);
	}

    /**
     * 秒转时:分:秒
     * 
     * @param second
     * @return
     */
    public static String secondToTime(Long second) {
        if (second == null || second <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        long h = 0;
        long d = 0;
        long s = 0;
        long temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        if (h < 10) {
            sb.append("0" + h + ":");
        } else {
            sb.append(h + ":");
        }
        if (d < 10) {
            sb.append("0" + d + ":");
        } else {
            sb.append(d + ":");
        }
        if (s < 10) {
            sb.append("0" + s);
        } else {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月10日下午4:12:58
     * @description 获取指定周的周几,第一天为周日
     * @param week
     *            1:本周 -1:向前推迟一周 2:下周
     * @param day
     *            周几(Calendar.MONDAY)
     * @return 日期的yyyy-MM-dd格式
     *
     */
    public static String getDay(int week, int day) {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        cal.add(Calendar.DATE, week * 7);
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午6:07:23
     * @description 获得当前日期与本周一相差的天数
     *
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午6:08:34
     * @description 获得当前周- 周一的日期
     *
     */
    public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午6:08:45
     * @description 获得当前周- 周日 的日期
     *
     */
    public static String getCurrentSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

}