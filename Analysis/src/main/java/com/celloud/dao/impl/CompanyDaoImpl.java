package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.CompanyDao;
import com.celloud.sdo.Company;
import com.celloud.sdo.Data;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class CompanyDaoImpl implements CompanyDao {
	Logger log = Logger.getLogger(CompanyDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public Object getBigUserCompanyNum(Integer companyId,int role) {
		Connection conn = ConnectManager.getConnection();
		Map<String, Object> map = null;
		String sql = "select count(*) num from (select c.company_name from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id"
			//	+ " and u.company_id=? "
				+ SqlController.whereCompany(role, companyId)
				+ " and u.user_id not in ("
				+ noUserid + ") group by c.company_id) t";
		LogUtil.info(log, sql);
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return map.get("num");
	}

	@Override
	public List<Map<String, Object>> getBigUserCompanyNumByAddr(Integer companyId) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select company_id,company_name,address,count(company_id) num from (select c.company_id,c.company_name,c.address from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id and u.company_id=? and u.user_id not in ("
				+ noUserid + ") group by c.company_id)t group by address;";
		LogUtil.info(log, sql);
		try {
			list = qr.query(conn , sql, new MapListHandler(), companyId);
		} catch (SQLException e) { 
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Company> getCompanyByAddr(String addr) {
		List<Company> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select user_name as userName,count(*) as logNum from tb_log where 1=1";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId,Integer role) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select count(*) num,left(create_date,7) createDate from "
				+     	" (select c.company_name,c.create_date "
				+ 	 	" from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id "
				+ SqlController.whereCompany("u", role, companyId)
				+ SqlController.notUserId(role, noUserid)
						+ "group by c.company_id) t group by createDate";
		LogUtil.info(log, sql);

		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Company> getCompanyDetailById(Integer companyId,Integer role) {
		Connection conn = ConnectManager.getConnection();
		String sql = "select a.company_id,a.company_name,a.address,a.tel,a.create_date,count(username) userNum,sum(a.fnum) fileNum,sum(a.fsize) fileSize"
				+ ",sum(a.rnum) reportNum from "
				+ "(select c.company_id,c.company_name,c.address,c.tel,c.create_date ,(select count(f.file_id) from tb_file f where f.user_id=u.user_id and f.state=0) fnum,"
				+ "(select ifnull(sum(f.size),0) from tb_file f where f.user_id=u.user_id and f.state=0) fsize,"
				+ "(select count(*) from tb_report r where r.user_id=u.user_id and r.isdel=0 and (r.flag=0 or r.report_id=11)) rnum,"
				+ "u.username from tb_company c,tb_dept d,tb_user u where c.company_id=d.company_id and d.dept_id=u.dept_id and c.state=0 and u.state=0 "
				+ SqlController.notUserId(role, noUserid)
				+SqlController.whereCompany(role, companyId)
				+" )a  group by a.company_id order by a.fsize desc; ";
		LogUtil.info(log, sql);
		List<Company> list = null;
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public Company getCompanyById(Integer compId) {
		Connection conn = ConnectManager.getConnection();
		String sql = "select  company_id,company_name,address,tel,create_date, count(id.user_id) userNum,group_concat(username) userNames,group_concat(distinct dept_name) deptNames,sum(fileNum) fileNum,sum(fileSize) fileSize,sum(reportNum) reportNum    from (select a.user_id,a.username,d.dept_name,c.company_id,c.company_name,c.address,c.tel,c.create_date from tb_company c,tb_dept d,tb_user a  where c.company_id=d.company_id and a.dept_id=d.dept_id and a.state=0 and a.user_id not in ("
				+ noUserid
				+ ") and c.company_id=?) id left join  (select user_id,count(file_id) fileNum,ifnull(sum(size),0) fileSize from tb_file where state = 0 group by user_id) f on f.user_id=id.user_id left join  (select user_id,count(report_id) reportNum from tb_report where isdel = 0 and (flag = 0 or software_id = 11) group by user_id) r on r.user_id=id.user_id";
		Company com = null;
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<Company> rsh = new BeanHandler<Company>(Company.class);
			com = qr.query(conn, sql, rsh, compId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return com;
	}

	@Override
	public List<Map<String, Object>> getProvince(Integer companyId,int role) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select province,count(distinct c.company_id) as num from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and d.company_id=c.company_id and u.user_id not in ("
						+ noUserid + ") "
						+ SqlController.whereCompany(role, companyId)
						+ "group by province";
		LogUtil.info(log, sql);

		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Software> getCompanyRunAppNumByCId(Integer companyId,String groupByTag) {
		List<Software> list =null;
		Connection conn = ConnectManager.getConnection();
		String group="";//按不同的字段分组
		switch(groupByTag){
		case "month":
			group = " group by left(r.create_date,7)";
			break;
		case "software":
			group = " group by s.software_id";
			break;
		case "week":
			group = "group by weekDate";
			break;
		}
		String sql =" select "
			    +" count(r.user_id) as runNum, s.software_id as softwareId, software_name as softwareName,left(r.create_date,7) as yearMonth,left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10) as weekDate"
			    +" from"
			    +"( SELECT" 
			    +" u.user_id, c.company_id"
			    +" FROM"
			    +" tb_user u, tb_dept d, tb_company c"
			    + " where"
			    +" u.dept_id = d.dept_id"
			    +" and d.company_id = c.company_id"
			    +" and c.company_id = ?"
			    + " and u.user_id not in("
			    +	 noUserid
			    +")) uc,"
			    +" tb_report r,"
			    +" tb_software s"
			    +" where"
			    +" r.user_id = uc.user_id"
			    +" and s.software_id = r.software_id"
			    +" and r.create_date is not null"
			    +" and r.flag=0 "
			    +group;
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, companyId);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Data> getCompanyUpLoadDataByCId(Integer companyId,String groupBytag) {
		List<Data> list =null;
		Connection conn = ConnectManager.getConnection();

		String group="";//按不同的字段分组
		switch(groupBytag){
		case "month":
			group = " group by yearMonth";
			break;
		case "week":
			group = " group by weekDate";
			break;
		}
		String sql="select uf.create_date as yearMonth,sum(uf.num) as fileNum,sum(uf.size) as size,left(date_add(uf.weekDate ,INTERVAL -weekday(uf.weekDate ) day),10) as weekDate from"
				+		" (select u.user_id,c.company_id from tb_user u,tb_dept d, tb_company c"
				+		" where u.dept_id = d.dept_id and d.company_id = c.company_id and c.company_id = ?"
				+ 		" and u.user_id not in(	"
				+		noUserid
				+		 " ))uc,"
				+		" (SELECT f.user_id,count(f.user_id) as num,left(f.create_date,7) as create_date,sum(f.size)as size,f.create_date as weekDate FROM tb_file f group by f.user_id,left(f.create_date,7))uf"
				+	    " where uc.user_id = uf.user_id"
				+		group
				+		" order by uf.create_date";
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<Data>> rsh = new BeanListHandler<Data>(Data.class);
			list = qr.query(conn, sql, rsh, companyId);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}
	@Override
	public List<LoginLog> getCompanyLoginInWeek(Integer cmpId, Date start, Date end, List<Integer> cmpIdList,Integer role) {
		List<LoginLog> list =null;
		Connection conn = ConnectManager.getConnection();

		String sql="select uc.company_name as companyName,sum(tu.logNum) as logNum,tu.weekDate as weekDate from"

							+" (select l.user_name,count(l.user_name)as logNum,left(date_add(l.log_date ,INTERVAL -weekday(l.log_date ) day),10)as weekDate"   ////////table 1
							+ " from tb_log l "
							+ " where l.user_name is not null"
							+ " and l.log_date between ? and ? "					
							+ "  group by l.user_name,weekDate) tu,"
							
							+ " (select u.username,company_name "    	////table2
							+ " from tb_user u,tb_dept d, tb_company c "
							+ " where u.dept_id = d.dept_id "
							+ " and d.company_id = c.company_id"
							+	SqlController.whereIdNotIn("c"," company_id", cmpIdList)
							+  SqlController.whereCompany(role, cmpId)
							+ 	SqlController.notUserId(role, noUserid)	//////排除测试用户
							+ ") uc"
							
							+ " where tu.user_name = uc.username"
							+ " group by weekDate, companyName"
							+ " order by weekDate ,logNum desc";
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start,end);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<LoginLog> getCompanyLoginInMonth(Integer cmpId, Date start, Date end, List<Integer> companyLis,Integer role) {
		List<LoginLog> list =null;
		Connection conn = ConnectManager.getConnection();

		String sql="select uc.company_name as companyName,sum(tu.logNum) as logNum,tu.yearMonth as yearMonth from"
							+" (select l.user_name,count(l.user_name)as logNum,left(l.log_date,7)as yearMonth"   ////////table 1
							+ " from tb_log l "
							+ " where l.user_name is not null"
							+ " and l.log_date between ? and ? "	
							+ "  group by l.user_name,yearMonth) tu,"
							
							+ " (select u.username,company_name "    	////table2
							+ " from tb_user u,tb_dept d, tb_company c "
							+ " where u.dept_id = d.dept_id "
							+	SqlController.whereIdNotIn("c"," company_id", companyLis)
							+ " and d.company_id = c.company_id"
							+ SqlController.whereCompany(role, cmpId)
							+ 	SqlController.notUserId(role, noUserid)	
							 + ") uc"
							
							+ " where tu.user_name = uc.username"
							+ " group by yearMonth, companyName"
							+ " order by yearMonth ,logNum desc";
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<LoginLog>> rsh = new BeanListHandler<LoginLog>(LoginLog.class);
			list = qr.query(conn, sql, rsh, start,end);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Data> getCompanyFileInWeek(Integer cmpId, Date start, Date end, List<Integer> cmpIdList,Integer role) {
		List<Data> list =null;
		Connection conn = ConnectManager.getConnection();

		LogUtil.info(log, SqlController.whereIdNotIn("c", "company_id", cmpIdList));
		String sql="select uc.company_name as company_name,sum(f.fileNum) as fileNum, sum(f.size) as size, f.weekDate  from"

						+" (SELECT f.user_id,count(f.file_id) as fileNum,sum(f.size) as size,left(date_add(f.create_date ,INTERVAL -weekday(f.create_date ) day),10)as weekDate"
						+" FROM tb_file f where f.create_date is not null"
						+ " and f.create_date between ? and ? "
						+ 	SqlController.notUserId("f",role, noUserid)	
						+"  group by f.user_id,weekDate) f,"
						
						+" (select u.user_id,u.username,company_name "
						+" from tb_user u,tb_dept d, tb_company c "
						+" where u.dept_id = d.dept_id "
						+ SqlController.whereIdNotIn("c", "company_id", cmpIdList)
						+ SqlController.whereCompany(role, cmpId)
						+" 	and d.company_id = c.company_id) uc"
												
						+" 	where f.user_id = uc.user_id"
						+" 	group by weekDate,company_name"
						+" order by weekDate ,size desc";
		LogUtil.info(log, sql);
		LogUtil.info(log, SqlController.whereIdNotIn("c", "company_id", cmpIdList));

		try{
			ResultSetHandler<List<Data>> rsh = new BeanListHandler<Data>(Data.class);
			list = qr.query(conn, sql, rsh, start,end);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Data> getCompanyFileInMonth(Integer cmpId, Date start, Date end,List<Integer> cmpIdList, Integer role) {
		List<Data> list =null;
		Connection conn = ConnectManager.getConnection();

		String sql="select uc.company_name as company_name,sum(f.fileNum) as fileNum, sum(f.size) as size, f.yearMonth as yearMonth from"
				+" (SELECT f.user_id,count(f.file_id) as fileNum,sum(f.size) as size,left(f.create_date ,7)as yearMonth"
				+"   FROM tb_file f"
				+ " where f.create_date is not null"
				+ " and  f.create_date between ? and ? "
				+ 	SqlController.notUserId("f",role, noUserid)	
				+"  group by f.user_id,yearMonth) f,"
				
				+"  (select u.user_id,u.username,company_name "
				+" from tb_user u,tb_dept d, tb_company c "
				+" where u.dept_id = d.dept_id "
				+ SqlController.whereCompany(role, cmpId)
				+ SqlController.whereIdNotIn("c", "company_id", cmpIdList)
				+" and d.company_id = c.company_id) uc"
				+" where f.user_id = uc.user_id"
				+" group by yearMonth, company_name"
				+" order by yearMonth ,size desc";
		LogUtil.info(log, sql);
		LogUtil.info(log, SqlController.whereIdNotIn("c", "company_id", cmpIdList));

		try{
			ResultSetHandler<List<Data>> rsh = new BeanListHandler<Data>(Data.class);
			list = qr.query(conn, sql, rsh, start,end);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Software> getCompanySoftwareInWeek(Integer cmpId,Date start, Date end, List<Integer> cmpIds,Integer role) {
		List<Software> list =null;
		Connection conn = ConnectManager.getConnection();

		String sql="select uc.company_name as companyName,sum(rn.runNum) as runNum,rn.weekDate as weekDate from"

					+" (SELECT r.user_id,count(r.user_id) as runNum,left(date_add(r.create_date ,INTERVAL -weekday(r.create_date ) day),10)as weekDate FROM tb_report r"
					+" where r.flag =0 "
					+ " and r.create_date between ? and ? "
					+ SqlController.notUserId("r", role, noUserid)													//////排除测试用户
					+" group by r.user_id,weekDate) rn,"
					
					+" (select u.user_id,u.username,company_name "
					+" from tb_user u,tb_dept d, tb_company c "
					+" where u.dept_id = d.dept_id "
					+ SqlController.whereIdNotIn("c", "company_id", cmpIds)
					+ SqlController.whereCompany(role, cmpId)
					+" and d.company_id = c.company_id) uc"
									
					+" where rn.user_id = uc.user_id"
					+" group by weekDate,companyName"
					+" order by weekDate ,runNum desc";
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
			list = qr.query(conn, sql, rsh, start,end);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Software> getCompanySoftwareInMonth(Integer cmpId,Date start, Date end,List<Integer> cmpIds,Integer role) {
		List<Software> list =null;
		Connection conn = ConnectManager.getConnection();

		String sql="select uc.company_name as companyName,sum(rn.runNum) as runNum,rn.yearMonth as yearMonth from"

					+" (SELECT r.user_id,count(r.user_id) as runNum,left(r.create_date,7)as yearMonth FROM tb_report r"
					+" where r.flag =0 "
					+ " and r.create_date between ? and ? "
					+ SqlController.notUserId("r",role, noUserid)														//////排除测试用户
					+"  group by r.user_id,yearMonth) rn,"
					
					+" (select u.user_id,u.username,company_name "
					+" from tb_user u,tb_dept d, tb_company c "
					+" where u.dept_id = d.dept_id "
					+ SqlController.whereCompany(role, cmpId)
					+" and d.company_id = c.company_id) uc"
									
					+" where rn.user_id = uc.user_id"
					+" group by yearMonth, companyName"
					+" order by yearMonth ,runNum desc";
				LogUtil.info(log, sql);
				try{
					ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(Software.class);
					list = qr.query(conn, sql, rsh, start,end);
				}catch(SQLException e){
					LogUtil.query(log, sql, e);
				}
				return list;
	}

	@Override
	public List<Company> getCompanyClient(Integer cmpId, Integer role) {
		List<Company> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql="select distinct(c.company_id),c.company_name   from tb_company c,tb_dept d,tb_user u"
				+"	where c.company_id = d.company_id and d.dept_id = u.dept_id"
				+ SqlController.whereCompany(role, cmpId);
		LogUtil.info(log, sql);
		try{
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			list = qr.query(conn, sql, rsh);
		}catch(SQLException e){
			LogUtil.query(log, sql, e);
		}
		return list;
	}
}
