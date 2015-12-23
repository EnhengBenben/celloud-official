package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;
import com.celloud.dao.SoftwareDao;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class SoftwareDaoImpl implements SoftwareDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;
	private String noUsername = PropertiesUtil.noUsername;

	@Override
	public Object getBigUserAPPNum(Integer companyId, int role) {
		Connection conn = ConnectManager.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(s.software_id) num from tb_software s where s.off_line=0"
				+ SqlController.whereCompany("s", role, companyId);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public List<Software> getAppListByBigUser(Integer companyId, Integer role) {
		List<Software> list = new ArrayList<Software>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select s.software_id softwareId,s.software_name softwareName,s.create_date createDate,s.bhri,s.type,s.data_num dataNum,s.description,"
				+ "(select count(r.report_id) from tb_report r where r.flag=0 and r.software_id=s.software_id "
				+ SqlController.notUserId("r", role, noUserid)
				+ ") runNum,(select GROUP_CONCAT(df.format_desc) from tb_data_format df,tb_software_format_relat sf where df.format_id=sf.format_id and sf.software_id=s.software_id group by sf.software_id) dataType "
				+ "from tb_software s where s.off_line=0 " + SqlController.whereCompany("s", role, companyId);
		log.info(sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Software getAppById(Integer appId) {
		Software soft = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select s.software_id softwareId,s.software_name softwareName,s.create_date createDate,s.bhri,s.`type`,s.data_num dataNum,s.description,s.picture_name pictureName,s.intro,(select count(r.report_id) from tb_report r where r.flag=0 and r.software_id=s.software_id and r.user_id not in ("
				+ noUserid
				+ ")) runNum,(select GROUP_CONCAT(df.format_desc) from tb_data_format df,tb_software_format_relat sf where df.format_id=sf.format_id and sf.software_id=s.software_id group by sf.software_id) dataType from tb_software s where s.software_id=?";
		try {
			ResultSetHandler<Software> rsh = new BeanHandler<Software>(Software.class);
			soft = qr.query(conn, sql, rsh, appId);
		} catch (SQLException e) {
			log.error("根据软件id获取软件信息:" + e);
			e.printStackTrace();
		}
		return soft;
	}

	@Override
	public List<Software> getAppRunTimeInWeek(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		Connection conn = ConnectManager.getConnection();
		List<Software> list = new ArrayList<Software>();
		String sql = "select r.user_id, left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,count(r.report_id) as runNum,"
				+ " (select s.software_name from tb_software s where s.software_id = r.software_id ) as softwareName"
				+ " from tb_report r, "

				+ " (select u.user_id,u.username,company_name " + " from tb_user u,tb_dept d, tb_company c "
				+ " where u.dept_id = d.dept_id " + SqlController.whereCompany(role, cmpId)
				+ " and d.company_id = c.company_id) uc "

				+ " where r.create_date between ? and ?" + SqlController.whereSoftware("r", softwareId)
				+ " and r.flag = 0 and r.user_id = uc.user_id" + SqlController.notUserId("r", role, noUserid)
				+ "  group by weekDate, r.software_id" + " order by weekDate  ,runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			log.error("获取周APP运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getAppRunTimeInMonth(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		Connection conn = ConnectManager.getConnection();
		List<Software> list = new ArrayList<Software>();
		String sql = "select left(r.create_date,7)as yearMonth,count(r.report_id) as runNum,"
				+ " (select s.software_name from tb_software s where s.software_id = r.software_id ) as softwareName"
				+ " from tb_report r ,"

				+ " (select u.user_id,u.username,company_name " + " from tb_user u,tb_dept d, tb_company c "
				+ " where u.dept_id = d.dept_id " + SqlController.whereCompany(role, cmpId)
				+ " and d.company_id = c.company_id) uc "

				+ " where r.create_date between ? and ?" + SqlController.whereSoftware("r", softwareId)
				+ " and r.flag = 0 and r.user_id = uc.user_id" + SqlController.notUserId("r", role, noUserid)
				+ " group by yearMonth, r.software_id" + " order by yearMonth  ,runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			log.error("获取APP各月运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getAppByCompanyId(Integer cmpId, Integer role) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select s.software_id as softwareId,s.software_name as softwareName,s.english_name as englishName,s.picture_name as pictureName,s.bhri,"
				+ " s.create_date as createDate,s.company_id as companyId,s.intro,s.description "
				+ "  from tb_software s order by softwareName" + SqlController.whereCompany("s", role, cmpId);
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			log.error("获取APP各月运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getAppRunTop(String type, int topN, Date start, Date end) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = " select left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,"
				+ " r.software_id,(select s.software_name from tb_software s where s.software_id=r.software_id )as softwareName,count(r.software_id) as runNum"
				+ " from tb_report r " + " where r.create_date is not null"
				+ SqlController.whereifTimeNull("r", "create_date", start, end) + " group by weekDate,r.software_id"
				+ " order by weekDate desc,runNum desc";
		// + SqlController.limit(topN);
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getTotalUserRunNum(int topN) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.user_id,count(r.report_id)as runNum,"
				+ " (select username from tb_user where user_id = r.user_id)as userName,"
				+ " s.software_name as softwareName" + " from tb_report r ,tb_software s"
				+ " where r.software_id = s.software_id and s.off_line =0" + " group by r.user_id"
				+ " order by runNum desc " + SqlController.limit(topN);
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<LoginLog> getTotalUserLogin(int topN) {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select l.user_name as userName,count(l.user_name) as logNum from tb_log l"
				+ SqlController.whereNotUserName("l", "user_name", noUsername) + " and l.user_name is not null"
				+ " group by l.user_name" + " order by logNum desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<LoginLog> getBrowerCount() {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT l.browser,count(l.browser)as logNum FROM tb_log l "
				+ SqlController.whereNotUserName("l", "user_name", noUsername) + "group by l.browser "
				+ "order by logNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getAppRunNumCount(Date start) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(r.create_date,10) as weekDate,count(r.report_id)as runNum from tb_report r"
				+ " where r.create_date is not null" + SqlController.notUserId("r", 3, noUserid)
				+ " and left(date_add(r.create_date,INTERVAL -weekday(r.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
				+ " group by weekDate" + " order by weekDate desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getAppUserCount(Date start) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.user_id,(select username from tb_user where user_id = r.user_id)as userName,count(r.report_id)as runNum from tb_report r"
				+ " where r.create_date is not null" + SqlController.notUserId("r", 3, noUserid)
				+ " and left(date_add(r.create_date,INTERVAL -weekday(r.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
				+ " group by r.user_id" + " order by runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Software> getTotalAppRunNum(int topN) {
		List<Software> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.software_id,count(r.software_id)as runNum,"
				+ " (select software_name from tb_software where software_id = r.software_id)as software_name  "
				+ " from tb_report r group by r.software_id "
				+ " order by runNum desc "
				+ SqlController.limit(topN);
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
