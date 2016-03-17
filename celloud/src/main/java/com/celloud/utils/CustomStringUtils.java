package com.celloud.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 自定义的字符串处理类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月19日 下午12:58:02
 */
public class CustomStringUtils extends StringUtils {
    /**
     * 将一个字符串转化为一个6位长度的短字符串（不保证唯一性），区分大小写
     * 
     * @param longString
     * @param toLowerCase
     * @return
     */
    public static String[] shortString(String longString, boolean toLowerCase) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "nova" + longString + "celloud";
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        // 对传入网址进行 MD5 加密
        String hex = MD5Util.getMD5(key);

        String[] results = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 ,
            // 如果不用long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            results[i] = toLowerCase ? outChars.toLowerCase() : outChars;
        }
        return results;
    }

    /**
     * 将一个字符串转化为一个6位长度的短字符串（不保证唯一性）,自动将结果全部转化为小写
     * 
     * @param longString
     * @return
     */
    public static String[] shortString(String longString) {
        return shortString(longString, true);
    }

    /**
     * 通过文件名称获取Barcode
     * 
     * @param fileName
     * @return
     */
    public static String getBarcode(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] s = fileName.split("_");
        if (s.length > 2) {
            fileName = s[0] + "_" + s[1];
        }
        return fileName;
    }
    
    public static String htmlbr(String txt) {
		return txt == null ? null : txt.replace("\n", "<br/>");
	}
}
