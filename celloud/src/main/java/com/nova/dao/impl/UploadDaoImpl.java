package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nova.dao.IUploadDao;
import com.nova.utils.ConnectManager;

public class UploadDaoImpl implements IUploadDao {
	@Override
	public void addInfo(String filename, Integer chunk, Integer chunks,
			String md5) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into tb_upload (filename,schunk,schunks,md5,create_date) values (?,?,?,?,now())";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, filename);
			ps.setInt(2, chunk);
			ps.setInt(3, chunks);
			ps.setString(4, md5);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}

	@Override
	public Integer getInfo(String filename) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select max(schunk) as schunk from tb_upload where filename=?";
		int result = 0;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, filename);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("schunk");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public void deleteInfo(String filename) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_upload where filename=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, filename);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
	}
}