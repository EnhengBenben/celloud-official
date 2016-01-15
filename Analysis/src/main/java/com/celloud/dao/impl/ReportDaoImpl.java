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
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.ReportDao;
import com.celloud.sdo.Company;
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
	public List<Map<String, Object>> getCompanyRunEachApp(Connection conn, Integer role, Integer cmpId) {
		String sql = "select u.company_id,c.company_name,r.app_id,p.app_name,count(r.report_id)as runNum from tb_report r,tb_user_company_relat uc,tb_company c,tb_app p,tb_user u where u.company_id = c.company_id and r.user_id = uc.user_id "
				+ " and r.user_id = u.user_id and r.app_id = p.app_id and  r.user_id not in ( " + noUserid + ")  "
				+ SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by r.app_id,uc.company_id order by runNum desc";
		LogUtil.info(log, sql);
		List<Map<String, Object>> res = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			res = new MapListHandler().handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return res;
	}

	@Override
	public List<Company> getCompanyRunEachApp_Company(Connection conn, Integer role, Integer cmpId) {
		String sql = "select  distinct(u.company_id),c.company_name from tb_user u,tb_company c, tb_user_company_relat uc where u.company_id = c.company_id and u.user_id = uc.user_id "
				+ " and u.user_id not in ( " + noUserid + ")  "
				+ SqlController.whereCompany("uc", "company_id", role, cmpId);
		LogUtil.info(log, sql);
		List<Company> res = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<>(Company.class);
			res = rsh.handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return res;
	}

}
