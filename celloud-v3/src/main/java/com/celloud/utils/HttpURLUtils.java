package com.celloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * HTTP 工具类
 * 
 * @author lin
 * @date 2016-1-10 下午11:14:55
 */
public class HttpURLUtils {
    private static Logger log = Logger.getLogger(HttpURLUtils.class);

    /**
     * 执行HTTP请求并获取返回值
     * 
     * @param url
     * @return
     * @date 2016-1-10 下午11:14:36
     */
    public static String getHTTPResult(String url) {
        log.info("url:" + url);
        HttpURLConnection conn = null;
        BufferedReader br = null;
        String result = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setDoOutput(true);
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(240000);
            conn.connect();

            br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            StringBuffer buff = new StringBuffer();
            String lineStr = null;
            while ((lineStr = br.readLine()) != null) {
                buff.append(lineStr);
            }
            result = buff.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
}
