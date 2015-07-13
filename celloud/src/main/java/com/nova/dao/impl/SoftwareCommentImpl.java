package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.constants.CommentState;
import com.nova.dao.ISoftwareCommentDao;
import com.nova.pager.Page;
import com.nova.sdo.SoftwareComment;
import com.nova.utils.ConnectManager;

public class SoftwareCommentImpl implements ISoftwareCommentDao {
	Logger log = Logger.getLogger(SoftwareCommentImpl.class);

	@Override
	public List<SoftwareComment> getCommentList(int softwareId, Page page) {
		log.info("获取软件" + softwareId + "评论列表");
		List<SoftwareComment> commentList = new ArrayList<SoftwareComment>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SoftwareComment comment = null;
		if (null == page) {
			page = new Page();
			page.setCurrentPage(1);
		}
		page.setPageSize(10);
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		String sql = "select c.*,u.username comment_user_name from tb_user u,tb_software_comment c where c.comment_user_id=u.user_id and c.software_id=? and c.comment_type=? order by c.comment_date desc limit "
				+ start + "," + page.getPageSize();
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			ps.setInt(2, CommentState.COMMENT);
			rs = ps.executeQuery();
			while (rs.next()) {
				comment = new SoftwareComment();
				comment.setId(rs.getInt("id"));
				comment.setCommentUserId(rs.getInt("comment_user_id"));
				comment.setCommentUserName(rs.getString("comment_user_name"));
				comment.setSoftwareId(softwareId);
				comment.setReplyUserId(rs.getInt("reply_user_id"));
				comment.setCommentType(rs.getInt("comment_type"));
				comment.setComment(rs.getString("comment"));
				comment.setCommentDate(rs.getTimestamp("comment_date"));
				commentList.add(comment);
			}
		} catch (SQLException e) {
			log.info("获取软件" + softwareId + "评论列表失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return commentList;
	}

	@Override
	public List<SoftwareComment> getCommentList(int softwareId) {
		log.info("获取软件" + softwareId + "评论列表");
		List<SoftwareComment> commentList = new ArrayList<SoftwareComment>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SoftwareComment comment = null;
		String sql = "select c.*,u.username comment_user_name from tb_user u,tb_software_comment c where c.comment_user_id=u.user_id and c.software_id=? and c.comment_type=? order by c.comment_date desc";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			ps.setInt(2, CommentState.COMMENT);
			rs = ps.executeQuery();
			while (rs.next()) {
				comment = new SoftwareComment();
				comment.setId(rs.getInt("id"));
				comment.setCommentUserId(rs.getInt("comment_user_id"));
				comment.setCommentUserName(rs.getString("comment_user_name"));
				comment.setSoftwareId(softwareId);
				comment.setReplyUserId(rs.getInt("reply_user_id"));
				comment.setCommentType(rs.getInt("comment_type"));
				comment.setComment(rs.getString("comment"));
				comment.setCommentDate(rs.getTimestamp("comment_date"));
				comment.setReplyId(rs.getInt("reply_id"));
				commentList.add(comment);
			}
		} catch (SQLException e) {
			log.info("获取软件" + softwareId + "评论列表失败！" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return commentList;
	}

	@Override
	public int addComment(SoftwareComment comment) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 1;
		int commentType = comment.getCommentType();
		String sql = null;
		if (commentType == CommentState.COMMENT) {// 发表评论
			sql = "insert into tb_software_comment(comment_user_id,software_id,comment_date,comment_type,comment) values(?,?,now(),?,?)";
		} else if (commentType == CommentState.REPLY) {// 回复评论
			sql = "insert into tb_software_comment(comment_user_id,software_id,comment_date,comment_type,comment,reply_user_id,reply_id) values(?,?,now(),?,?,?,?)";
		}
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, comment.getCommentUserId());
			ps.setInt(2, comment.getSoftwareId());
			ps.setInt(3, comment.getCommentType());
			ps.setString(4, comment.getComment());
			if (comment.getCommentType() == CommentState.REPLY) {
				ps.setInt(5, comment.getReplyUserId());
				ps.setInt(6, comment.getReplyId());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public int delComment(int commentId, int delType) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 1;
		// 默认删除评论
		String sql = "delete from tb_software_comment where id=? or reply_id=?";
		// 删除回复
		if (delType == 1) {
			sql = "delete from tb_software_comment where id=?";
		}
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, commentId);
			if (delType == 0) {
				ps.setInt(2, commentId);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public SoftwareComment getCommentById(int commentId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SoftwareComment comment = null;
		String sql = "select * from tb_software_comment where id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, commentId);
			rs = ps.executeQuery();
			if (rs.next()) {
				comment = new SoftwareComment();
				comment.setId(rs.getInt("id"));
				comment.setCommentUserId(rs.getInt("comment_user_id"));
				comment.setSoftwareId(rs.getInt("software_id"));
				comment.setReplyUserId(rs.getInt("reply_user_id"));
				comment.setCommentType(rs.getInt("comment_type"));
				comment.setComment(rs.getString("comment"));
				comment.setCommentDate(rs.getTimestamp("comment_date"));
				comment.setReplyId(rs.getInt("reply_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return comment;
	}

	@Override
	public List<SoftwareComment> getReplyList(int commentId) {
		log.info("获取评论" + commentId + "回复列表");
		List<SoftwareComment> commentList = new ArrayList<SoftwareComment>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SoftwareComment comment = null;
		String sql = "select c.*,u.username reply_user_name from tb_user u,tb_software_comment c where c.reply_user_id=u.user_id and c.reply_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, commentId);
			rs = ps.executeQuery();
			while (rs.next()) {
				comment = new SoftwareComment();
				comment.setId(rs.getInt("id"));
				comment.setCommentUserId(rs.getInt("comment_user_id"));
				comment.setSoftwareId(rs.getInt("software_id"));
				comment.setReplyUserId(rs.getInt("reply_user_id"));
				comment.setReplyUserName(rs.getString("reply_user_name"));
				comment.setCommentType(rs.getInt("comment_type"));
				comment.setComment(rs.getString("comment"));
				comment.setCommentDate(rs.getTimestamp("comment_date"));
				commentList.add(comment);
			}
		} catch (SQLException e) {
			log.info("获取评论" + commentId + "回复列表失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return commentList;
	}
}
