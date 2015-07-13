package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.constants.DataState;
import com.nova.dao.IDeptDao;
import com.nova.sdo.Dept;
import com.nova.utils.ConnectManager;

public class DeptDaoImpl implements IDeptDao {
	Logger log = Logger.getLogger(CompanyDaoImpl.class);
	@Override
	public List<Dept> getDeptAll(Integer companyId) {
		String sql = "select * from tb_dept where company_id=? and state=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Dept> list = new ArrayList<Dept>();
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			ps.setInt(2, DataState.ACTIVE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setDeptId(rs.getInt("dept_id"));
				dept.setDeptName(rs.getString("dept_name"));
				dept.setEnglishName(rs.getString("english_name"));
				dept.setTel(rs.getString("tel"));
				dept.setDeptIcon(rs.getString("dept_icon"));
				dept.setCompanyId(rs.getInt("company_id"));
				dept.setState(rs.getInt("state"));
				list.add(dept);
			}
			log.info("查询公司下全部部门信息成功");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("查询公司下全部部门信息失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public Dept getDept(Integer deptId) {
		String sql = "select * from tb_dept where dept_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Dept dept = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deptId);
			rs = ps.executeQuery();
			while (rs.next()) {
				dept = new Dept();
				dept.setDeptId(rs.getInt("dept_id"));
				dept.setDeptName(rs.getString("dept_name"));
				dept.setEnglishName(rs.getString("english_name"));
				dept.setTel(rs.getString("tel"));
				dept.setDeptIcon(rs.getString("dept_icon"));
				dept.setCompanyId(rs.getInt("company_id"));
				dept.setState(rs.getInt("state"));
			}
			log.info("单查部门信息成功");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("单查部门信息失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dept;
	}

	@Override
	public Integer add(Dept dept) {
		String sql = "insert into tb_dept (dept_name,english_name,tel,dept_icon,company_id) values (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Integer flag = 0;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getDeptName());
			ps.setString(2, dept.getEnglishName());
			ps.setString(3, dept.getTel());
			ps.setString(4, dept.getDeptIcon());
			ps.setInt(5, dept.getCompanyId());
			flag = ps.executeUpdate();
			log.info("单查部门信息成功");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("单查部门信息失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public Integer updateState(Dept dept) {
		Connection conn = null;
		PreparedStatement ps = null;
		Integer flag = 0;
		String sql = "update tb_dept set state=? where dept_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dept.getState());
			ps.setInt(2, dept.getDeptId());
			flag = ps.executeUpdate();
			log.info("修改部门状态成功");
		} catch (SQLException e) {
			log.error("修改部门状态失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public Integer updateDept(Dept dept) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		String sql = "update tb_dept set dept_name=?,english_name=?,tel=?,dept_icon=? where dept_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getDeptName());
			ps.setString(2, dept.getEnglishName());
			ps.setString(3, dept.getTel());
			ps.setString(4, dept.getDeptIcon());
			ps.setInt(5, dept.getDeptId());
			flag = ps.executeUpdate();
			log.info("修改公司信息成功");
		} catch (SQLException e) {
			log.error("修改公司信息失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
}