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
import com.celloud.sdo.DataFile;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class DataDaoImpl implements DataDao {
	Logger log = Logger.getLogger(DataDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId, Integer role, String orderType) {
		Connection conn = ConnectManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select f.user_id,sum(ifnull(f.size,0))size,count(f.file_id)as fileNum,u.username,(select company_name "
				+ " from tb_company where company_id = u.company_id)as company_name from tb_file f, tb_user_company_relat uc ,tb_user u"
				+ " where f.user_id = uc.user_id and f.state=0 and f.user_id = u.user_id "
				+ SqlController.whereCompany("uc", "company_id", role, companyId)
				+ SqlController.notUserId("uc", noUserid) + " group by f.user_id "
				+ SqlController.orderBy(orderType);
		LogUtil.info(log, sql);
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<DataFile> getUserMonthDataList(Integer companyId, Integer role) {
		List<DataFile> list = new ArrayList<DataFile>();
		Connection conn = ConnectManager.getConnection();

		String sql = "select left(f.create_date,7) as yearMonth,count(f.file_id) as fileNum,sum(f.size)  as size "
				+ " from tb_file f,tb_user_company_relat uc where  f.state = 0 and f.user_id = uc.user_id and f.create_date is not null "
				+ SqlController.notUserId("f", noUserid)
				+ SqlController.whereCompany("uc", "company_id", role, companyId)
				+ "group by yearMonth order by yearMonth asc";
		LogUtil.info(log, sql);
		ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<>(DataFile.class);
		try {
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<DataFile> getUserMonthData(Integer userId, Integer companyId) {
		List<DataFile> list = new ArrayList<DataFile>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select u.user_id as userId,u.username as userName,company_name as companyName,left(f.create_date,7) as yearMonth,count(f.file_id) as fileNum from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and  u.user_id = ? group by yearMonth,userId  order by yearMonth ";
		ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<>(DataFile.class);
		try {
			list = qr.query(conn, sql, rsh, userId);
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
	public Object getBigUserDataNum(Integer companyId, int role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select count(f.file_id) num from tb_file f, tb_user_company_relat uc where f.state=0 and f.user_id = uc.user_id and f.user_id not in ("
				+ noUserid + ") " + SqlController.whereCompany("uc", "company_id", role, companyId);

		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public Object getBigUserDataSize(Integer companyId, int role) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		String sql = "select sum(f.size) num from tb_file f,tb_user_company_relat uc  where f.state=0 and uc.user_id =f.user_id  and f.user_id not in ("
				+ noUserid + ") " + SqlController.whereCompany("uc", "company_id", role, companyId);
		LogUtil.info(log, sql);
		try {
			map = qr.query(conn, sql,new MapHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return map.get("num");
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds, String start, String end) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = ConnectManager.getConnection();

		String sql = "select res.user_id,res.username,res.file_id,res.data_key,res.file_name,res.create_date,res.path,GROUP_CONCAT(distinct(res.software_name)) as soft from (select f.user_id,u.username,f.file_id,f.data_key,f.file_name,f.create_date,f.path,s.software_name from (select * from tb_file where user_id in ("
				+ userIds + " ) and create_date between '" + start + " 00:00:00' and '" + end
				+ " 23:59:59') f left join tb_report r on r.file_id = f.file_id left join tb_software s on r.software_id = s.software_id left join tb_user u on f.user_id=u.user_id) res GROUP BY res.user_id,res.file_id order by res.user_id,res.file_id;";
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			log.error("query:" + sql + "\n" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DataFile> getBigUserDataFile(Integer cmpId) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = " select left(f.create_date,7)as yearMonth, sum(f.size) as size,count(f.file_id)as fileNum "
				+ " from tb_file f,tb_user_company_relat uc"
				+ " where f.user_id = uc.user_id  and f.state=0 and f.create_date is not null "
				+ SqlController.notUserId("f", noUserid)
				+ " and uc.company_id = ? " 
				+ " group by yearMonth order by yearMonth desc";
		LogUtil.info(log, sql);
		ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<>(DataFile.class);
		try {
			list = qr.query(conn, sql, rsh,cmpId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}
}