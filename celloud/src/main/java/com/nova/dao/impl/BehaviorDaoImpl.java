package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nova.dao.IBehaviorDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Behavior;
import com.nova.utils.ConnectManager;

public class BehaviorDaoImpl extends BaseDao implements IBehaviorDao {
	Logger log = Logger.getLogger(BehaviorDaoImpl.class);
	
	@Override
	public int logLoginInfo(Behavior behavior) {
		Connection conn = null;
		PreparedStatement ps = null;
		int flag = 1;
		try {
			conn = ConnectManager.getConnection();
			String sql = "insert into tb_log(log_date,browser,os,ip,address,user_name) values(now(),?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, behavior.getBrowser());
			ps.setString(2, behavior.getOs());
			ps.setString(3, behavior.getIp());
			ps.setString(4, behavior.getAddress());
			ps.setString(5, behavior.getUserName());
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
	public PageList<Behavior> getUserLogInfo(Page page) {
		if (null == page) {// 若page为null,则从第1页开始
			page = new Page(10, 1);
		}
		PageList<Behavior> dataPageList = new PageList<Behavior>();
		StringBuffer queryBuff = new StringBuffer();
		StringBuffer countBuff = new StringBuffer();
		queryBuff.append("select * from tb_log where user_name=? order by log_date desc");
		int start = (page.getCurrentPage() - 1) * page.getPageSize();
		queryBuff.append(" limit " + start + "," + page.getPageSize());
		countBuff.append("select count(*) log_count from tb_log where user_name=?");
		Connection conn = null;
		PreparedStatement qps = null;
		PreparedStatement cps = null;
		ResultSet qrs = null;
		ResultSet crs = null;
		Behavior behavior = null;
		List<Behavior> datas = new ArrayList<Behavior>();
		try {
			conn = ConnectManager.getConnection();
			qps = conn.prepareStatement(queryBuff.toString());
			qps.setString(1, super.userName);
			qrs = qps.executeQuery();
			cps = conn.prepareStatement(countBuff.toString());
			cps.setString(1, super.userName);
			crs = cps.executeQuery();
			while (qrs.next()) {
				behavior = new Behavior();
				behavior.setLogDate(qrs.getTimestamp("log_date"));
				behavior.setIp(qrs.getString("ip"));
				behavior.setAddress(qrs.getString("address"));
				behavior.setBrowser(qrs.getString("browser"));
				behavior.setOs(qrs.getString("os"));
				behavior.setUserName(qrs.getString("user_name"));
				datas.add(behavior);
			}
			if (crs.next()) {
				page.make(crs.getInt("log_count"));
			} else {
				page.make(0);
			}
		} catch (SQLException e) {
			log.error("用户"+super.userName+"访问自己的登录列表失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, qps, qrs);
			ConnectManager.free(conn, cps, crs);
		}
		dataPageList.setDatas(datas);
		dataPageList.setPage(page);
		return dataPageList;
	}

	@Override
	public List<Behavior> getBehaviorList(String date) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Behavior> list = new ArrayList<Behavior>();
		try {
			conn = ConnectManager.getConnection();
			String sql = null;
			if (date == null || "".equals(date) || "null".equals(date)) {
				sql = "select count(*) as num,left(log_date,13) as date from tb_log where user_name not in ('lin','xiawt','lqx','zl','demo','administrator','test') group by date";
			} else {
				sql = "select count(*) as num,left(log_date,13) as date from tb_log where user_name not in ('lin','xiawt','lqx','zl','demo','administrator','test') and left(log_date,10)='"
						+ date + "' group by date;";
			}
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Behavior behavior = null;
			while (rs.next()) {
				behavior = new Behavior();
				behavior.setUserName(rs.getString("num"));
				behavior.setAddress(rs.getString("date"));
				list.add(behavior);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return list;
	}
}
