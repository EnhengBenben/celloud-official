package com.celloud.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.celloud.constants.SampleTypes;
import com.celloud.model.mysql.Metadata;

public class DataUtil {
    private static SecureRandom s = new SecureRandom();
    /**
     * 生成dataKey
     * 
     * @param id
     *            数据编号
     * @return
     */
    public static String getNewDataKey(int id) {
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

    /**
     * 生成6位随机数验证码
     * 
     * @return
     * @author leamo
     * @date 2016年10月31日 下午5:37:15
     */
    public static String getCapchaRandom() {
        return String.format("%06d", s.nextInt(999999));
    }

    /**
     * 判断字符串是不是手机号
     * 
     * @param str
     * @return
     * @author leamo
     * @date 2017年2月20日 下午3:46:23
     */
    public static boolean checkCellphone(String str) {
        String regExp = "^1\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
