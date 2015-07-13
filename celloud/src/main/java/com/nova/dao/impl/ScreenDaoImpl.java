package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.IScreenDao;
import com.nova.sdo.Screen;
import com.nova.utils.ConnectManager;

public class ScreenDaoImpl implements IScreenDao {
	Logger log = Logger.getLogger(ScreenDaoImpl.class);

	@Override
	public int createScreen(String screenName, int softwareId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int j = 0;
		String sql = "insert into tb_screen (screen_name,software_id) values(?,?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, screenName);
			ps.setInt(2, softwareId);
			j = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("新增软件截图失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return j;
	}

	@Override
	public int deleteScreen(int softwareId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int j = 0;
		String sql = "delete from tb_screen where software_id = ?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, softwareId);
			j = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("删除软件截图失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return j;
	}

	@Override
	public List<Screen> getAllScreen(int softId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Screen> list = new ArrayList<Screen>();
		String sql = "select * from tb_screen where software_id=" + softId;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Screen screen = new Screen();
				screen.setScreenId(rs.getInt("screen_id"));
				screen.setScreenName(rs.getString("screen_name"));
				screen.setSoftwareId(rs.getInt("software_id"));
				list.add(screen);
			}
			log.debug("查询所有软件截图信息成功");
		} catch (SQLException e) {
			log.error("查询所有软件截图信息失败：id=" + softId + "," + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}
}