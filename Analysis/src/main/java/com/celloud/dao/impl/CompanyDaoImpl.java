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
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.CompanyDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.LogUtil;
import com.celloud.utils.PropertiesUtil;
import com.celloud.utils.SqlController;

public class CompanyDaoImpl implements CompanyDao {
	Logger log = Logger.getLogger(CompanyDaoImpl.class);
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;

	@Override
	public Object getBigUserCompanyNum(Connection conn, Integer companyId, int role) {
		String sql = "select count(distinct(c.company_id))cmpNum from tb_company c left join tb_user u on u.company_id = c.company_id "
				+ " left join tb_user_company_relat uc on u.user_id = uc.user_id  " + "where  u.user_id not in ("
				+ noUserid + ") " + SqlController.whereCompany("uc", "company_id", role, companyId);
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
	public List<Map<String, Object>> getBigUserCompanyNumByAddr(Integer companyId) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select company_id,company_name,address,count(company_id) num from (select c.company_id,c.company_name,c.address from tb_user u,tb_dept d,tb_company c where u.dept_id=d.dept_id and c.state=0 and c.company_id=d.company_id and u.company_id=? and u.user_id not in ("
				+ noUserid + ") group by c.company_id)t group by address;";
		LogUtil.info(log, sql);
		try {
			list = qr.query(conn, sql, new MapListHandler(), companyId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
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
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId, Integer role) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select count(t.company_id) num,left(t.create_date,7) createDate from "
				+ " (select distinct(c.company_id),DATE_FORMAT(c.create_date,'%Y-%m')as create_date from tb_user u,tb_company c,tb_user_company_relat uc "
				+ " where u.company_id = c.company_id and u.user_id = uc.user_id and c.state=0  "
				+ SqlController.whereCompany("uc", "company_id", role, companyId)
				+ SqlController.notUserId("u", noUserid) + ") t group by createDate";
		LogUtil.info(log, sql);

		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<Company> getCompanyDetailById(Integer companyId, Integer role, String orderBy) {
		Connection conn = ConnectManager.getConnection();
		String sql = "select c.company_name,c.tel,c.address,c.create_date,u.company_id, count(f.file_id) as fileNum,sum(f.size) as size"
				+ " from tb_user u,tb_file f,tb_company c,tb_user_company_relat uc "
				+ "  where  u.user_id = f.user_id and u.company_id = c.company_id and u.user_id = uc.user_id and u.user_id not in (9,12,15,16,18,20,21,23,24,27,28,71) "
				+ SqlController.whereCompany("uc", "company_id", role, companyId) + " group by u.company_id "
				+ SqlController.orderBy(orderBy);
		LogUtil.info(log, sql);
		List<Company> list = null;
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public Company getCompanyById(Integer compId) {
		Connection conn = ConnectManager.getConnection();
		String sql = "SELECT u.company_id,c.company_name,c.address,c.tel,c.create_date,count(distinct(u.user_id))userNum,count(distinct(d.dept_id))as deptNum,"
				+ " group_concat(distinct(u.username))as userNames,group_concat(distinct(d.dept_name)) as deptNames,"
				+ "(select count(tf.file_id) from tb_file tf,tb_user tu where tu.company_id = u.company_id and tu.user_id = tf.user_id and tf.state=0)as fileNum, "
				+ "(select sum(ifnull(tf.size,0)) from tb_file tf,tb_user tu where tu.company_id = u.company_id and tu.user_id = tf.user_id and tf.state=0)as size, "
				+ " (select count(tr.report_id) FROM tb_report tr,tb_user tu2 where tr.user_id = tu2.user_id and tr.flag =0 and tu2.company_id = u.company_id) as reportNum "
				+ " FROM tb_user u,tb_dept d,tb_company c where u.state =0 and u.user_id not in (" + noUserid
				+ ") and  u.company_id=? and  c.company_id = u.company_id and u.dept_id = d.dept_id  group by u.company_id";
		Company com = null;
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<Company> rsh = new BeanHandler<Company>(Company.class);
			com = qr.query(conn, sql, rsh, compId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return com;
	}

	@Override
	public List<Map<String, Object>> getProvince(Integer companyId, int role) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select c.province,count(distinct(u.company_id)) as num from tb_user u,tb_company c, tb_user_company_relat uc where "
				+ " u.company_id = c.company_id and u.user_id = uc.user_id and u.user_id not in (" + noUserid + ") "
				+ SqlController.whereCompany("uc", "company_id", role, companyId) + "group by province";
		LogUtil.info(log, sql);
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<App> getCompanyRunAppNumByCId(Integer companyId) {
		List<App> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select r.app_id,(select app_name from tb_app where app_id = r.app_id)as app_name,"
				+ " count(r.report_id)as runNum "
				+ " from tb_report r,tb_user_company_relat uc where r.flag=0 and r.user_id = uc.user_id "
				+ SqlController.notUserId("r", noUserid) + " and uc.company_id = ? "
				+ " group by uc.company_id,r.app_id ";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<App>> rsh = new BeanListHandler<App>(App.class);
			list = qr.query(conn, sql, rsh, companyId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<DataFile> getCompanyUpLoadDataByCId(Integer companyId, String groupBytag) {
		List<DataFile> list = null;
		Connection conn = ConnectManager.getConnection();

		String group = "";// 按不同的字段分组
		switch (groupBytag) {
		case "month":
			group = " group by yearMonth";
			break;
		case "week":
			group = " group by weekDate";
			break;
		}
		String sql = "select uf.create_date as yearMonth,sum(uf.num) as fileNum,sum(uf.size) as size,left(date_add(uf.weekDate ,INTERVAL -weekday(uf.weekDate ) day),10) as weekDate from"
				+ " (select u.user_id,c.company_id from tb_user u,tb_dept d, tb_company c"
				+ " where u.dept_id = d.dept_id and d.company_id = c.company_id and c.company_id = ?"
				+ " and u.user_id not in(	" + noUserid + " ))uc,"
				+ " (SELECT f.user_id,count(f.user_id) as num,left(f.create_date,7) as create_date,sum(f.size)as size,f.create_date as weekDate FROM tb_file f group by f.user_id,left(f.create_date,7))uf"
				+ " where uc.user_id = uf.user_id" + group + " order by uf.create_date";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<DataFile>> rsh = new BeanListHandler<DataFile>(DataFile.class);
			list = qr.query(conn, sql, rsh, companyId);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<Company> getCompanyClient(Integer cmpId, Integer role) {
		List<Company> list = null;
		Connection conn = ConnectManager.getConnection();

		String sql = "select distinct(c.company_id),c.company_name   from tb_company c,tb_dept d,tb_user u"
				+ "	where c.company_id = d.company_id and d.dept_id = u.dept_id";
		// + SqlController.whereCompany(role, cmpId);
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			list = qr.query(conn, sql, rsh);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<Company> BigUserList(Connection conn) {
		List<Company> list = null;
		String sql = "select uc.company_id,c.company_name,c.create_date,count(distinct(u.user_id))as userNum,count(distinct(u.company_id))as companyNum,"
				+ " sum(ifnull(p.data_num,0))as runNum "
				+ " from tb_user_company_relat uc left join tb_user u on uc.user_id = u.user_id "
				+ " left join tb_company c on uc.company_id = c.company_id "
				+ " left join tb_project p on uc.user_id = p.user_id "
				+ " where uc.company_id!=0 and c.state=0 and u.state=0 " + SqlController.notUserId("uc", noUserid)
				+ " group by uc.company_id";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			list = rsh.handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<DataFile> getCompanyFileNum(Connection conn, int role, int cmpId, Date start, Date end, int topN) {
		List<DataFile> list = null;
		String sql = "select count(f.file_id)as fileNum,sum(ifnull(f.size,0))as size,u.company_id,"
				+ " (select company_name from tb_company where company_id = u.company_id)as company_name,"
				+ "(select create_date from tb_company where company_id = u.company_id)as create_date "
				+ " from tb_file f,tb_user u,tb_user_company_relat uc  where f.state=0 and f.user_id = u.user_id and u.company_id !=0 and left(f.create_date,10) between  left(?,10) and  left(?,10)  and u.user_id = uc.user_id "
				+ SqlController.notUserId("f", noUserid) + SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by u.company_id order by fileNum desc" + SqlController.limit(topN);
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
	public List<DataFile> getCompanyFileSize(Connection conn, int role, int cmpId, Date start, Date end, int topN) {
		List<DataFile> list = null;
		String sql = "select sum(f.size)as size,u.company_id,(select company_name from tb_company where company_id = u.company_id)as company_name "
				+ " from tb_file f,tb_user u,tb_user_company_relat uc  where f.state=0 and f.user_id = u.user_id and u.company_id !=0 and left(f.create_date,10) between  left(?,10) and  left(?,10)  and u.user_id = uc.user_id "
				+ SqlController.notUserId("f", noUserid) + SqlController.whereCompany("uc", "company_id", role, cmpId)
				+ " group by u.company_id order by size desc" + SqlController.limit(topN);
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
	public List<Map<String, Object>> getCompanys(Integer role, Integer cmpId, String province) {
		List<Map<String, Object>> list = null;
		Connection conn = ConnectManager.getConnection();
		String sql = "select distinct(c.company_id),c.company_name FROM tb_company c,tb_user u,tb_user_company_relat uc "
				+ "where c.company_id = u.company_id and u.user_id = uc.user_id and c.province like \'%" + province
				+ "%\'" + SqlController.whereCompany("uc", "company_id", role, cmpId);

		LogUtil.info(log, sql);
		try {
			list = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
        } finally {
            ConnectManager.close(conn);
        }
		return list;
	}

	@Override
	public List<Company> getBigUserCmpNum(Connection conn) {
		List<Company> list = null;
		String sql = "select count(distinct(c.company_id))as num,left(c.create_date,7)as yearMonth,uc.company_id,cname.company_name  from tb_company c,tb_user u,tb_user_company_relat uc,tb_company cname "
				+ " where c.company_id = u.company_id and u.user_id = uc.user_id and uc.company_id = cname.company_id"
				+ " group by uc.company_id,yearMonth " + " order by yearMonth asc";
		LogUtil.info(log, sql);
		try {
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			list = rsh.handle(rs);

		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}

	@Override
	public List<Company> getCompanyBaseInfo(Connection conn) {
		List<Company> list = null;
		String sql = "select distinct(c.company_id)as company_id,c.company_name as company_name ,c.create_date,c.address,"
				+ " count(distinct(u.user_id))as userNum,uc.company_id as bigCmpId,big.company_name as belowCompany "
				+ " from tb_company c left join tb_user u on c.company_id = u.company_id "
				+ " left join tb_user_company_relat uc on uc.user_id = u.user_id "
				+ " left join tb_company big on uc.company_id = big.company_id " + " where uc.company_id is not null "
				+ SqlController.notUserId("u", noUserid) + SqlController.notUserId("uc", noUserid)
				+ " and c.company_id not in (select company_id from tb_user_company_relat) "
				+ " group by uc.company_id, c.company_id ";
		LogUtil.info(log, sql);
		try {
			// list = qr.query(conn, sql, new MapListHandler());
			ResultSetHandler<List<Company>> rsh = new BeanListHandler<Company>(Company.class);
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			list = rsh.handle(rs);
		} catch (SQLException e) {
			LogUtil.query(log, sql, e);
		}
		return list;
	}
}
