package com.celloud.utils;

/**
 * 
 * @author lin
 * @date 2015-11-2 下午3:46:58
 * @description :自定义字符串工具类（叫 StringUtil 的实在太多了）
 */
public class CustomString {
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
}
