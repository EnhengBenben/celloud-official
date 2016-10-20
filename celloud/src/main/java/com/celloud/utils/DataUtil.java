package com.celloud.utils;

import java.security.SecureRandom;

public class DataUtil {
    /**
     * 生成dataKey
     * 
     * @param id
     *            数据编号
     * @return
     */
    public static String getNewDataKey(int id) {
        SecureRandom s = new SecureRandom();
        String timeStamp = DateUtil.getDateToString()
                + String.format("%06d", id) + ""
                + String.format("%02d", s.nextInt(99));
        return timeStamp;
    }

}
