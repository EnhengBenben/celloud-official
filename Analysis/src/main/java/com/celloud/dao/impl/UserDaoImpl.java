package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;

public class UserDaoImpl implements UserDao {
	Logger log = Logger.getLogger(UserDaoImpl.class);
	private Connection conn = ConnectManager.getConnection();
	private QueryRunner qr = new QueryRunner();
	private String noUsername = PropertiesUtil.noUsername;
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public List<LoginLog> logCountEveryUser(Date beginDate, Date endDate) {
		log.info("统计指定时间内每个用户登录次数，倒序排序");
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(
					LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select user_name as userName,count(*) as logNum from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername)
					.append(") group by userName order by logNum desc");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("统计指定时间内每个用户登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> logCountEveryDay(Date beginDate, Date endDate) {
		log.info("统计指定时间内所有用户每天登录次数");
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(
					LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select date_format(log_date,'%Y-%c-%d') as logDate,count(*) as logNum from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername)
					.append(") group by logDate");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("统计指定时间内所有用户每天登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LoginLog> logCountEveryBrowser(Date beginDate, Date endDate) {
		log.info("指定时间内所有浏览器的登录次数，倒序排序");
		List<LoginLog> list = null;
		try {
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(
					LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select os,browser from tb_log from tb_log where 1=1");
			if (beginDate != null) {
				sql.append(" and log_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and log_date<'").append(endDate).append("'");
			}
			sql.append(" and user_name not in (").append(noUsername)
					.append(") group by browser");
			list = qr.query(conn, sql.toString(), rsh);
		} catch (SQLException e) {
			log.error("指定时间内所有浏览器的登录次数出错！！！" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer userCount() {
		log.info("统计所有用户数量");
		int count = 0;
		try {
			ResultSetHandler<LoginLog> rsh = new BeanHandler<LoginLog>(
					LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select count(*) as logNum from tb_user where state=0 and role != 3");
			sql.append(" and username not in (").append(noUsername)
					.append(")");
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
		log.info("统计指定时间段新增的用户数量");
		int count = 0;
		try {
			ResultSetHandler<LoginLog> rsh = new BeanHandler<LoginLog>(
					LoginLog.class);
			StringBuffer sql = new StringBuffer(
					"select count(*) as logNum from tb_user where 1=1");
			if (beginDate != null) {
				sql.append(" and create_date>'").append(beginDate).append("'");
			}
			if (endDate != null) {
				sql.append(" and create_date<'").append(endDate).append("'");
			}
			sql.append(" and username not in (").append(noUsername)
					.append(")");
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
		log.info("用户登录");
		String sql = "select * from tb_user where (username=? or email=?) and password=?";
		User user = null;
		try {
			ResultSetHandler<User> rsh = new BeanHandler<User>(User.class);
			user = qr.query(conn, sql.toString(), rsh, username, username,
					password);
		} catch (SQLException e) {
			log.error("用户登录失败！！！" + e);
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Object getBigUsersUserNum(Integer companyId) {
		log.info("获取大客户的所有用户量");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select count(u.user_id) num from tb_user u,tb_dept d,tb_company c where u.state=0 and u.dept_id=d.dept_id and c.company_id=d.company_id and u.user_id not in ("
				+ noUserid + ") and u.company_id=?";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map<String, Object> m = list.get(0);
		return m.get("num");
	}

	@Override
	public List<Map<String, Object>> getBigUsersUser(Integer companyId) {
		log.info("获取大客户的所有客户信息");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from tb_user where state=0 and user_id not in (" + noUserid + ") and company_id=?";
		try {
			list = qr.query(conn,sql, new MapListHandler(),companyId);
		} catch (SQLException e) {
			log.error("获取大客户的所有客户信息出错。"+e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<User> getUserListByBigCom(Integer companyId) {
		log.info("获取大客户的用户信息列表");
		List<User> list = new ArrayList<User>();
		String sql = "select u.username,u.email,u.cellphone,u.create_date createDate,d.dept_name deptName,c.company_name companyName,(select count(f.file_id) from tb_file f where f.user_id=u.user_id and f.state=0) fileNum,(select ifnull(sum(f.size),0) from tb_file f where f.user_id=u.user_id and f.state=0) fileSize,(select count(*) from tb_report r where r.user_id=u.user_id and r.isdel=0 and (r.flag=0 or r.report_id=11)) reportNum from tb_user u left join tb_dept d on u.dept_id=d.dept_id left join tb_company c on d.company_id=c.company_id where u.state=0 and u.user_id not in ("
				+ noUserid + ") and u.company_id=?";
		try {
			ResultSetHandler<List<User>> rsh = new BeanListHandler<User>(
					User.class);
			list = qr.query(conn, sql, rsh, companyId);
		} catch (SQLException e) {
			log.error("获取大客户的用户信息列表:" + e);
			e.printStackTrace();
		}
		return list;
	}
}
