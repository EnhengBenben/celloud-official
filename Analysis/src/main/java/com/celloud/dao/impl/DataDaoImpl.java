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
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.DataDao;
import com.celloud.sdo.Data;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class DataDaoImpl implements DataDao {
	Logger log = Logger.getLogger(DataDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId,Integer role) {
		Connection conn = ConnectManager.getConnection();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select a.user_id ,a.username ,a.create_date ,a.company_name ,count(f.file_id) as num,"
				+ "IFNULL(sum(f.size),0) as size from (select u.user_id,u.username,u.create_date,company_name from tb_user u,tb_dept d,tb_company c where  u.dept_id = d.dept_id and d.company_id = c.company_id" 
			//	+SqlController.whereCompany(role,companyId)
				+SqlController.notUserId("u",role, noUserid)
				+") as a left join (select * from tb_file where state = 0) f on a.user_id = f.user_id group by username order by username;";
		log.info("Query:"+sql);
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Data> getUserMonthDataList(Integer companyId,Integer role) {
		List<Data> list = new ArrayList<Data>();
		Connection conn = ConnectManager.getConnection();

		String sql = "select left(f.create_date,7) as yearMonth,count(f.file_id) as fileNum,sum(f.size)  as size from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and f.create_date is not null"
				+SqlController.notUserId("f",role, noUserid)
				+ " and u.dept_id = d.dept_id and d.company_id = c.company_id "
			//	+ SqlController.whereCompany(role, companyId) 
				+ "group by yearMonth order by yearMonth desc";
		log.info("Query:"+sql);
		ResultSetHandler<List<Data>> rsh = new BeanListHandler<>(Data.class);
		try {
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Data> getUserMonthData(Integer userId, Integer companyId) {
		List<Data> list = new ArrayList<Data>();	
		Connection conn = ConnectManager.getConnection();
		String sql = "select u.user_id as userId,u.username as userName,company_name as companyName,left(f.create_date,7) as yearMonth,count(f.file_id) as fileNum from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and  u.user_id = ? group by yearMonth,userId  order by yearMonth ";
		ResultSetHandler<List<Data>> rsh = new BeanListHandler<>(Data.class);
		try {
			list = qr.query(conn, sql, rsh,  userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getMonthDataList(Integer userId, String month, Integer companyId) {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select f.file_id,f.file_name,f.create_date,f.size from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? and u.user_id = ? and left(f.create_date,7) =?;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId, userId, month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId, String month) {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select username,company_name,count(file_id) as num from tb_user u,tb_company c,tb_dept d,tb_file f where u.company_id=? and u.dept_id=d.dept_id and d.company_id=c.company_id and f.state=0 and u.user_id=f.user_id and u.user_id not in ("
				+ noUserid + ") and left(f.create_date,7) =? group by u.username order by c.company_name;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId, month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getBigUserDataNum(Integer companyId,int role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select count(f.file_id) num from tb_file f,tb_user u where f.state=0 and f.user_id=u.user_id and u.user_id not in (" + noUserid + ") ";
		//		+ SqlController.whereCompany(role, companyId);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public Object getBigUserDataSize(Integer companyId,int role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select sum(f.size) num from tb_file f,tb_user u where f.state=0 and f.user_id=u.user_id and u.user_id not in (" + noUserid + ") ";
	//			+ SqlController.whereCompany(role, companyId);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds, String start, String end) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = ConnectManager.getConnection();

		String sql = "select res.user_id,res.username,res.file_id,res.data_key,res.file_name,res.create_date,res.path,GROUP_CONCAT(distinct(res.software_name)) as soft from (select f.user_id,u.username,f.file_id,f.data_key,f.file_name,f.create_date,f.path,s.software_name from (select * from tb_file where user_id in ("
				+ userIds
				+ " ) and create_date between '"
				+ start
				+ " 00:00:00' and '"
				+ end
				+ " 23:59:59') f left join tb_report r on r.file_id = f.file_id left join tb_software s on r.software_id = s.software_id left join tb_user u on f.user_id=u.user_id) res GROUP BY res.user_id,res.file_id order by res.user_id,res.file_id;";
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			log.error("query:"+sql+"\n"+e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Data> getUserWeekData(Date start) {
		List<Data> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = " select f.user_id,count(f.file_id)as fileNum, sum(f.size) as size,u.username from tb_file f,tb_user u "
							+ " where u.user_id = f.user_id"
							+ " and left(date_add(f.create_date,INTERVAL -weekday(f.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
							+ " group by f.user_id"
							+ " order by fileNum desc";
		ResultSetHandler<List<Data>> rsh = new BeanListHandler<>(Data.class);
		try {
			list = qr.query(conn, sql, rsh,  start,start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Data> getEachDayData(Date start) {
		List<Data> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = " select left(f.create_date,10)as weekDate,count(f.file_id)as fileNum, sum(f.size) as size,u.username from tb_file f,tb_user u "
							+ " where u.user_id = f.user_id"
							+ " and left(date_add(f.create_date,INTERVAL -weekday(f.create_date) day),10)=left(date_add(?,INTERVAL -weekday(?) day),10)"
							+ " group by weekDate"
							+ " order by weekDate desc";
		ResultSetHandler<List<Data>> rsh = new BeanListHandler<>(Data.class);
		try {
			list = qr.query(conn, sql, rsh,  start,start);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}