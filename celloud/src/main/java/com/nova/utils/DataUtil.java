package com.nova.utils;

import java.security.SecureRandom;

public class DataUtil {
    /**
     * 生产系统dateKey
     * 
     * @return
     */
    public static String getNewDataKey() {
	SecureRandom s = new SecureRandom();
	String timeStamp = DateUtil.getDateToString()
		+ String.format("%06d", s.nextInt(1000000));
	return timeStamp;
    }
}
