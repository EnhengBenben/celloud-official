package com.celloud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.celloud.dao.ReportDao;
import com.celloud.sdo.Report;
import com.mysql.jdbc.Statement;
import com.nova.constants.ReportState;
import com.nova.constants.ReportType;
import com.nova.utils.ConnectManager;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-24下午2:53:19
 * @version Revision: 1.0
 */
public class ReportDaoImpl extends BaseDao implements ReportDao {
    Logger log = Logger.getLogger(ReportDaoImpl.class);
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Long insertProReport(Report report) {
	String sql = "insert into tb_report(user_id,software_id,project_id,state,flag,create_date) values(?,?,?,?,?,now())";
	Long id = null;
	try {
	    conn = ConnectManager.getConnection();
	    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setLong(1, report.getUserId());
	    ps.setLong(2, report.getSoftwareId());
	    ps.setLong(3, report.getProjectId());
	    ps.setInt(4, ReportState.NOREPORT);
	    ps.setInt(5, ReportType.PROJECT);
	    ps.executeUpdate();
	    rs = ps.getGeneratedKeys();
	    if (rs.next()) {
		id = rs.getLong(1);
	    }
	} catch (SQLException e) {
	    log.error("用户" + super.userName + "创建项目报告失败");
	    e.printStackTrace();
	} finally {
	    ConnectManager.free(conn, ps, rs);
	}
	return id;
    }

}
