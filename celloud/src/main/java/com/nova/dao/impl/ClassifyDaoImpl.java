package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.IClassifyDao;
import com.nova.sdo.Classify;
import com.nova.sdo.Software;
import com.nova.utils.ConnectManager;
import com.nova.utils.Page;

public class ClassifyDaoImpl implements IClassifyDao {
	Logger log = Logger.getLogger(NoticeDaoImpl.class);

	@Override
	public int createClassify(Classify classify) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		try {
			conn = ConnectManager.getConnection();
			String sql = "insert into tb_classify (classify_name,classify_pid,notes) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, classify.getClassifyName());
			ps.setInt(2, classify.getClassifyPid());
			ps.setString(3, classify.getNotes());
			flag = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("新增软件分类失败:" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public int deleteClassify(int classifyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			conn = ConnectManager.getConnection();
			String sql = "delete from tb_classify where classify_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classifyId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("删除软件分类失败：ID--" + classifyId + ";原因" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int updateClassify(Classify classify) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "update tb_classify set classify_name=?,classify_pid=?,notes=? where classify_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, classify.getClassifyName());
			ps.setInt(2, classify.getClassifyPid());
			ps.setString(3, classify.getNotes());
			ps.setInt(4, classify.getClassifyId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("修改软件分类失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public Classify getClassify(int classifyId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Classify classify = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_classify where classify_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classifyId);
			rs = ps.executeQuery();
			if (rs.next()) {
				classify = new Classify();
				classify.setClassifyId(rs.getInt("classify_id"));
				classify.setClassifyName(rs.getString("classify_name"));
				classify.setClassifyPid(rs.getInt("classify_pid"));
				classify.setNotes(rs.getString(("notes")));
			}
		} catch (SQLException e) {
			log.error("查询classifyId=" + classifyId + "失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return classify;
	}

	@Override
	public List<Classify> getAllClassifyList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Classify classify = null;
		List<Classify> list = new ArrayList<Classify>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_classify where classify_pid=0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				classify = new Classify();
				classify.setClassifyId(rs.getInt("classify_id"));
				classify.setClassifyName(rs.getString("classify_name"));
				classify.setClassifyPid(rs.getInt("classify_pid"));
				classify.setNotes(rs.getString("notes"));
				list.add(classify);
			}
		} catch (SQLException e) {
			log.error("查询所有软件分类失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Classify> getPageClassify(Page page) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Classify classify = null;
		List<Classify> list = new ArrayList<Classify>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_classify limit "
					+ (page.getPageNow() - 1) * page.getPageSize() + ","
					+ page.getPageSize();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				classify = new Classify();
				classify.setClassifyId(rs.getInt("classify_id"));
				classify.setClassifyName(rs.getString("classify_name"));
				classify.setClassifyPid(rs.getInt("classify_pid"));
				classify.setNotes(rs.getString("notes"));
				list.add(classify);
			}
		} catch (SQLException e) {
			log.error("查询所有软件分类失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public int getTotalClassify() {
		log.info("统计所有软件分类数目");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select count(*) from tb_classify";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				flag = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("分页查询--计算所有软件数目：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}

	@Override
	public List<Classify> selectChildNode(int classifyId) {
		log.info("查询" + classifyId + "分类下的子分类");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Classify classify = null;
		List<Classify> list = new ArrayList<Classify>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_classify where classify_pid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classifyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				classify = new Classify();
				classify.setClassifyId(rs.getInt("classify_id"));
				classify.setClassifyName(rs.getString("classify_name"));
				classify.setClassifyPid(classifyId);
				classify.setNotes(rs.getString("notes"));
				list.add(classify);
			}
		} catch (SQLException e) {
			log.error("查询软件" + classifyId + "子分类失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<Classify> getAllSubClassifyList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Classify classify = null;
		List<Classify> list = new ArrayList<Classify>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_classify where classify_pid!=0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				classify = new Classify();
				classify.setClassifyId(rs.getInt("classify_id"));
				classify.setClassifyPid(rs.getInt("classify_pid"));
				classify.setClassifyName(rs.getString("classify_name"));
				list.add(classify);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}
	
	@Override
	public List<Software> selectChildSoft(int classifyId) {
		log.info("查询软件分类ID是" + classifyId + "下的所有软件");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Software> list = new ArrayList<Software>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_software where software_id in (select software_id from tb_software_classify_relat where classify_id = ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classifyId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software sf = new Software();
				sf.setSoftwareId(rs.getInt("software_id"));
				sf.setSoftwareName(rs.getString("software_name"));
				sf.setSoftwareEntry(rs.getString("software_entry"));
				sf.setProcessName(rs.getString("process_name"));
				sf.setHost(rs.getString("host"));
				sf.setPictureName(rs.getString("picture_name"));
				sf.setBhri(rs.getInt("bhri"));
				sf.setCreateDate(rs.getTimestamp("create_date"));
				sf.setDescription(rs.getString("remark"));
				sf.setType(rs.getInt("state"));
				sf.setFlag(rs.getInt("flag"));
                sf.setCompanyId(rs.getInt("company_id"));
                sf.setAttribute(rs.getInt("attribute"));
				list.add(sf);
			}
		} catch (SQLException e) {
			log.error("分页查询所有软件" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public String selectClassifyName(String ClassifyName) {
		log.info("查看某软件分类下是否有子分类");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String flag = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select classify_name from tb_classify where classify_name = '"
					+ ClassifyName + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				flag = rs.getString(1);
			}
		} catch (SQLException e) {
			log.error("查看某软件分类下是否有子分类：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return flag;
	}
}