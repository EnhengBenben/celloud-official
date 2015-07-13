package com.celloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

/**
 * @Description:数据库连接类
 */
public class ConnectManager {
	private static Logger logger = Logger.getLogger(ConnectManager.class);
	private static DataSource dataSource = null;
	private static boolean isInited = false;
	private static final String PROPERTIES_FILE = "jdbc.properties";
	private static Properties prop = new Properties();

	/**
	 * 初始化数据源
	 * 
	 * @return
	 */
	public synchronized static boolean initContext() {
		if (isInited) {
			return true;
		}
		logger.info("init SystemContext...");
		InputStream is = ConnectManager.class.getClassLoader()
				.getResourceAsStream(PROPERTIES_FILE);
		try {
			prop.load(is);
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (IOException e) {
			throw new RuntimeException(PROPERTIES_FILE + " file load error");
		} catch (Exception e) {
			logger.info("数据源创建失败");
			e.printStackTrace();
		}
		isInited = true;
		return true;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public synchronized static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			logger.info("获取数据库连接失败！");
			e.printStackTrace();
			return null;
		}
	}
}