package com.celloud.mongo.utils;

import java.net.UnknownHostException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.nova.tools.utils.PropertiesUtils;

public class MongoDBUtils {
    private static final Logger logger = LogManager
	    .getLogger(MongoDBUtils.class);
    private static Mongo mongo_report = null;
    private static Morphia morphia = null;

    public static synchronized Mongo getReportMongo() {
	if (mongo_report == null) {
	    try {
		mongo_report = new Mongo(PropertiesUtils.report_dburl);
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
