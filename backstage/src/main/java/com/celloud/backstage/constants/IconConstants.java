package com.celloud.backstage.constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author han
 * @date 2016年1月26日 下午4:30:09
 */
public class IconConstants {
	private static Logger logger = LoggerFactory.getLogger(IconConstants.class);
	private static Properties properties = null;
	private static String appPath;
	private static String screenPath;
	private static String deptPath;
	private static String companyPath;
	private static String tempPath;
	/**
	 * 配置文件的地址
	 */
	private static final String PROPERTIES_PATH = "icon_path.properties";
	/**
	 * rocky乳腺癌产品的appid
	 */
	public static final Integer APP_ID_ROCKY = 123;
	/**
	 * bsi血流产品的appid
	 */
	public static final Integer APP_ID_BSI = 118;

	static {
		properties = new Properties();
		InputStream inputStream = IconConstants.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
		try {
			properties.load(inputStream);
			appPath = properties.getProperty("app");
			screenPath = properties.getProperty("screen");
			deptPath = properties.getProperty("dept");
			companyPath = properties.getProperty("company");
			tempPath = properties.getProperty("temp");
		} catch (IOException e) {
			logger.error("加载配置文件失败：{}", PROPERTIES_PATH, e);
		}
	}

	/**
	 * 获取APP图标文件夹
	 * 
	 * @return
	 * @author lin
	 * @date 2016年9月20日上午11:37:01
	 */
	public static String getAppPath() {
		return appPath;
	}

	/**
	 * 获取APP图标路径
	 * 
	 * @param name
	 * @return
	 * @author lin
	 * @date 2016年9月20日上午11:42:30
	 */
	public static String getAppPath(String name) {
		return appPath + File.separator + name;
	}

	/**
	 * 获取APP截图文件夹
	 * 
	 * @return
	 */
	public static String getScreenPath() {
		return screenPath;
	}

	/**
	 * 获取APP截图路径
	 * 
	 * @param name
	 * @return
	 * @author lin
	 * @date 2016年9月20日上午11:38:09
	 */
	public static String getScreenPath(String name) {
		return screenPath + File.separator + name;
	}

	/**
	 * 获取部门icon文件夹
	 * 
	 * @return
	 */
	public static String getDeptPath() {
		return deptPath;
	}

	/**
	 * 获取部门icon路径
	 * 
	 * @param name
	 * @return
	 * @author lin
	 * @date 2016年9月20日上午11:38:09
	 */
	public static String getDeptPath(String name) {
		return deptPath + File.separator + name;
	}

	/**
	 * 获取医院icon文件夹
	 * 
	 * @return
	 */
	public static String getCompanyPath() {
		return companyPath;
	}

	/**
	 * 获取医院icon路径
	 * 
	 * @param name
	 * @return
	 * @author lin
	 * @date 2016年9月20日上午11:38:09
	 */
	public static String getCompanyPath(String name) {
		return screenPath + File.separator + name;
	}

	/**
	 * 获取临时存储文件夹
	 * 
	 * @return
	 * @author lin
	 * @date 2016年9月20日下午1:44:04
	 */
	public static String getTempPath() {
		return tempPath;
	}

	/**
	 * 获取临时存储文件路径
	 * 
	 * @param name
	 * @return
	 * @author lin
	 * @date 2016年9月20日下午1:44:19
	 */
	public static String getTempPath(String name) {
		return tempPath + File.separator + name;
	}

	/**
	 * 清理7天之前的临时icon
	 * 
	 * @author lin
	 * @date 2016年9月20日下午2:18:28
	 */
	public static void cleanTemp() {
		File tempDir = new File(tempPath);
		if (tempDir == null || !tempDir.exists()) {
			return;
		}
		File[] tempFiles = tempDir.listFiles();
		if (tempFiles == null || tempFiles.length == 0) {
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date date = calendar.getTime();
		for (File file : tempFiles) {
			ObjectId id = new ObjectId(file.getName().substring(0, file.getName().indexOf(".")));
			if (date.after(id.getDate())) {
				file.delete();
			}
		}
	}

}
