package com.celloud.utils;

import org.apache.log4j.Logger;

public class LogUtil {
	public static void info(Logger log, Object sql) {
		log.info(Thread.currentThread().getStackTrace()[2] + ":" + sql);
	}
	public static void query(Logger log, String sql, Exception e) {
		log.error("query:" + sql);
		log.error(e.getStackTrace());
		e.printStackTrace();
	}
	
	public static void erro(Logger log,Exception e){
		log.error(e.getStackTrace());
	}
}
