package com.celloud.utils;

import org.apache.log4j.Logger;

public class LogUtil {
	public static void info(Logger log,Object msg){
		log.info(Thread.currentThread().getStackTrace()[2]+":"+msg);
	}
	public static void query(Logger log, String sql, Exception e){
		log.error("query:"+sql);
		log.error(e.getStackTrace());
		e.printStackTrace();
	}
//	public static void main(String[] args) {
//		Logger log = Logger.getLogger(Threefish.class.getClass());
//		info(log,"test");
//	}
}
