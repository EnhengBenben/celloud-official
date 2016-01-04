package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.celloud.dao.AppDao;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class AppDaoImpl implements AppDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;
	private String noUsername = PropertiesUtil.noUsername;

	@Override
	public Object getBigUserAPPNum(Integer companyId, int role) {
		Connection conn = ConnectManager.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(s.app_id) num from tb_app s where s.off_line=0"
				+ SqlController.whereCompany("s", "company_id", role, companyId);
		LogUtil.info(log, sql);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return map.get("num");
	}

	@Override
	public List<App> getAppListByBigUser(Integer companyId, Integer role) {
		List<App> list = new ArrayList<App>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select p.app_id,p.app_name,p.create_date,p.description,"
				+ "(select company_name from tb_company where company_id = p.company_id)as company_name,"
				+ "(select count(report_id) from tb_report where app_id = p.app_id)as runNum"
				+ " from tb_app p where p.off_line =0" + " order by runNum desc; ";
		SqlController.whereCompany("p", "company_id", role, companyId);
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public App getAppById(Integer appId) {
		App soft = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select p.app_id,p.app_name,p.data_num,p.create_date,p.intro,p.description,p.picture_name,"
				+ "(select count(r.report_id) from tb_report r where r.flag=0 and r.app_id = p.app_id  "
				+ SqlController.notUserId("r", noUserid) + ") as runNum ,"
				+ "(select ff.format_desc from tb_app_format_relat fr left join tb_file_format ff on fr.format_id = ff.format_id where fr.app_id = p.app_id limit 1) as dataType "
				+ " from tb_app p  where p.app_id = 	?";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<App> rsh = new BeanHandler<App>(App.class);
			soft = qr.query(conn, sql, rsh, appId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return soft;
	}

	@Override
	public List<App> getAppRunTimeInWeek(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		Connection conn = ConnectManager.getConnection();
		List<App> list = new ArrayList<App>();
		String sql = "select r.user_id, left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,count(r.report_id) as runNum,"
				+ " (select s.software_name from tb_software s where s.software_id = r.software_id ) as softwareName"
				+ " from tb_report r, "

				+ " (select u.user_id,u.username,company_name " + " from tb_user u,tb_dept d, tb_company c "
				// + " where u.dept_id = d.dept_id " +
				// SqlController.whereCompany(role, cmpId)
				+ " and d.company_id = c.company_id) uc "

				+ " where r.create_date between ? and ?" + SqlController.whereSoftware("r", softwareId)
				+ " and r.flag = 0 and r.user_id = uc.user_id" + SqlController.notUserId("r", noUserid)
				+ "  group by weekDate, r.software_id" + " order by weekDate  ,runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			log.error("获取周APP运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getAppRunTimeInMonth(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		Connection conn = ConnectManager.getConnection();
		List<App> list = new ArrayList<App>();
		String sql = "select left(r.create_date,7)as yearMonth,count(r.report_id) as runNum,"
				+ " (select s.software_name from tb_software s where s.software_id = r.software_id ) as softwareName"
				+ " from tb_report r ,"

				+ " (select u.user_id,u.username,company_name " + " from tb_user u,tb_dept d, tb_company c "
				// + " where u.dept_id = d.dept_id " +
				// SqlController.whereCompany(role, cmpId)
				+ " and d.company_id = c.company_id) uc "

				+ " where r.create_date between ? and ?" + SqlController.whereSoftware("r", softwareId)
				+ " and r.flag = 0 and r.user_id = uc.user_id" + SqlController.notUserId("r", noUserid)
				+ " group by yearMonth, r.software_id" + " order by yearMonth  ,runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			log.error("获取APP各月运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getAppByCompanyId(Integer cmpId, Integer role) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select s.software_id as softwareId,s.software_name as softwareName,s.english_name as englishName,s.picture_name as pictureName,s.bhri,"
				+ " s.create_date as createDate,s.company_id as companyId,s.intro,s.description ";
		// + " from tb_software s order by softwareName" +
		// SqlController.whereCompany("s", role, cmpId);
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			log.error("获取APP各月运行次数列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getAppRunTop(String type, int topN, Date start, Date end) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = " select left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,"
				+ " r.software_id,(select s.software_name from tb_software s where s.software_id=r.software_id )as softwareName,count(r.software_id) as runNum"
				+ " from tb_report r " + " where r.create_date is not null"
				+ SqlController.whereifTimeNull("r", "create_date", start, end) + " group by weekDate,r.software_id"
				+ " order by weekDate desc,runNum desc";
		// + SqlController.limit(topN);
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getTotalUserRunNum(int topN) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.user_id,count(r.report_id) as runNum,u.username FROM tb_report r,tb_user u where u.user_id = r.user_id and u.state =0 and r.flag = 1 "
				+ SqlController.notUserId("u",  noUserid) + "  group by user_id order by runNum desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
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
		String sql = "select l.user_name ,count(l.user_name) as logNum from tb_log l,tb_user u "
				+ " where l.user_name is not null and l.user_name = u.username and u.state=0"
				+ SqlController.notUserName("l", "user_name", 3, noUsername) + " group by l.user_name"
				+ " order by logNum desc";
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
				+ SqlController.notUserName("l", "user_name", 3,noUsername) + "group by l.browser "
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
	public List<App> getAppRunNumCount(Date start) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(r.create_date,10) as weekDate,count(r.report_id)as runNum from tb_report r"
				+ " where r.create_date is not null" + SqlController.notUserId("r", noUserid)
				+ " and left(date_add(r.create_date,INTERVAL -weekday(r.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
				+ " group by weekDate" + " order by weekDate desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getAppUserCount(Date start) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.user_id,(select username from tb_user where user_id = r.user_id)as userName,count(r.report_id)as runNum from tb_report r"
				+ " where r.create_date is not null" + SqlController.notUserId("r",  noUserid)
				+ " and left(date_add(r.create_date,INTERVAL -weekday(r.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
				+ " group by r.user_id" + " order by runNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, start, start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getTotalAppRunNum(int topN) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.app_id,count(r.app_id)as runNum,"
				+ " (select app_name from tb_app  where app_id = r.app_id)as app_name "
				+ " from tb_report r where r. flag=0  group by r.app_id  order by runNum desc "
				+ SqlController.limit(topN);
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<App> getBigUserAppList() {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select p.company_id,count(p.app_id)as runNum,c.company_name,c.create_date from tb_app p left join tb_company c on p.company_id = c.company_id "
				+ " where p.off_line=0 and company_name is not null group by company_id order by runNum desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<App> getAppRun(int app_id) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(r.create_date,7) as yearMonth,count(1)as runNum from tb_report r where  r.app_id = ? and r.flag =0 and r.create_date is not null group by yearMonth order by yearMonth desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, app_id);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<App> getAppListByBigUserId(int cmpId) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select p.app_id,p.app_name,p.create_date,p.picture_name,p.company_id,p.description,"
				+" (select count(1) from tb_report where app_id = p.app_id)as runNum,"
				+" (select company_name from tb_company where company_id = p.company_id) as company_name"
				+"  from tb_app p where p.company_id =? order by runNum desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, cmpId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<App> getAppList(Connection conn, Integer role, Integer cmpId, Date start, Date end, Integer topN) {
		List<App> list = null;
		String sql = "select count(1)as runNum,r.app_id,s.app_name,s.create_date "
				+" from tb_report r,tb_user_company_relat uc ,tb_app s where r.app_id = s.app_id and r.flag=0 and r.user_id = uc.user_id and r.create_date between  ? and  ?  "
				+ SqlController.notUserId("r", noUserid)
				+ SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by r.app_id order by runNum desc"
				+SqlController.limit(topN);
		LogUtil.info(log, sql);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, start);
			ps.setObject(2, end);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<List<App>> rsh = new BeanListHandler<>(App.class);
			list = rsh.handle(rs);
		} catch (Exception e) {
			LogUtil.query(log, sql, e);
			ConnectManager.close(conn);
		}
		return list;
	}
}
