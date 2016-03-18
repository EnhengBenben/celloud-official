package com.celloud.constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedbackConstants {
    private static Logger logger = LoggerFactory.getLogger(FeedbackConstants.class);
    private static Properties properties = null;
    /**
     * 配置文件的地址
     */
    private static final String PROPERTIES_PATH = "system.properties";
    /**
     * 工单已关闭
     */
    public static final Integer SOLVED = new Integer(1);
    /**
     * 工单未关闭
     */
    public static final Integer UNSOLVED = new Integer(0);
    /**
     * 工单包含附件
     */
    public static final Integer HASATTACHMENT = new Integer(1);
    /**
     * 工单不包含附件
     */
    public static final Integer NOT_HAS_ATTACHMENT = new Integer(0);
    /**
     * 工单附件的上传目录
     */
    private static String attachmentPath;

    public static Properties loadProperties() {
        properties = new Properties();
        InputStream inputStream = FeedbackConstants.class.getClassLoader().getResourceAsStream("system.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("加载配置文件失败：{}", PROPERTIES_PATH, e);
        }
        return properties;
    }

    public static String getProperty(String name) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(name);
    }

    /**
     * 获取工单附件的上传目录
     * 
     * @return
     */
    public static String getAttatchmentPath() {
        return getAttachmentPath(new Date());
    }

    public static String getAttachmentTempPath() {
        if (attachmentPath == null) {
            attachmentPath = getProperty("feedback_attatchment_path");
        }
        return attachmentPath + File.separator + ConstantsData.getLoginUserId() + File.separator + "temp";
    }

    /**
     * 根据文件名获取工单附件的路径
     * 
     * @param name
     * @return
     */
    public static String getAttachment(String nameWithSuffix) {
        int index = nameWithSuffix.indexOf(".");
        ObjectId id = new ObjectId(nameWithSuffix.substring(0, index));
        return getAttachmentPath(id.getDate()) + File.separator + nameWithSuffix;
    }

    /**
     * 根据文件名获取工单附件的路径
     * 
     * @param nameWithSuffix
     * @return
     */
    public static String getAttachment(String nameWithoutSuffix, String suffix) {
        return getAttachment(nameWithoutSuffix + "." + suffix);
    }

    /**
     * 获取工单附件的上传目录
     * 
     * @param date
     * @return
     */
    public static String getAttachmentPath(Date date) {
        if (attachmentPath == null) {
            attachmentPath = getProperty("feedback_attatchment_path");
        }
        return attachmentPath + File.separator + ConstantsData.getLoginUserId() + File.separator
                + new SimpleDateFormat("yyyyMM").format(date);
    }
}
