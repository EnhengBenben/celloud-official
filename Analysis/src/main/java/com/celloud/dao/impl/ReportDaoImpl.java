package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.ReportDao;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class ReportDaoImpl implements ReportDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public Object getBigUserReportNum(Connection conn, Integer companyId, int role) {
		String sql = "select count(r.report_id)as num  FROM tb_report r,tb_user_company_relat uc "
				+ "where r.user_id = uc.user_id  and r.user_id not in ( " + noUserid + ")  "
				+ SqlController.whereCompany("uc", "company_id", role, companyId);
		LogUtil.info(log, sql);
		Long count = 0l;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<Long> rsh = new ScalarHandler<Long>();
			count = rsh.handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return count;
	}

	@Override
	public Object getBigUserRunNum(Integer companyId) {
		Connection conn = ConnectManager.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(r.report_id) num from tb_report r,tb_user u where r.user_id=u.user_id and r.flag=0 and r.isdel=0 and u.state=0 and u.user_id not in ("
				+ noUserid + ") and u.company_id=?";
		try {
			map = qr.query(conn, sql, new MapHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public List<Map<String, Object>> getUserRunEachApp(Connection conn, Integer role, Integer cmpId) {
		String sql = "select uc.company_id,c.company_name,r.app_id,count(r.report_id)as runNum from tb_report r,tb_user_company_relat uc,tb_company c where uc.company_id = c.company_id and r.user_id = uc.user_id "
				+ " and r.user_id not in ( " + noUserid + ")  "
				+ SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by r.app_id,uc.company_id order by runNum desc";
		LogUtil.info(log, sql);
		List<Map<String, Object>> res = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<List<Map<String, Object>>> rsh = new ScalarHandler<List<Map<String, Object>>>();
			res = rsh.handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return res;
	}

}
