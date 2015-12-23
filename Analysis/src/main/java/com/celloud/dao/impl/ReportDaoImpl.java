package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;
import com.celloud.dao.ReportDao;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public  class ReportDaoImpl implements ReportDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;
	@Override
	public Object getBigUserReportNum(Integer companyId,int role) {
		Connection conn = ConnectManager.getConnection();

		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(r.report_id)as num  FROM tb_report r,tb_user_company_relat uc "
				+	"where r.user_id = uc.user_id  and r.user_id not in ( "
				+ noUserid + ")  "
			//	+ SqlController.whereCompany("uc", role, companyId)
				+ " group by uc.company_id ";
		LogUtil.info(log, sql);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return map.get("num");
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


}
