package com.celloud.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.model.ActionLog;
import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;

import ua_parser.Client;
import ua_parser.Parser;

/**
 * 用户请求属性帮助类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月29日 下午2:16:34
 */
public class UserAgentUtil {
    private static Logger logger = LoggerFactory.getLogger(UserAgentUtil.class);
    /**
     * uaparser配置文件地址
     */
    private static final String REGEXES_PATH = "regexes.yaml";
    /**
     * 根据ip获取地址插件的配置文件
     */
    private static final String QQWRY_PATH = "qqwry.dat";
    private static Parser uaParser = null;
    private static QQWry qqwry = null;

    /**
     * 初始化
     * 
     * @return
     */
    public static boolean init() {
        try {
            if (uaParser == null) {
                uaParser = new Parser(UserAgentUtil.class.getClassLoader().getResourceAsStream(REGEXES_PATH));
            }
            if (qqwry == null) {
                InputStream is = QQWry.class.getClassLoader().getResourceAsStream(QQWRY_PATH);
                File file = new File(QQWRY_PATH);
                if (!file.exists()) {
                    FileUtils.copyInputStreamToFile(is, file);
                }
                byte[] data = FileUtils.readFileToByteArray(file);
                qqwry = new QQWry(data);
            }
        } catch (IOException e) {
            logger.error("初始化ActionLogUtil失败！", e);
            return false;
        }
        return true;
    }

    /**
     * 从userAgent中获取用户的浏览器、操作系统等信息，并组装成actionLog
     * 
     * @param request
     * @return
     */
    public static ActionLog getActionLog(HttpServletRequest request) {
        if (uaParser == null && !init()) {
            return null;
        }
        String userAgent = request.getHeader("user-agent");
        ActionLog log = new ActionLog();
        Client c = uaParser.parse(userAgent);
        log.setBrowser(c.userAgent.family);
        log.setBrowserVersion(c.userAgent.major + "." + c.userAgent.minor);
        log.setOs(c.os.family);
        log.setOsVersion(c.os.major + "." + c.os.minor);
        String ip = getIp(request);
        log.setIp(ip);
        log.setAddress(getAddreeByIp(ip));
        return log;
    }

    /**
     * 根据输入的ip获取地址
     * 
     * @param ip
     * @return
     */
    public static String getAddreeByIp(String ip) {
        if (qqwry == null && !init()) {
            return null;
        }
        String result = null;
        try {
            IPZone ipzone = qqwry.findIP(ip);
            result = ipzone.getMainInfo() + "-" + ipzone.getSubInfo();
        } catch (Exception e) {
            logger.warn("不能识别的ip地址：{}", ip);
        }
        return result;
    }

    /**
     * 获取用户的真实地址
     * 
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
