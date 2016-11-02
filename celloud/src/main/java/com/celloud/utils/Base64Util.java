package com.celloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

public class Base64Util {
    public final static String ENCODING = "UTF-8";
    private static Base64 base64 = new Base64();
    private static Map<String, String> mimeTypeMap = new HashMap<String, String>();

    static {
        mimeTypeMap.put(MediaType.IMAGE_PNG_VALUE, "png");
        mimeTypeMap.put(MediaType.IMAGE_JPEG_VALUE, "jpg");
    }

    // 解密
    public static String decode(String data)
	    throws UnsupportedEncodingException {
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(b, ENCODING);
    }

    /**
     * 解密
     * 
     * @param context
     * @return
     */
    public static String decrypt(String context) {
        String s = new String(base64.decode(context.getBytes()));
        return s.substring(4, s.length() - 5);
    }

    /**
     * 加密
     * 
     * @param context
     * @return
     */
    public static String encrypt(String context) {
        context = "nova" + context + "cloud";
        return new String(base64.encode(context.getBytes()));
    }

    // base64字符串转化成图片
    /**
     * 
     * @description 根据图片的base64字符串转换成图片并存储
     * @author miaoqi
     * @date 2016年11月2日上午10:25:28
     *
     * @param imgStr
     *            base64字符串
     * @param filePath
     *            不带后缀名的文件路径
     * @return
     */
    public static String strToImage(String imgStr, String filePath) {
        try {
            // Base64解码
            String[] baseArr = StringUtils.split(imgStr, ";");
            if (baseArr == null) {
                return null;
            }
            String type = mimeTypeMap.get(StringUtils.split(baseArr[0], ":")[1]);
            byte[] b = Base64.decodeBase64(StringUtils.split(baseArr[1], ",")[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成图片
            File target = new File(filePath + "." + type);
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(target);
            out.write(b);
            out.flush();
            out.close();
            return target.getName();
        } catch (Exception e) {
            return null;
        }
    }

    // 图片转化成base64字符串
    public static String imageToStr(String filepath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            File file = new File(filepath);
            String type = file.getName().split("\\.")[1];
            String mimeType = null;
            for (Map.Entry<String, String> entry : mimeTypeMap.entrySet()) {
                if (entry.getValue().equals(type)) {
                    mimeType = entry.getKey();
                }
            }
            in = new FileInputStream(filepath);
            data = new byte[in.available()];
            in.read(data);

            // 对字节数组Base64编码
            return "data:" + mimeType + ";" + "base64," + Base64.encodeBase64String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}