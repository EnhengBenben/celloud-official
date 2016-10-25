package com.celloud.box.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行系统命令的工具类
 * 
 * @author sun8wd
 *
 */
public class CommandUtils {
	private static Logger logger = LoggerFactory.getLogger(CommandUtils.class);

	public static boolean excute(String command) {
		logger.info("执行命令：{}", command);
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc = runtime.exec(command);
			InputStream ins = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			String line = null;
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}
			return true;
		} catch (IOException e) {
			logger.error("执行命令失败：{}", command, e);
		}
		return false;
	}

}
