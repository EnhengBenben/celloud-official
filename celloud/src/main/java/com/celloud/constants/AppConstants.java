package com.celloud.constants;

import java.io.File;
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
public class AppConstants {
    private static Logger logger=LoggerFactory.getLogger(AppConstants.class);
    private static Properties properties = null;
    private static String AppPicturePath;
    /**
     * 配置文件的地址
     */
    private static final String PROPERTIES_PATH = "file_path.properties";
    public static Properties loadProperties() {
        properties = new Properties();
        InputStream inputStream = AppConstants.class.getClassLoader().getResourceAsStream("file_path.properties");
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
     * 获取APP图标
     * 
     * @return
     */
    public static String getAppPicturePath() {
        if (AppPicturePath == null) {
        	synchronized (PROPERTIES_PATH) {
        		AppPicturePath = getProperty("appPicturePath");
			}
        }
        return AppPicturePath+File.separator+"icon";
    }

    public static String getAppTempPath() {
        if (AppPicturePath == null) {
            AppPicturePath = getProperty("appPicturePath");
        }
        return AppPicturePath + File.separator + "temp";
    }
    
    //根据文件名获取
    public static String geAppPicturePath(String nameWithSuffix) {
        return getAppPicturePath() + File.separator + nameWithSuffix;
    }
    
    /**
     * 获取APP截图
     * 
     * @return
     */
    public static String getAppScreenPath() {
        if (AppPicturePath == null) {
            AppPicturePath = getProperty("appPicturePath");
        }
        return AppPicturePath+File.separator+"screen";
    }

    
    //根据文件名获取
    public static String geAppScreenPath(String nameWithSuffix) {
        return getAppScreenPath() + File.separator + nameWithSuffix;
    }
}
