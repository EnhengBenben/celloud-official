package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.LoginLogDao;
import com.celloud.sdo.LoginLog;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class LoginLogDaoImpl implements LoginLogDao {

	Logger log = Logger.getLogger(this.getClass());
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;
	private String noUsername = PropertiesUtil.noUsername;

	@Override
	public List<LoginLog> getUserLoginInWeek(Date start) {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select  left(l.log_date,10)as weekDate, COUNT(l.user_name) AS logNum from tb_log l WHERE"
				+ " l.user_name is not null" + SqlController.notUserName("l", "user_name", noUsername)
				+ " and left(l.log_date,10) >= left(date_add(? ,INTERVAL -weekday(?) day),10) and left(l.log_date,10)<=left(date_add(? ,INTERVAL 7-weekday(?) day),10)"
				+ " group  by weekDate" + " order by weekDate desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start, start, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> getUserLoginNum(Date start) {
		Connection conn = ConnectManager.getConnection();
		List<LoginLog> list = null;
		String sql = "select l.user_name as userName,count(l.user_name) as logNum from tb_log l"
				+ " where l.user_name is not null" + SqlController.notUserName("l", "user_name", noUsername)
				+ " and left(l.log_date,10) >= left(date_add(?,INTERVAL -(7+weekday(now())) day),10) and left(l.log_date,10)<=left(?,10)"
				+ " group by l.user_name" + " order by logNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> getBrowserInWeek(Date start) {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select l.browser,count(l.user_name) as logNum from tb_log l" + " where l.browser is not null"
				+ SqlController.notUserName("l", "user_name", noUsername)
				+ " and left(l.log_date,10) >= left(date_add(?,INTERVAL -(7+weekday(now())) day),10) and left(l.log_date,10)<=left(?,10)"
				+ " group by l.browser" + " order by logNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
