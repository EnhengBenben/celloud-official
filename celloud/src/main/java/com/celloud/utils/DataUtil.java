package com.celloud.utils;

import java.security.SecureRandom;
import java.util.HashMap;

import com.celloud.constants.SampleTypes;
import com.celloud.model.mysql.Metadata;

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
     * 生成样本订单编号
     * 
     * @param type
     * @param id
     * @return
     * @author leamo
     * @date 2016年10月25日 下午1:48:38
     */
    public static String getSampleOrderNo(int id) {
        SecureRandom s = new SecureRandom();
        String timeStamp = DateUtil.getDateToString("yyyyMM")
                + String.format("%06d", id)
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
		if (SampleTypes.typesMap == null) {
			HashMap<String, String> map = new HashMap<>();
			for (Metadata metadata : SampleTypes.types) {
				map.put(metadata.getName(), metadata.getSeq());
			}
			SampleTypes.typesMap = map;
		}
		String timeStamp = SampleTypes.typesMap.get(type)
                + DateUtil.getDateToString("yyyyMM")
                + String.format("%06d", id);
        return timeStamp;
    }
}
