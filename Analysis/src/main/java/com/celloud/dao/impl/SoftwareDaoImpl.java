package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.SoftwareDao;
import com.celloud.utils.ConnectManager;

public class SoftwareDaoImpl implements SoftwareDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private Connection conn = ConnectManager.getConnection();
	private QueryRunner qr = new QueryRunner();
	@Override
	public Object getBigUserAPPNum(Integer companyId) {
		log.info("获取大客户的APP总量");
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(software_id) num from tb_software where company_id=? and off_line=0";
		try {
			map = qr.query(conn, sql, new MapHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

}
