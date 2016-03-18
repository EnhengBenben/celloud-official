package com.celloud.constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author han
 * @date 2016年1月27日 下午2:29:40
 */
public class DeptConstants {
    private static Logger logger=LoggerFactory.getLogger(DeptConstants.class);
    private static Properties properties = null;
    private static String deptIconPath;
    /**
     * 配置文件的地址
     */
    private static final String PROPERTIES_PATH = "file_path.properties";
    public static Properties loadProperties() {
        properties = new Properties();
        InputStream inputStream = DeptConstants.class.getClassLoader().getResourceAsStream("file_path.properties");
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
     * 获取部门logo上传目录
     * 
     * @return
     */
    public static String getDeptIconPath() {
        if (deptIconPath == null) {
            deptIconPath = getProperty("deptIconPath");
        }
        return deptIconPath;
    }

    public static String getDeptIconTempPath() {
        if (deptIconPath == null) {
            deptIconPath = getProperty("deptIconPath");
        }
        return deptIconPath + File.separator + "temp";
    }
    
    /**
     * 根据文件名获取部门logo路径
     * 
     * @param name
     * @return
     */
    public static String getDeptIconPath(String nameWithSuffix) {
        int index = nameWithSuffix.indexOf(".");
        ObjectId id = new ObjectId(nameWithSuffix.substring(0, index));
        return getDeptIconPath() + File.separator + nameWithSuffix;
    }
}
