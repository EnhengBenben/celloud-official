package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nova.dao.IDataFormatDao;
import com.nova.sdo.DataFormat;
import com.nova.utils.ConnectManager;

public class DataFormatDaoImpl implements IDataFormatDao {
	@Override
	public List<DataFormat> getDataFormatList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DataFormat dataType = null;
		List<DataFormat> list = new ArrayList<DataFormat>();
		String sql = "select * from tb_data_format";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataType = new DataFormat();
				dataType.setFormatId(rs.getInt("format_id"));
				dataType.setFormatDesc(rs.getString("format_desc"));
				list.add(dataType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

	@Override
	public int getDataFormatIdByName(String formatName) {
		int dataType = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select format_id from tb_data_format where format_desc=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, formatName);
			rs = ps.executeQuery();
			if (rs.next()) {
				dataType = rs.getInt("format_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataType;
	}

	@Override
	public String getDataFormatNameById(int dataFormatId) {
		String typeName = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select format_desc from tb_data_format where format_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataFormatId);
			rs = ps.executeQuery();
			if (rs.next()) {
				typeName = rs.getString("format_desc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return typeName;
	}
}
