package com.nova.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String toolsPath;
	public static String toolsOutPath;
	public static String bigFilePath;
	public static String bigFileCompressPath;
	public static String rsainfo;
	public static String fileFinal;
	/**
	 * 文件断点续传时，临时文件存放处
	 */
	public static String tmp;
	static {
		Properties prop = new Properties();
		InputStream inStream = ConnectManager.class.getClassLoader()
				.getResourceAsStream("jdbc.properties");
		try {
			prop.load(inStream);
			toolsPath = prop.getProperty("toolsPath");
			toolsOutPath = prop.getProperty("toolsOutPath");
			bigFilePath = prop.getProperty("bigFilePath");
			bigFileCompressPath = prop.getProperty("bigFileCompressPath");
			rsainfo = prop.getProperty("rsainfo");
			tmp = prop.getProperty("tmp");
			fileFinal = prop.getProperty("fileFinal");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}