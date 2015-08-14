package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

import com.celloud.dao.SoftwareDao;
import com.celloud.sdo.Software;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.PropertiesUtil;

public class SoftwareDaoImpl implements SoftwareDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);
	private Connection conn = ConnectManager.getConnection();
	private QueryRunner qr = new QueryRunner();
	private String noUserid = PropertiesUtil.noUserid;
	@Override
	public Object getBigUserAPPNum(Integer companyId) {
		log.info("获取大客户的APP总量");
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(software_id) num from tb_software where company_id=? and off_line=0";
		try {
			map = qr.query(conn, sql, new MapHandler(), companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map.get("num");
	}

	@Override
	public List<Software> getAppListByBigUser(Integer companyId) {
		log.info("获取大客户的APP列表");
		List<Software> list = new ArrayList<Software>();
		String sql = "select s.software_id softwareId,s.software_name softwareName,s.create_date createDate,s.bhri,s.`type`,s.data_num dataNum,s.description,(select count(r.report_id) from tb_report r where r.flag=0 and r.software_id=s.software_id and r.user_id not in ("
				+ noUserid
				+ ")) runNum,(select GROUP_CONCAT(df.format_desc) from tb_data_format df,tb_software_format_relat sf where df.format_id=sf.format_id and sf.software_id=s.software_id group by sf.software_id) dataType from tb_software s where s.off_line=0 and s.company_id=?";
		try {
			ResultSetHandler<List<Software>> rsh = new BeanListHandler<Software>(
					Software.class);
			list = qr.query(conn, sql, rsh, companyId);
		} catch (SQLException e) {
			log.error("获取大客户的APP列表失败:" + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Software getAppById(Integer appId) {
		log.info("根据软件id获取软件信息");
		Software soft = null;
		String sql = "select s.software_id softwareId,s.software_name softwareName,s.create_date createDate,s.bhri,s.`type`,s.data_num dataNum,s.description,s.picture_name pictureName,s.intro,(select count(r.report_id) from tb_report r where r.flag=0 and r.software_id=s.software_id and r.user_id not in ("
				+ noUserid
				+ ")) runNum,(select GROUP_CONCAT(df.format_desc) from tb_data_format df,tb_software_format_relat sf where df.format_id=sf.format_id and sf.software_id=s.software_id group by sf.software_id) dataType from tb_software s where s.software_id=?";
		try {
			ResultSetHandler<Software> rsh = new BeanHandler<Software>(
					Software.class);
			soft = qr.query(conn, sql, rsh, appId);
		} catch (SQLException e) {
			log.error("根据软件id获取软件信息:" + e);
			e.printStackTrace();
		}
		return soft;
	}

}
