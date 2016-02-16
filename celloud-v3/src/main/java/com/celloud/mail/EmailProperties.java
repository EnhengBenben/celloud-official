package com.celloud.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮箱功能的配置
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月21日 下午1:30:14
 */
public final class EmailProperties {
    private static Logger logger = LoggerFactory.getLogger(EmailProperties.class);
    private static Properties properties;
    private static boolean isInited = false;
    /**
     * 配置文件地址
     */
    private static final String PROPERTY_PATH = "email.properties";
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 用户名
     */
    public static String username;
    /**
     * 密码
     */
    public static String password;
    /**
     * smtp
     */
    public static String smtp;
    /**
     * 默认标题
     */
    public static String defaultTitle;
    /**
     * 邮箱名称
     */
    public static String emailName;
    /**
     * 系统异常信息的邮件标题
     */
    public static String errorTitle;
    /**
     * 工单提示信息的邮件标题
     */
    public static String feedbackTitle;
    /**
     * 系统异常信息的邮件接收者
     */
    public static String[] errorMailTo;
    /**
     * 工单提示信息的邮件接收者
     */
    public static String[] feedbackMailTo;

    /**
     * 加载配置文件(不会重新加载)
     * 
     * @return
     */
    public static boolean load() {
        return load(false);
    }

    /**
     * 加载配置文件(可以重新加载)
     * 
     * @param reload
     * @return
     */
    public static boolean load(boolean reload) {
        if (!reload && isInited) {
            return isInited;
        }
        properties = new Properties();
        String path = EmailProperties.class.getClassLoader().getResource("") + PROPERTY_PATH;
        path = path.replace("file:", "");
        File ef = new File(path);
        if (!ef.exists()) {
            logger.warn("email配置文件不存在：{}" + PROPERTY_PATH);
            return false;
        }
        try {
            // 获取jar包外的资源文件
            InputStream is = new FileInputStream(ef);
            properties.load(is);
        } catch (IOException e) {
            logger.error("获取email.properties文件信息失败", e);
            return false;
        }
        username = getProperty("mail.username");
        password = getProperty("mail.password");
        smtp = getProperty("mail.smtp");
        defaultTitle = getProperty("mail.defaultTitle");
        emailName = getProperty("mail.emailName");
        errorTitle = getProperty("mail.errorTitle");
        feedbackTitle = getProperty("mail.feedbackTitle");
        emailName = getProperty("mail.emailName");
        errorMailTo = getMails("mail.errorMailTo");
        feedbackMailTo = getMails("mail.feedbackMailTo");
        return true;
    }

    /**
     * 获取property,并且重新编码
     * 
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        String value = properties.getProperty(name);
        try {
            // 属性配置文件自身的编码
            if (value != null) {
                value = new String(value.getBytes("ISO-8859-1"), ENCODING);
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 获取邮箱列表
     * 
     * @param propertyName
     * @return
     */
    private static String[] getMails(String propertyName) {
        String[] result = null;
        String mails = getProperty(propertyName);
        if (mails != null && !mails.trim().equals("")) {
            result = mails.split(",");
            List<String> list = new ArrayList<>(new HashSet<>(Arrays.asList(result)));
            result = list.toArray(new String[list.size()]);
        }
        return result;
    }

}