package com.mongo.utils;

import java.net.UnknownHostException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.code.morphia.Morphia;
import com.mongo.core.SystemContext;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * mongo工具类.
 * 
 * @author <a href="mailto:wuzhiqiang@novacloud.com">wuzq</a>
 * @date 2013-8-14上午11:19:14
 * @version Revision: 1.3
 */
public class MongoDBUtils {
    private static final Logger logger = LogManager
	    .getLogger(MongoDBUtils.class);
    private static Mongo mongo_report = null;
    private static Morphia morphia = null;

    public static synchronized Mongo getReportMongo() {
	if (mongo_report == null) {
	    try {
		mongo_report = new Mongo(SystemContext.getReportDBUrl());
	    } catch (UnknownHostException e) {
		logger.error("不能连接数据库!", e);
	    } catch (MongoException e) {
		logger.error("数据库连接错误!", e);
	    }
	}
	return mongo_report;
    }

    public static synchronized Mongo getReportMongo(String dburl) {
	if (mongo_report == null
		|| !mongo_report.getAddress().toString().equals(dburl)) {
	    try {
		mongo_report = new Mongo(dburl);
	    } catch (UnknownHostException e) {
		logger.error("不能连接数据库!", e);
	    } catch (MongoException e) {
		logger.error("数据库连接错误!", e);
	    }
	}
	return mongo_report;
    }

    public static synchronized Morphia getMorphia() {
	if (morphia == null) {
	    morphia = new Morphia();
	}
	return morphia;
    }

    public static synchronized void closeReportMongo() {
	if (mongo_report != null) {
	    mongo_report.close();
	}
    }

}
