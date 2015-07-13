package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.IClientDao;
import com.nova.sdo.Client;
import com.nova.utils.ConnectManager;

public class ClientDaoImpl implements IClientDao {
	Logger log = Logger.getLogger(NoticeDaoImpl.class);

	@Override
	public int add(Client client) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 0;
		try {
			conn = ConnectManager.getConnection();
			String sql = "insert into tb_client (version,name,create_date) values(?,?,now())";
			ps = conn.prepareStatement(sql);
			ps.setString(1, client.getVersion());
			ps.setString(2, client.getName());
			flag = ps.executeUpdate();
			log.error("新增客户端成功");
		} catch (SQLException e) {
			log.error("新增客户端失败:" + e);
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	@Override
	public Client getLast() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Client client = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_client order by id desc limit 1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getString("client"));
				client.setVersion(rs.getString("version"));
				client.setCreateDate(rs.getDate("create_date"));
			}
			log.info("查询最新的客户端成功");
		} catch (SQLException e) {
			log.error("查询最新的客户端失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return client;
	}

	@Override
	public List<Client> getAll() {
		log.info("查询所有客户端版本");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Client> list = new ArrayList<Client>();
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_client";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getString("name"));
				client.setVersion(rs.getString("version"));
				client.setCreateDate(rs.getDate("create_date"));
				list.add(client);
			}
		} catch (SQLException e) {
			log.error("查询所有客户端版本失败：" + e);
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return list;
	}
}