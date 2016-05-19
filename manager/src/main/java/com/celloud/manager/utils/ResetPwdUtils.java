package com.celloud.manager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ResetPwdUtils {
    static Logger logger = Logger.getLogger(ResetPwdUtils.class);
    public static String password;
    public static String title;
    public static String content;
    public static String celloudPath;

    public static String userPath;
    public static String userTitle;
    public static String userContent;

    public static Properties prop = null;
    static {
	prop = new Properties();
	InputStream inStream = ResetPwdUtils.class.getClassLoader()
		.getResourceAsStream("ResetPwd.properties");
	try {
	    prop.load(inStream);
	    password = prop.getProperty("password");
	    title = prop.getProperty("title");
	    content = prop.getProperty("content");
	    celloudPath = prop.getProperty("celloudPath");

	    userPath = prop.getProperty("userPath");
	    userTitle = prop.getProperty("userTitle");
	    userContent = prop.getProperty("userContent");
	} catch (IOException e) {
	    logger.info("读取ResetPwd配置文件失败");
	}
    }

    /**
     * 获取重置密码
     * 
     * @return
     */
    public String getPwd() {
	return password;
    }

    /**
     * 忘记密码标题
     * 
     * @return
     */
    public static String getEmailTile() {
	return title;
    }
}
