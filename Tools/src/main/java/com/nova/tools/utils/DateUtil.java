package com.nova.tools.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 将当前时间格式化为：yyyy-MM-dd hh:mm:ss
     * 
     * @return
     */
    public static String formatNowDate() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	return sdf.format(new Date());
    }
}
