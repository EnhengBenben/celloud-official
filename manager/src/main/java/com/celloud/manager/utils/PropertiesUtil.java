package com.celloud.manager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String testAccountIds;
	public static String outputPath;
	public static String reportTemplatePath;
	static {
		Properties prop = new Properties();
		InputStream inStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("file_path.properties");
		try {
			prop.load(inStream);
			testAccountIds=prop.getProperty("testAccountIds");
			outputPath = prop.getProperty("outputPath");
			reportTemplatePath = prop.getProperty("reportTemplatePath");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}