package com.celloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String bigFilePath;
    public static String datalist;
    public static String outputPath;
    public static String experimentExcelPath;
    public static String testAccountIds;
    public static String rockyPdfPath;
    /**
     * pdf 的 logo
     */
    public static String img;
	static {
		Properties prop = new Properties();
		InputStream inStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("file_path.properties");
		try {
			prop.load(inStream);
			bigFilePath = prop.getProperty("bigFilePath");
            datalist = prop.getProperty("datalist");
            outputPath = prop.getProperty("outputPath");
            img = prop.getProperty("img");
            testAccountIds = prop.getProperty("testAccountIds");
            experimentExcelPath = prop.getProperty("experimentExcelPath");
            rockyPdfPath = prop.getProperty("rockyPdfPath");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}