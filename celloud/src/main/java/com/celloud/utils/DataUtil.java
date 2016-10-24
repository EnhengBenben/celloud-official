package com.celloud.utils;

import java.security.SecureRandom;

import com.celloud.constants.SampleTypes;

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

    /**
     * 生成实验样本编号
     * 
     * @param type
     * @return
     * @author leamo
     * @date 2016年10月24日 下午2:30:32
     */
    public static String getExperSampleNo(String type, int id) {
        SecureRandom s = new SecureRandom();
        System.out.println(SampleTypes.types);
        System.out.println(type);
        String timeStamp = SampleTypes.types.get(type)
                + DateUtil.getDateToString("yyyyMM") + String.format("%06d", id)
                + "" + String.format("%02d", s.nextInt(99));
        return timeStamp;
    }
}
