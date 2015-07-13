package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.DataDao;
import com.celloud.sdo.Data;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;

public class DataDaoImpl implements DataDao {
	Logger log = Logger.getLogger(DataDaoImpl.class);
	private Connection conn = ConnectManager.getConnection();
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public int dataCount(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Data> dataCountEveryUser(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Data> clientDataCountEveryUser(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId) {
		log.info("获取大客户的所有用户及总上传数据量");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select a.user_id userId,a.username userName,a.create_date createDate,a.company_name companyName,count(f.file_id) as num,IFNULL(sum(f.size),0) as size from (select u.user_id,u.username,u.create_date,company_name from tb_user u,tb_dept d,tb_company c where u.company_id = ? and u.dept_id = d.dept_id and d.company_id = c.company_id and u.user_id not in ("
				+ noUserid
				+ ")) as a left join (select * from tb_file where state = 0) f on a.user_id = f.user_id group by username order by company_name;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getUserMonthDataList(Integer companyId) {
		log.info("获取大客户的所有用户每月上传的数据量");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select left(f.create_date,7) as month,count(f.file_id) as num from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.user_id not in ("
				+ noUserid
				+ ") and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? group by month order by month;";
		try {
			list = qr.query(conn, sql, new MapListHandler(),
					companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getUserMonthData(Integer userId,
			Integer companyId) {
		log.info("获取某个用户每个月的数据量");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select u.user_id,u.username,company_name,left(f.create_date,7) as month,count(f.file_id) as num from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? and u.user_id = ? group by month order by month;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getMonthDataList(Integer userId,
			String month, Integer companyId) {
		log.info("获取某个用户某个月的数据量列表");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select f.file_id,f.file_name,f.create_date,f.size from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? and u.user_id = ? and left(f.create_date,7) =?;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId, userId,
					month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId,
			String month) {
		log.info("获取某个月每个用户的上传量列表");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select username,company_name,count(file_id) as num from tb_user u,tb_company c,tb_dept d,tb_file f where u.company_id=? and u.dept_id=d.dept_id and d.company_id=c.company_id and f.state=0 and u.user_id=f.user_id and u.user_id not in ("
				+ noUserid
				+ ") and left(f.create_date,7) =? group by u.username order by c.company_name;";
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId, month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getBigUserDataNum(Integer companyId) {
		log.info("获取大客户的所有数据量");
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(f.file_id) num from tb_file f,tb_user u where f.state=0 and f.user_id=u.user_id and u.user_id not in ("
				+ noUserid + ") and u.company_id=?";
		try {
			map = qr.query(conn, sql, new MapHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public Object getBigUserDataSize(Integer companyId) {
		log.info("获取大客户的所有数据大小");
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select sum(f.size) num from tb_file f,tb_user u where f.state=0 and f.user_id=u.user_id and u.user_id not in ("
				+ noUserid + ") and u.company_id=?";
		try {
			map = qr.query(conn, sql, new MapHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds,
			String start, String end) {
		log.info("获取用户在一个时间段内上传的文件及运行状态");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select res.user_id,res.username,res.file_id,res.data_key,res.file_name,res.create_date,res.path,GROUP_CONCAT(distinct(res.software_name)) as soft from (select f.user_id,u.username,f.file_id,f.data_key,f.file_name,f.create_date,f.path,s.software_name from (select * from tb_file where user_id in ("
			+userIds+" ) and create_date between '"
			+start+" 00:00:00' and '"
			+end+" 23:59:59') f left join tb_report r on r.file_id = f.file_id left join tb_software s on r.software_id = s.software_id left join tb_user u on f.user_id=u.user_id) res GROUP BY res.user_id,res.file_id order by res.user_id,res.file_id;";
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			log.error("获取用户在一个时间段内上传的文件及运行状态出错。"+e);
			e.printStackTrace();
		}
		return list;
	}
}