package com.celloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String rsainfo;
	public static String noUserid;
	public static String noUsername;
	public static String outputPath;
	static {
		Properties prop = new Properties();
		InputStream inStream = ConnectManager.class.getClassLoader()
				.getResourceAsStream("jdbc.properties");
		try {
			prop.load(inStream);
			rsainfo = prop.getProperty("rsainfo");
			noUserid = prop.getProperty("noUserid");
			noUsername = prop.getProperty("noUsername");
			outputPath = prop.getProperty("outputPath");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}