/**  */
package com.celloud.mongo.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.celloud.mongo.dao.ReportDAO;
import com.celloud.mongo.utils.MongoDBUtils;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

/**
 * 系统配置
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午5:27:33
 * @version Revision: 1.0
 */
public class SystemContext {
	private static final Logger logger = LogManager
			.getLogger(SystemContext.class.getName());
	private static boolean isInited = false;
	public static final String PROPERTIES_FILE = "system.properties";

	public static final String PROP_REPORTDAO = "ReportDAO";

	private static ReportDAO reportDAO = null;

	private static Properties sysProperties = new Properties();

	public synchronized static boolean initContext() {
		if (isInited) {
			return true;
		}
		logger.info("init  SystemContext...");
		InputStream is = SystemContext.class.getClassLoader()
				.getResourceAsStream(PROPERTIES_FILE);
		try {
			sysProperties.load(is);
			// 设置commons日志不输出
			System.setProperty("org.apache.commons.logging.Log",
					"org.apache.commons.logging.impl.NoOpLog");
		} catch (IOException e) {
			throw new RuntimeException(PROPERTIES_FILE + " file load error");

		}
		isInited = true;
		return true;
	}

	public static Properties getSysProperties() {
		return sysProperties;
	}

	public static void setSysProperties(Properties sysProperties) {
		SystemContext.sysProperties = sysProperties;
	}

	public synchronized static ReportDAO getReportDAO() {
		if (reportDAO == null) {
			try {
				Class<?> clazz = Class.forName(sysProperties
						.getProperty(PROP_REPORTDAO));
				Constructor<?> constructor = clazz.getConstructor(Mongo.class,
						Morphia.class, String.class);
				reportDAO = (ReportDAO) constructor.newInstance(
						MongoDBUtils.getReportMongo(),
						MongoDBUtils.getMorphia(),
						SystemContext.getReportDBName());
			} catch (Exception e) {
				logger.error(" class load error", e);
			}
		}
		return reportDAO;
	}

	public static String getReportDBUrl() {
		return sysProperties.getProperty("report_dburl");
	}

	public static String getReportDBName() {
		return sysProperties.getProperty("report_dbname");
	}

	public static String getTime() {
		return sysProperties.getProperty("time");
	}

}
