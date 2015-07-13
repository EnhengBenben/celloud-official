package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nova.dao.IDataTypeDao;
import com.nova.sdo.DataType;
import com.nova.utils.ConnectManager;

public class DataTypeDaoImpl implements IDataTypeDao {

	@Override
	public List<DataType> getDataTypeList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DataType dataType = null;
		List<DataType> list = new ArrayList<DataType>();
		String sql = "select * from tb_data_type";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataType = new DataType();
				dataType.setTypeId(rs.getInt("type_id"));
				dataType.setTypeDesc(rs.getString("type_desc"));
				list.add(dataType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}

}
