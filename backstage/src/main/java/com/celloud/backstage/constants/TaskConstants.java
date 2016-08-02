package com.celloud.backstage.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author han
 * @date 2016年1月26日 下午4:30:09
 */
public class TaskConstants {
    private static Logger logger = LoggerFactory.getLogger(TaskConstants.class);
    private static Properties properties = null;
    private static String weekStatisticsResourcesPath;
    /**
     * 配置文件的地址
     */
    private static final String PROPERTIES_PATH = "file_path.properties";

    public static Properties loadProperties() {
        properties = new Properties();
        InputStream inputStream = TaskConstants.class.getClassLoader().getResourceAsStream("file_path.properties");
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


    public static String getWeekStatisticsResourcesPath() {
        if (weekStatisticsResourcesPath == null) {
            weekStatisticsResourcesPath = getProperty("weekStatisticsResourcesPath");
        }
        return weekStatisticsResourcesPath;
    }

}
