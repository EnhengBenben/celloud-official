package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.IAppDao;
import com.nova.sdo.App;
import com.nova.utils.ConnectManager;

public class AppDaoImpl extends BaseDao implements IAppDao {
	Logger log = Logger.getLogger(AppDaoImpl.class);
	@Override
	public List<App> getAll(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		App app = null;
		List<App> list = new ArrayList<App>();
		String sql = "select a.* from tb_software a,tb_user_software b where a.software_id=b.software_id and b.user_id = ? order by desk_no";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				app = new App();
				app.setAppId(rs.getString("software_id"));
				app.setAppName(rs.getString("software_name"));
				app.setAppImg(rs.getString("picture_name"));
				app.setIframeSrc(rs.getString("host"));
				app.setFlag(rs.getString("flag"));
				app.setState(rs.getString("type"));
				list.add(app);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public List<App> getAll(int userId, int deskNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		App app = null;
		List<App> list = new ArrayList<App>();
		String sql = "select a.* from tb_software a,tb_user_software b where a.software_id=b.software_id and b.user_id = ? and b.desk_no = ?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, deskNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				app = new App();
				app.setAppId(rs.getString("software_id"));
				app.setAppName(rs.getString("software_name"));
				app.setAppImg(rs.getString("picture_name"));
				app.setIframeSrc(rs.getString("host"));
				app.setFlag(rs.getString("flag"));
				app.setState(rs.getString("type"));
				list.add(app);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public boolean updateDesk(int userId, int deskNo, int oldDeskNo, int appId) {
		log.info("用户"+super.userName+"将App"+appId+"从桌面"+oldDeskNo+"移动到桌面"+deskNo);
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = false;
		try {
			conn = ConnectManager.getConnection();
			String sql = "update tb_user_software set desk_no=?,update_date=now() where user_id=? and desk_no=? and software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deskNo);
			ps.setInt(2, userId);
			ps.setInt(3, oldDeskNo);
			ps.setInt(4, appId);
			int i = ps.executeUpdate();
			if (i != 1) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (SQLException e) {
			log.info("用户"+super.userName+"将App"+appId+"从桌面"+oldDeskNo+"移动到桌面"+deskNo+"失败");
			e.printStackTrace();
			return false;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public boolean deleteAppFromDesk(int userId, int deskNo, int appId) {
		log.info("用户"+super.userName+"从桌面"+deskNo+"删除App"+appId);
		Connection conn = null;
		PreparedStatement ps = null;
		boolean flag = false;
		try {
			conn = ConnectManager.getConnection();
			String sql = "delete from tb_user_software where desk_no=? and user_id=? and software_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deskNo);
			ps.setInt(2, userId);
			ps.setInt(3, appId);
			int i = ps.executeUpdate();
			if (i != 1) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (SQLException e) {
			log.info("用户"+super.userName+"从桌面"+deskNo+"删除App"+appId+"失败");
			e.printStackTrace();
			return false;
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}
}
