/**    
 * @Title: NoticeDaoImpl.java  
 * @Package com.nova.dao.impl   
 * @author summer    
 * @date 2012-6-28 上午10:34:03  
 * @version V1.0    
 */
package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.INoticeDao;
import com.nova.sdo.Notice;
import com.nova.utils.ConnectManager;

/**
 * @ClassName: NoticeDaoImpl
 * @Description: (公告实现类)
 * @author summer
 * @date 2012-6-28 上午10:34:03
 * 
 */
public class NoticeDaoImpl implements INoticeDao {
	Logger log = Logger.getLogger(NoticeDaoImpl.class);

	/**
	 * @Methods: addNotice
	 * @Description: 新增公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public boolean addNotice(Notice notice) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		int j = 0;
		String sql = "insert into tb_notice (notice_title,notice_context,create_date,stats) values(?,?,now(),?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, notice.getNoticeTitle());
			ps.setString(2, notice.getNoticeContext());
			ps.setInt(3, notice.getStats());
			j = ps.executeUpdate();
			if (j < 1) {
				flag = false;
				log.info("新增失败" + notice.getNoticeTitle());
			}
		} catch (SQLException e) {
			log.error("新增公告失败" + notice.getNoticeTitle() + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	/**
	 * @Methods: deleteNotice
	 * @Description: 公告失效
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public void deleteNotice(int noticeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_notice set stats=1 where notice_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, noticeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	/**
	 * @Methods: editNotice
	 * @Description: 修改公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public void editNotice(String noticeTitle, String noticeContext,
			int noticeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_notice set notice_title=?,notice_context=? where notice_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, noticeTitle);
			ps.setString(2, noticeContext);
			ps.setInt(3, noticeId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("修改公告失败" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	/**
	 * @Methods: getNoticeById
	 * @Description: 根据notice_id查询公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public Notice getNoticeById(int noticeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Notice notice = null;
		String sql = "select * from tb_notice where notice_id = ?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, noticeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("notice_id"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeContext(rs.getString("notice_context"));
				notice.setCreateDate(rs.getDate("create_date"));
				notice.setExpireDate(rs.getDate("expire_date"));
				notice.setStats(rs.getInt("stats"));
			}
		} catch (SQLException e) {
			log.error("查询NoticeId=" + noticeId + "失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return notice;
	}

	/**
	 * @Methods: getAllNotice
	 * @Description: 查询所有公告，按从新到老的顺序
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public List<Notice> getAllNotice() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Notice notice = null;
		List<Notice> list = new ArrayList<Notice>();
		String sql = "select * from tb_notice order by notice_id desc";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("notice_id"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeContext(rs.getString("notice_context"));
				notice.setCreateDate(rs.getDate("create_date"));
				notice.setExpireDate(rs.getDate("expire_date"));
				notice.setStats(rs.getInt("stats"));
				list.add(notice);
			}
			log.debug("查询所有信息成功");
		} catch (SQLException e) {
			log.error("查询所有信息成功失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	/**
	 * @Methods: getNewsNotice
	 * @Description: 查询最新公告
	 * @Author: ASUS
	 * @Date: 2012-6-28
	 */
	@Override
	public Notice getNewsNotice() {
		log.info("获取最新的公告");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Notice notice = null;
		String sql = "SELECT * from tb_notice where notice_id = (SELECT MAX(notice_id) from tb_notice)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("notice_id"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeContext(rs.getString("notice_context"));
				notice.setCreateDate(rs.getDate("create_date"));
				notice.setExpireDate(rs.getDate("expire_date"));
				notice.setStats(rs.getInt("stats"));
			}
		} catch (SQLException e) {
			log.error("获取最新公告失败" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return notice;
	}
}
