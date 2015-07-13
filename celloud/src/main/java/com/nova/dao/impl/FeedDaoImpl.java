package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nova.dao.IFeedBackDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.FeedBack;
import com.nova.utils.ConnectManager;

public class FeedDaoImpl implements IFeedBackDao {

	@Override
	public PageList<FeedBack> selectAllFeedBack(Page page) {
		if (null == page) {// 若page为null,则从第1页开始
			page = new Page(5, 1);
		}
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		PageList<FeedBack> dataPageList = new PageList<FeedBack>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FeedBack> fList = new ArrayList<FeedBack>();
		FeedBack fb = null;
		String qsql = "select * from tb_feedback order by create_date desc limit "
				+ start + "," + page.getPageSize();
		String cSql = "select count(*) fcount from tb_feedback";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(qsql);
			rs = ps.executeQuery();
			while (rs.next()) {
				fb = new FeedBack();
				fb.setId(rs.getInt("id"));
				fb.setUserName(rs.getString("user_name"));
				fb.setEmail(rs.getString("email"));
				fb.setTitle(rs.getString("title"));
				fb.setContent(rs.getString("content"));
				fb.setCreateDate(rs.getTimestamp("create_date"));
				fb.setSolve(rs.getInt("solve"));
				fList.add(fb);
			}
			ps = conn.prepareStatement(cSql);
			rs = ps.executeQuery();
			if (rs.next()) {
				page.make(rs.getInt("fcount"));
			} else {
				page.make(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		dataPageList.setDatas(fList);
		dataPageList.setPage(page);
		return dataPageList;
	}

	@Override
	public boolean saveFeedBack(FeedBack fb) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = true;
		String sql = "insert into tb_feedback(user_name,email,title,content,create_date) values(?,?,?,?,now())";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, fb.getUserName());
			ps.setString(2, fb.getEmail());
			ps.setString(3, fb.getTitle());
			ps.setString(4, fb.getContent());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
}
