package com.celloud.backstage.utils;

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

}