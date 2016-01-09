package com.celloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String bigFilePath;
    public static String outputPath;
	static {
		Properties prop = new Properties();
		InputStream inStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("file_path.properties");
		try {
			prop.load(inStream);
			bigFilePath = prop.getProperty("bigFilePath");
            outputPath = prop.getProperty("outputPath");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}