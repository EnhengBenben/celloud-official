package com.celloud.backstage.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MachineUtil {
	static Logger logger = Logger.getLogger(MachineUtil.class);
    public static String host;
    public static String port;
    public static String username;
    public static String password;
	static {
		Properties prop = new Properties();
		InputStream inStream = MachineUtil.class.getClassLoader()
                .getResourceAsStream("machine.properties");
		try {
			prop.load(inStream);
            host = prop.getProperty("host");
            port = prop.getProperty("port");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
		} catch (IOException e) {
            logger.info("读取机器配置文件失败");
		}
	}
}