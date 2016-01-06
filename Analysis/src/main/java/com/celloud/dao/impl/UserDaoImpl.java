package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
import com.celloud.dao.UserDao;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.Entry;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.sdo.TotalCount;
import com.celloud.sdo.User;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class UserDaoImpl implements UserDao {
	Logger log = Logger.getLogger(UserDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUsername = PropertiesUtil.noUsername;
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public List<LoginLog> logCountEveryUser(Date beginDate, Date endDate) {
		Connection conn = ConnectManager.getConnection();
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select user_name as userName,count(*) as logNum from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername).append(") group by userName order by logNum desc");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("统计指定时间内每个用户登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> logCountEveryDay(Date beginDate, Date endDate) {
		Connection conn = ConnectManager.getConnection();
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select date_format(log_date,'%Y-%c-%d') as logDate,count(*) as logNum from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername).append(") group by logDate");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("统计指定时间内所有用户每天登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> logCountEveryBrowser(Date beginDate, Date endDate) {
		Connection conn = ConnectManager.getConnection();
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			StringBuffer sql = new StringBuffer("select os,browser from tb_log from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername).append(") group by browser");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("指定时间内所有浏览器的登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer userCount() {
		Connection conn = ConnectManager.getConnection();
		int count = 0;
		try {
			ResultSetHandler<LoginLog> rsh = new BeanHandler<LoginLog>(LoginLog.class);
			StringBuffer sql = new StringBuffer("select count(*) as logNum from tb_user where state=0 and role != 3");
			sql.append(" and username not in (").append(noUsername).append(")");
			LoginLog loginlog = qr.query(conn, sql.toString(), rsh);
			count = loginlog.getLogNum();
		} catch (SQLException e) {
			log.error("统计所有用户数量出错！！！" + e);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Integer userAddBetweenDate(Date beginDate, Date endDate) {
		int count = 0;
		Connection conn = ConnectManager.getConnection();
		try {
			ResultSetHandler<LoginLog> rsh = new BeanHandler<LoginLog>(LoginLog.class);
			StringBuffer sql = new StringBuffer("select count(*) as logNum from tb_user where 1=1");
			if (beginDate != null) {
				sql.append(" and create_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and create_date<'").append(endDate).append("'");
			}
			sql.append(" and username not in (").append(noUsername).append(")");
			LoginLog loginlog = qr.query(conn, sql.toString(), rsh);
			count = loginlog.getLogNum();
		} catch (SQLException e) {
			log.error("统计指定时间段新增的用户数量出错！！！" + e);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public User login(String username, String password) {
		Connection conn = ConnectManager.getConnection();
		String sql = "select u.user_id,u.username,u.email,u.cellphone,u.create_date,u.role,u.remark,u.navigation,u.truename,u.state,u.dept_id,u.company_id,(select company_name from tb_company where company_id = u.company_id)as company_name from tb_user u where  (u.username=? or u.email=? or user_id= ?) and u.password=?";
		User user = null;
		log.info("query:" + sql);
		try {
			ResultSetHandler<User> rsh = new BeanHandler<User>(User.class);
			user = qr.query(conn, sql.toString(), rsh, username, username, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Object getBigUsersUserNum(Integer cmpId, int role) {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = null;
		String sql = "select count(u.user_id) num from tb_user u ,tb_user_company_relat uc where  uc.user_id = u.user_id and u.state=0 and u.user_id not in ("
				+ noUserid + ") " 
				+ SqlController.whereCompany("uc", "company_id", role, cmpId);
		LogUtil.info(log, sql);
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		Map<String, Object> m = list.get(0);
		return m.get("num");
	}

	@Override
	public List<Map<String, Object>> getBigUsersUser(Integer companyId) {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = null;
		String sql = "select * from tb_user where state=0 and user_id not in (" + noUserid + ") and company_id=?";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId);
		} catch (SQLException e) {
			log.error("获取大客户的所有客户信息出错。" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<User> getUserListByBigCom(Integer companyId, Integer role, String orderType) {
		List<User> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = " select u.user_id,u.username,u.email, u.cellphone,u.create_date as createDate,"
				+ " (select company_name from tb_company where company_id = u.company_id) as company_name, "
				+ " (select dept_name from tb_dept where dept_id = u.dept_id)as dept_name, "
				+ " (select count(ifnull(file_id,1)) from tb_file where user_id = u.user_id and state=0)as fileNum, "
				+ " (select sum(ifnull(size,0)) from tb_file where user_id = u.user_id and state=0)as size, "
				+ " (select count(report_id) from tb_report where user_id = u.user_id and flag =0)as runNum "
				+ " from tb_user u where u.role=0 and u.state=0 " + SqlController.notUserId("u", noUserid)
				+ SqlController.whereCompany("u", "company_id", role, companyId) + SqlController.orderBy(orderType);
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<User>> rsh = new BeanListHandler<User>(User.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public User getUserById(Integer userId) {
		Connection conn = ConnectManager.getConnection();
		User user = null;
		String sql = "select u.user_id userId,u.username,u.email,u.cellphone,u.create_date createDate,d.dept_name deptName,c.company_name companyName,(select count(f.file_id) from tb_file f where f.user_id=u.user_id and f.state=0) fileNum,(select ifnull(sum(f.size),0) from tb_file f where f.user_id=u.user_id and f.state=0) fileSize,(select count(*) from tb_report r where r.user_id=u.user_id and r.isdel=0 and (r.flag=0 or r.report_id=11)) reportNum from tb_user u left join tb_dept d on u.dept_id=d.dept_id left join tb_company c on d.company_id=c.company_id where u.user_id=?";
		try {
			ResultSetHandler<User> rsh = new BeanHandler<User>(User.class);
			user = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			log.error("根据用户id获取用户信息失败:" + e);
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<LoginLog> getLogById(String userId) {
		Connection conn = ConnectManager.getConnection();
		List<LoginLog> list = null;
		String sql = "SELECT user_name as userName,log_date as logDate,ip,os,browser,address FROM tb_log where user_name = ? order by log_date desc limit 10";
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<App> getAppRunTimesByUId(Integer userId) {
		Connection conn = ConnectManager.getConnection();
		List<App> list = null;
		String sql = "select s.software_name as softwareName,count(r.report_id)as runNum from tb_report r,tb_software s "
				+ " where r.user_id = ? and r.flag=0 " + " and s.software_id = r.software_id"
				+ " group by r.software_id";
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Entry> getAppRunEachMonthByUId(Integer userId) {
		List<Entry> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(r.create_date,7) as skey ,count(r.report_id) as svalue "
				+ " from tb_report r where r.flag =0 and r.user_id =? and r.create_date is not null " + " group by skey"
				+ " order by skey ";
		try {
			ResultSetHandler<List<Entry>> rsh = new BeanListHandler<Entry>(Entry.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Entry> getAppRunEachWeekByUId(Integer userId) {
		List<Entry> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10) as skey ,count(r.report_id) as svalue "
				+ " from tb_report r where r.flag =0 and r.user_id =? and r.create_date is not null " + " group by skey"
				+ " order by skey ";
		try {
			ResultSetHandler<List<Entry>> rsh = new BeanListHandler<Entry>(Entry.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DataFile> getUploadFileMonth(Integer userId) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT left(f.create_date,7)as yearMonth,count(f.file_id) as fileNum,sum(f.size) as size "
				+ " FROM tb_file f" + " where f.user_id = ? " + " group by yearMonth";
		try {
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<DataFile>(DataFile.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DataFile> getUploadFileWeek(Integer userId) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT left(date_add(f.create_date ,INTERVAL -weekday(f.create_date ) day),10)as weekDate,count(f.file_id) as fileNum,sum(f.size) as size "
				+ " FROM tb_file f" + " where f.user_id = ? " + " group by weekDate";
		try {
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<DataFile>(DataFile.class);
			list = qr.query(conn, sql, rsh, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> getLoginUserSortWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(log.log_date,10) as weekDate, log.user_name as userName,count(log.user_name)as logNum"
				+ " from  tb_log log, tb_user u " + " where log.user_name = u.username and log.log_date between ? and ?"
				// + SqlController.whereCompany(role, cmpId)
				+ SqlController.notUserId("u", noUserid) + SqlController.whereIdNotIn("u", "user_id", uids)
				+ " group by weekDate,log.user_name order by weekDate ,logNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> getLoginUserSortMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<LoginLog> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select left(date_add(log.log_date ,INTERVAL -weekday(log.log_date ) day),7) as yearMonth, log.user_name as userName,count(log.user_name)as logNum "
				+ " from  tb_log log,tb_user u " + " where log.user_name = u.username and log.log_date between ? and ?"
				// + SqlController.whereCompany(role, cmpId)
				+ SqlController.notUserId("u", noUserid) + SqlController.whereIdNotIn("u", "user_id", uids)
				+ " group by yearMonth,log.user_name order by yearMonth ,logNum desc";
		log.info("query:" + sql);
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DataFile> getFileMonthInDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT  left(f.create_date,7)as yearMonth,u.username as userName,count(f.file_id)as fileNum,sum(f.size)  as size "
				+ " FROM tb_file f left join tb_user u on f.user_id = u.user_id" + " where u.user_id is not null"
				// + SqlController.whereCompany(role, cmpId)
				+ SqlController.notUserId("u", noUserid) + SqlController.whereIdNotIn("u", "user_id", uids)
				+ " and f.create_date between ? and ?" + " group by yearMonth,f.user_id";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<DataFile>(DataFile.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<DataFile> getFileInWeekDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT  left(date_add(f.create_date ,INTERVAL -weekday(f.create_date ) day),10) as weekDate,u.username as userName,count(f.file_id)as fileNum,sum(f.size) as size "
				+ " FROM tb_file f left join tb_user u on f.user_id = u.user_id" + " where u.user_id is not null"
				// + SqlController.whereCompany(role, cmpId)
				+ SqlController.notUserId("u", noUserid) + SqlController.whereIdNotIn("u", "user_id", uids)
				+ " and f.create_date between ? and ?" + " group by weekDate,f.user_id"
				+ " order by weekDate desc,fileNum desc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<DataFile>(DataFile.class);
			list = qr.query(conn, sql, rsh, start, end);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<App> getAppRunInWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select u.username as userName,left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,count(r.report_id) as runNum from tb_report r left join tb_user u on r.user_id = u.user_id"
				+ " where r.create_date is not null and r.flag = 0"
				// + SqlController.whereCompany(role, cmpId)
				+ SqlController.whereIdNotIn("r", " user_id", uids) + SqlController.notUserId("u", noUserid)
				+ " and r.create_date between ? and ?" + " group by r.user_id" + " order by weekDate ,runNum desc";
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
	public List<App> getAppRunInMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select u.username as userName,left(r.create_date,7)as yearMonth,count(r.report_id) as runNum "
				+ "from tb_report r left join tb_user u on r.user_id = u.user_id"
				+ " where r.create_date is not null and r.flag = 0"
				+ SqlController.whereCompany("r", "company_id", role, cmpId)
				+ SqlController.whereIdNotIn("r", " user_id", uids) + SqlController.notUserId("u", noUserid)
				+ " and r.create_date between ? and ?" + " group by r.user_id" + " order by yearMonth ,runNum desc";
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
	public List<User> getUserByCompany(Integer companyId, Integer role) {
		List<User> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select u.user_id as userId,u.username,u.email,u.cellphone,u.create_date,u.type,u.theme,u.role,u.remark,"
				+ "u.navigation,u.truename,u.state,u.dept_id as deptId,c.company_id,c.company_name"
				+ " from tb_user u ,tb_dept d,tb_company c "
				+ "where u.dept_id = d.dept_id and d.company_id = c.company_id ";
		// + SqlController.whereCompany("u", role, companyId);
		try {
			ResultSetHandler<List<User>> rsh = new BeanListHandler<User>(User.class);
			log.info("query:" + sql);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}



	@Override
	public List<TotalCount> getCountInHistory() {
		List<TotalCount> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select tb1.weekDate as time, tb1.logNum, tb2.activityUser,ifnull(tb3.runNum,0) as runNum,ifnull(tb4.activityApp,0) as activityApp,ifnull(tb5.dataSize,0)as dataSize from "

				+ " (select  left(date_add(l.log_date ,INTERVAL -weekday(l.log_date ) day),10)as weekDate,count(l.user_name)logNum "
				+ " from tb_log l " + SqlController.notUserName("l", "user_name",noUsername)
				+ " group by weekDate) tb1" + " left join"
				+ " (select  left(date_add(l.log_date ,INTERVAL -weekday(l.log_date ) day),10)as weekDate,count(DISTINCT(l.user_name))as activityUser"
				+ " from tb_log l " + SqlController.notUserName("l", "user_name", noUsername)
				+ " group by weekDate)tb2" + " on tb1.weekDate = tb2.weekDate" + " left join"
				+ " (select left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,count(r.report_id)as runNum from tb_report r "
				+ " on tb1.weekDate = tb3.weekDate" + " left join"
				+ " (select left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate,count(distinct(r.app_id))as activityApp from tb_report r "
				+ " on tb1.weekDate = tb4.weekDate" + " left join"
				+ " (select left(date_add(f.create_date ,INTERVAL -weekday(f.create_date ) day),10)as weekDate,sum(ifnull(f.size,0))as dataSize from tb_file f"
				+ " on tb1.weekDate = tb5.weekDate" + " order by tb1.weekDate desc " + " limit 24";

		log.info("query:" + sql);
		try {
			ResultSetHandler<List<TotalCount>> rsh = new BeanListHandler<TotalCount>(TotalCount.class);
			log.info("query:" + sql);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	@Override
	public List<DataFile> getUserFileSize(Connection conn, int role, int cmpId, Date start, Date end, int topN) {
		List<DataFile> list = null;
		String sql = "select sum(f.size)as size,u.user_id,u.username as user_name "
				+ " from tb_file f,tb_user u,tb_user_company_relat uc  where f.user_id = u.user_id and u.company_id !=0 and f.state =0 and f.create_date between  ? and  ?  and u.user_id = uc.user_id "
				+ SqlController.notUserId("f", noUserid)
				+ SqlController.whereCompany("uc", "company_id", role, cmpId) + " group by f.user_id order by size desc"
				+ SqlController.limit(topN);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, start);
			ps.setObject(2, end);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<>(DataFile.class);
			list = rsh.handle(rs);
		} catch (Exception e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<DataFile> getUserFileNum(Connection conn, int role, int cmpId, Date start, Date end, int topN) {
		List<DataFile> list = null;
		String sql = "select count(f.file_id)as fileNum,sum(ifnull(f.size,0))as size,u.company_id,u.username as user_name,u.create_date,"
				+ " (select count(report_id) from tb_report where user_id = f.user_id and flag =0 )as runNum "
				+ " from tb_file f,tb_user u,tb_user_company_relat uc  where f.user_id = u.user_id and u.company_id !=0 and f.state =0 and f.create_date between  ? and  ?  and u.user_id = uc.user_id "
				+ SqlController.notUserId("f", noUserid)
				+ SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by f.user_id order by fileNum desc" + SqlController.limit(topN);
		LogUtil.info(log, sql);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, start);
			ps.setObject(2, end);
			ResultSet rs = ps.executeQuery();
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<>(DataFile.class);
			list = rsh.handle(rs);
		} catch (Exception e) {
			LogUtil.query(log, sql, e);
			ConnectManager.close(conn);
		}
		return list;
	}

	@Override
	public List<App> getUserRunApp(Connection conn, int role, int cmpId, Date start, Date end, int topN) {
		List<App> list = null;
		String sql = "select count(1)as runNum,r.user_id,(select username from tb_user where user_id = r.user_id)user_name "
				+" from tb_report r where r.flag=0 and r.create_date between  ? and  ?  "
				+ SqlController.notUserId("r", noUserid)
				+ " group by r.user_id order by runNum desc"
				+SqlController.limit(topN);
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
