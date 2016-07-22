package com.celloud.backstage.constants;

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
public class CompanyConstants {
    private static Logger logger = LoggerFactory.getLogger(CompanyConstants.class);
    private static Properties properties = null;
    private static String companyIconPath;
    private static String reportTemplatePath;
    /**
     * 配置文件的地址
     */
    private static final String PROPERTIES_PATH = "file_path.properties";

    public static Properties loadProperties() {
        properties = new Properties();
        InputStream inputStream = CompanyConstants.class.getClassLoader().getResourceAsStream("file_path.properties");
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
     * 获取医院logo上传目录
     * 
     * @return
     */
    public static String getCompanyIconPath() {
        if (companyIconPath == null) {
            companyIconPath = getProperty("companyIconPath");
        }
        return companyIconPath;
    }

    public static String getCompanyIconTempPath() {
        if (companyIconPath == null) {
            companyIconPath = getProperty("companyIconPath");
        }
        return companyIconPath + File.separator + "temp";
    }

    public static String getReportTemplatePath() {
        if (reportTemplatePath == null) {
            reportTemplatePath = getProperty("reportTemplatePath");
        }
        return reportTemplatePath;
    }

    /**
     * 根据文件名获取医院logo路径
     * 
     * @param name
     * @return
     */
    public static String getCompanyIconPath(String nameWithSuffix) {
        return getCompanyIconPath() + File.separator + nameWithSuffix;
    }
}
