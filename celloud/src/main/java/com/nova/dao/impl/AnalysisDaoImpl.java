package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nova.dao.IAnalysisDao;
import com.nova.utils.ConnectManager;

public class AnalysisDaoImpl extends BaseDao implements IAnalysisDao {
	Logger log = Logger.getLogger(AnalysisDaoImpl.class);


	@Override
	public List<Map<String, String>> getUserList(Integer companyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String sql = "select a.*,count(f.file_id) as num,IFNULL(sum(f.size),0) as size from (select u.user_id,u.username,company_name from tb_user u,tb_dept d,tb_company c where u.company_id = ? and u.dept_id = d.dept_id and d.company_id = c.company_id and u.username not in ('lin','ui','zhangjw','xiawt','lqx','zl','liushasha','xinglx','yangkai','demo','denghh','administrator','test','lintest')) as a left join (select * from tb_file where state = 0) f on a.user_id = f.user_id group by username order by company_name;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", rs.getString("user_id"));
				map.put("username", rs.getString("username"));
				map.put("company", rs.getString("company_name"));
				map.put("num", rs.getString("num"));
				map.put("totalSize", rs.getString("size"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getUserDataList(Integer companyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String sql = "select left(f.create_date,7) as mounth,count(f.file_id) as num from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? group by mounth order by mounth;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("mounth", rs.getString("mounth"));
				map.put("num", rs.getString("num"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getUserData(Integer userId,
			Integer companyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String sql = "select u.user_id,u.username,company_name,left(f.create_date,7) as mounth,count(f.file_id) as num from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? and u.user_id = ? group by mounth order by mounth;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", rs.getString("user_id"));
				map.put("username", rs.getString("username"));
				map.put("company", rs.getString("company_name"));
				map.put("mounth", rs.getString("mounth"));
				map.put("num", rs.getString("num"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getDataList(Integer userId, String mounth,
			Integer companyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String sql = "select f.file_id,f.file_name,f.create_date,f.size from tb_file f,tb_user u,tb_dept d,tb_company c where f.state = 0 and f.user_id = u.user_id and u.dept_id = d.dept_id and d.company_id = c.company_id and u.company_id = ? and u.user_id = ? and left(f.create_date,7) =?;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			ps.setInt(2, userId);
			ps.setString(3, mounth);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("fileId", rs.getString("file_id"));
				map.put("fileName", rs.getString("file_name"));
				map.put("create_date", rs.getString("create_date"));
				map.put("totalSize", rs.getString("size"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}
}
