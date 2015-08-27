package com.nova.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

public class ConnectManager {

    static Logger logger = Logger.getLogger(ConnectManager.class);
    private static DataSource dataSource = null;
    private static boolean isInited = false;
    public static final String PROPERTIES_FILE = "jdbc.properties";
    private static Properties prop = new Properties();

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

    public synchronized static Connection getConnection() {
	try {
	    return dataSource.getConnection();
	} catch (SQLException e) {
	    logger.info("获取数据库连接失败！");
	    e.printStackTrace();
	    return null;
	}
    }

    public synchronized static boolean initSystem() {
	if (isInited) {
	    return true;
	}
	logger.info("init SystemContext...jndi");
	try {
	    Context ctx = new InitialContext();
	    dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
	} catch (NamingException e) {
	    e.printStackTrace();
	}
	isInited = true;
	return isInited;
    }

    public static void free(Connection conn, PreparedStatement ps, ResultSet rs) {
	try {
	    if (rs != null) {
		rs.close();
	    }
	} catch (SQLException e) {
	    logger.error("关闭rs异常", e);
	} finally {
	    try {
		if (ps != null) {
		    ps.close();
		}
	    } catch (SQLException e) {
		logger.error("关闭ps异常", e);
	    } finally {
		try {
		    if (conn != null) {
			conn.close();
		    }
		} catch (SQLException e) {
		    logger.error("关闭conn异常", e);
		}
	    }
	}
    }

    public static void free(Connection conn, Statement st, ResultSet rs) {
	try {
	    if (rs != null) {
		rs.close();
	    }
	} catch (SQLException e) {
	    logger.error("关闭rs异常", e);
	} finally {
	    try {
		if (st != null) {
		    st.close();
		}
	    } catch (SQLException e) {
		logger.error("关闭st异常", e);
	    } finally {
		try {
		    if (conn != null) {
			conn.close();
		    }
		} catch (SQLException e) {
		    logger.error("关闭conn异常", e);
		}
	    }
	}
    }

}
