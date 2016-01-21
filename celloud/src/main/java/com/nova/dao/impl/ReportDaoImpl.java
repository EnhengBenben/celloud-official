package com.nova.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.nova.constants.DataState;
import com.nova.constants.ReportState;
import com.nova.constants.ShareState;
import com.nova.dao.IReportDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.CmpDrug;
import com.nova.sdo.Data;
import com.nova.sdo.Report;
import com.nova.sdo.SoftCMPGene;
import com.nova.sdo.Software;
import com.nova.sdo.UserReport;
import com.nova.utils.ConnectManager;

public class ReportDaoImpl extends BaseDao implements IReportDao {
	Logger log = Logger.getLogger(ReportDaoImpl.class);

	@Override
	public int addReportInfo(Report report) {
		int fileId = report.getFileId();
		boolean fileReport = fileId != 0;
		if (fileReport) {
			log.info("用户" + super.userName + "为数据" + fileId + "添加App："
					+ report.getSoftwareId());
		} else {
			log.info("用户" + super.userName + "为项目" + report.getProjectId()
					+ "添加App：" + report.getSoftwareId());
		}
		int addReportNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into tb_report(user_id,software_id,file_id,project_id,state,flag,create_date) values(?,?,?,?,?,?,now())";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, report.getUserId());
			ps.setInt(2, report.getSoftwareId());
			ps.setInt(3, report.getFileId());
			ps.setInt(4, report.getProjectId());
			ps.setInt(5, report.getState());
			ps.setInt(6, report.getFlag());
			addReportNum = ps.executeUpdate();
		} catch (SQLException e) {
			if (fileReport) {
				log.info("用户" + super.userName + "为数据" + fileId + "添加App："
						+ report.getSoftwareId() + "失败");
			} else {
				log.info("用户" + super.userName + "为项目" + report.getProjectId()
						+ "添加App：" + report.getSoftwareId() + "失败");
			}
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addReportNum;
	}

	@Override
	public int getTotalReportNum(int userId) {
		int totalReportNum = 0;
		String sql = "select * from tb_file_share where user_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int fileId = rs.getInt("file_id");
				if (fileId != 0) {// 数据共享
					String drSql = "select * from tb_report where file_id=? and state=?";
					ps = conn.prepareStatement(drSql);
					ps.setInt(1, fileId);
					ps.setInt(2, ReportState.COMPLETE);
					rs = ps.executeQuery();
					while (rs.next()) {
						totalReportNum++;
					}
				} else {// 项目共享
					int proId = rs.getInt("project_id");
					String prSql = "select * from tb_report where project_id=? and state=?";
					ps = conn.prepareStatement(prSql);
					ps.setInt(1, proId);
					ps.setInt(2, ReportState.COMPLETE);
					rs = ps.executeQuery();
					while (rs.next()) {
						totalReportNum++;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return totalReportNum;
	}

	@Override
	public int getPrivateReportNum(int userId) {
		int privateReportNum = 0;
		String sql = "select count(*) count from(select * from tb_report where user_id=? and state=?) t";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, ReportState.COMPLETE);
			rs = ps.executeQuery();
			while (rs.next()) {
				privateReportNum = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return privateReportNum;
	}

	@Override
	public int addProReportInfo(Report report) {
		log.info("添加项目报告信息");
		int addReportNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into tb_report(user_id,software_id,file_id,project_id,flag,create_date) values(?,?,?,?,1,now())";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, report.getUserId());
			ps.setInt(2, report.getSoftwareId());
			ps.setInt(3, report.getFileId());
			ps.setInt(4, report.getProjectId());
			addReportNum = ps.executeUpdate();
			log.info("添加项目报告信息成功");
		} catch (SQLException e) {
			log.error("添加项目报告信息失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addReportNum;
	}

	@Override
	public List<Report> getReportListByProId(int projectId) {
		List<Report> reportList = new ArrayList<Report>();
		StringBuffer qBuff = new StringBuffer(
				"select distinct * from(select r.report_id,r.user_id,r.software_id,r.state,s.picture_name,s.software_name,p.project_name,p.project_id from tb_report r,tb_software s,tb_project p where r.software_id=s.software_id and r.flag=1 and r.project_id="
						+ projectId + " and p.project_id=r.project_id) t");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(qBuff.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Report report = new Report();
				report.setReportId(rs.getInt("report_id"));
				report.setUserId(rs.getInt("user_id"));
				report.setSoftwareId(rs.getInt("software_id"));
				report.setSoftwareName(rs.getString("software_name"));
				report.setPictureName(rs.getString("picture_name"));
				report.setState(rs.getInt("state"));
				report.setProjectName(rs.getString("project_name"));
				report.setProjectId(rs.getInt("project_id"));
				reportList.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return reportList;
	}

	@Override
	public int updateReportState(int reportId, int state) {
		Report report = getReportById(reportId);
		int fileId = report.getFileId();
		int primaryState = report.getState();
		boolean fileReport = (fileId != 0);
		if (state == ReportState.NOREPORT) {// 修改为正在运行状态
			if (fileReport) {
				log.info("用户" + super.userName + "运行数据" + fileId + "中App："
						+ report.getSoftwareId());
			} else {
				log.info("用户" + super.userName + "运行项目" + report.getProjectId()
						+ "中App：" + report.getSoftwareId());
			}
		}
		String sql = "update tb_report set state=" + state
				+ " where report_id=" + reportId;
		// 点击开始运行时，修改创建时间
		if (primaryState == ReportState.UNRUN && state > ReportState.UNRUN) {
			sql = "update tb_report set create_date=now(),state=" + state
					+ " where report_id=" + reportId;
		}
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			if (state == ReportState.NOREPORT) {// 修改为正在运行状态
				if (fileReport) {
					log.info("用户" + super.userName + "运行数据" + fileId + "中App："
							+ report.getSoftwareId() + "失败");
				} else {
					log.info("用户" + super.userName + "运行项目"
							+ report.getProjectId() + "中App："
							+ report.getSoftwareId() + "失败");
				}
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public String getReportIdByUserFileId(String userId, String fileId) {
		log.info("获取报告编号");
		String reportId = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select report_id from tb_report where user_id=? and file_id=? and flag=0";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, fileId);
			rs = ps.executeQuery();
			if (rs.next()) {
				reportId = rs.getInt("report_id") + "";
			}
		} catch (SQLException e) {
			log.error("获取报告编号失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return reportId;
	}

	@Override
	public int saveTreeProject(Integer userId, String dataIds, int softwareId) {
		log.info("添加数据报告信息");
		String[] dataIdArr = dataIds.split(",");
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (String dataId : dataIdArr) {
				// 先删除之前的报告记录
				String delSql = "delete from tb_report where user_id=? and software_id=? and file_id=? and flag=0";
				ps = conn.prepareStatement(delSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, Integer.parseInt(dataId));
				ps.executeUpdate();

				String insertSql = "insert into tb_report(user_id,software_id,file_id,state,flag,create_date) values(?,?,?,?,?,now())";
				ps = conn.prepareStatement(insertSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, Integer.parseInt(dataId));
				ps.setInt(4, 2);
				ps.setInt(5, 0);
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public boolean existsReport(int userId, int softwareId, int dataId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from tb_report where user_id=? and file_id=? and flag=0 and state=2 and software_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, dataId);
			ps.setInt(3, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			log.error("获取报告编号失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return false;
	}

	@Override
	public int saveProDataReport(int userId, List<Data> dataList, int softwareId) {
		log.info("添加项目数据报告信息");
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (Data data : dataList) {
				String insertSql = "insert into tb_report(user_id,software_id,file_id,state,create_date) values(?,?,?,?,now())";
				ps = conn.prepareStatement(insertSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, data.getFileId());
				ps.setInt(4, 2);
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("添加项目数据报告信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int getSoftwareIdByProjectId(int projectId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select software_id from tb_report where project_id=? and flag=1";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("software_id");
			}
		} catch (SQLException e) {
			log.error("获取报告编号失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return 0;
	}

	@Override
	public int updateReportStateByProjectId(int projectId, int state) {
		int result = 1;
		String sql = "update tb_report set state=" + state
				+ " where project_id=" + projectId + " and flag=1";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int delReport(int userId, List<Data> dataList, int softwareId) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (Data data : dataList) {
				String insertSql = "delete from tb_report where user_id=? and software_id=? and file_id=?";
				ps = conn.prepareStatement(insertSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, data.getFileId());
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("添加项目数据报告信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public boolean proExistsReport(int userId, int projectId) {
		boolean hasProReport = false;
		String sql = "select * from tb_report where flag=1 and project_id=? and user_id=? and state>?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, userId);
			ps.setInt(3, ReportState.UNRUN);
			rs = ps.executeQuery();
			while (rs.next()) {
				hasProReport = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return hasProReport;
	}

	@Override
	public List<String> checkProjectHasReport(int userId, int projectId) {
		List<String> softList = new ArrayList<String>();
		String sql = "select r.software_id,software_name from tb_report r,tb_software s where r.software_id=s.software_id and r.flag=1 and r.project_id=? and r.user_id=? and r.state>?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, userId);
			ps.setInt(3, ReportState.UNRUN);
			rs = ps.executeQuery();
			while (rs.next()) {
				softList.add(rs.getString("software_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return softList;
	}

	@Override
	public boolean deleteProReport(int userId, int projectId) {
		boolean hasDelete = false;
		String sql = "delete from tb_report where project_id=? and user_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, userId);
			int deleteNum = ps.executeUpdate();
			if (deleteNum > 0) {
				hasDelete = true;
				log.info("删除项目报告成功");
			}
		} catch (SQLException e) {
			log.error("删除项目报告失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return hasDelete;
	}

	@Override
	public int saveSnpReport(int userId, int projectId, String dataIds,
			int softId) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			// 新建项目报告
			String proSql = "insert into tb_report(user_id,software_id,project_id,state,flag,create_date) values(?,?,?,?,1,now())";
			ps = conn.prepareStatement(proSql);
			ps.setInt(1, userId);
			ps.setInt(2, softId);
			ps.setInt(3, projectId);
			ps.setInt(4, ReportState.COMPLETE);
			ps.executeUpdate();
			// 新建数据报告
			for (String dataId : dataIds.split(",")) {
				String qSql = "select state from tb_report where user_id=? and software_id=? and file_id=? and flag=0";
				ps = conn.prepareStatement(qSql);
				ps.setInt(1, userId);
				ps.setInt(2, softId);
				ps.setInt(3, Integer.parseInt(dataId));
				rs = ps.executeQuery();
				if (rs.next()) {
					int state = rs.getInt("state");
					if (state == ReportState.COMPLETE) {
						continue;
					} else {
						String updateSql = "update tb_report set state=? where user_id=? and software_id=? and file_id=? and flag=0";
						ps = conn.prepareStatement(updateSql);
						ps.setInt(1, ReportState.COMPLETE);
						ps.setInt(2, userId);
						ps.setInt(3, softId);
						ps.setInt(4, Integer.parseInt(dataId));
						ps.executeUpdate();
					}
				} else {
					String dataSql = "insert into tb_report(user_id,software_id,file_id,state,flag,create_date) values(?,?,?,?,0,now())";
					ps = conn.prepareStatement(dataSql);
					ps.setInt(1, userId);
					ps.setInt(2, softId);
					ps.setInt(3, Integer.parseInt(dataId));
					ps.setInt(4, ReportState.COMPLETE);
					ps.executeUpdate();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("添加项目数据报告信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public boolean hasDataReport(int dataId, int softwareId) {
		boolean hasReport = false;
		int reportNum = 0;
		String sql = "select report_id from tb_report where file_id=? and software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dataId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			while (rs.next()) {
				reportNum++;
			}
			if (reportNum > 0) {
				hasReport = true;
				log.info("查询数据" + dataId + "是否关联" + softwareId + "成功");
			}
		} catch (SQLException e) {
			log.error("查询数据" + dataId + "是否关联" + softwareId + "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return hasReport;
	}

	@Override
	public boolean hasProReport(int projectId, int softwareId) {
		boolean hasReport = false;
		int reportNum = 0;
		String sql = "select report_id from tb_report where project_id=? and software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			while (rs.next()) {
				reportNum++;
			}
			if (reportNum > 0) {
				hasReport = true;
				log.info("查询项目" + projectId + "是否关联" + softwareId + "成功");
			}
		} catch (SQLException e) {
			log.error("查询数据" + projectId + "是否关联" + softwareId + "失败");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return hasReport;
	}

	@Override
	public boolean deleteReport(int reportId) {
		Report report = getReportById(reportId);
		int fileId = report.getFileId();
		boolean fileR = (fileId != 0);
		if (fileR) {
			log.info("用户" + super.userName + "删除数据" + fileId + "中App:"
					+ report.getSoftwareId());
		} else {
			log.info("用户" + super.userName + "删除项目" + report.getProjectId()
					+ "中App:" + report.getSoftwareId());
		}
		boolean delRep = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_report where report_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reportId);
			int delResult = ps.executeUpdate();
			if (delResult > 0) {
				delRep = true;
			}
		} catch (SQLException e) {
			if (fileR) {
				log.info("用户" + super.userName + "删除数据" + fileId + "中App:"
						+ report.getSoftwareId() + "失败");
			} else {
				log.info("用户" + super.userName + "删除项目" + report.getProjectId()
						+ "中App:" + report.getSoftwareId() + "失败");
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return delRep;
	}

	@Override
	public boolean deleteReportByProSoftId(int reportId, int projectId,
			int softwareId) {
		boolean delRep = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "delete from tb_report where report_id=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reportId);
			int delResult = ps.executeUpdate();
			System.err.println("delResult:" + delResult);
			if (delResult > 0) {
				// 删除数据报告
				String dSql = "select file_id from tb_data_project_relat where project_id=?";
				ps = conn.prepareStatement(dSql);
				ps.setInt(1, projectId);
				rs = ps.executeQuery();
				List<Integer> dataList = new ArrayList<Integer>();
				while (rs.next()) {
					dataList.add(rs.getInt("file_id"));
				}
				System.err.println("dataList.size():" + dataList.size());
				if (dataList.size() > 0) {
					for (Integer dataId : dataList) {
						String delSql = "delete from tb_report where software_id=? and file_id=?";
						ps = conn.prepareStatement(delSql);
						ps.setInt(1, softwareId);
						ps.setInt(2, dataId);
						ps.executeUpdate();
					}
				}
				delRep = true;
			}
		} catch (SQLException e) {
			delRep = false;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return delRep;
	}

	@Override
	public Report getReportById(int resportId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Report report = null;
		try {
			String sql = "select * from tb_report where report_id=?";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, resportId);
			rs = ps.executeQuery();
			if (rs.next()) {
				report = new Report();
				report.setFileId(rs.getInt("file_id"));
				report.setProjectId(rs.getInt("project_id"));
				report.setSoftwareId(rs.getInt("software_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return report;
	}

	@Override
	public int updateReportStateByProSoftId(int userId, int projectId,
			int softwareId, int state, String context) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String qSql = "select * from tb_report where project_id="
					+ projectId + " and software_id=" + softwareId;
			ps = conn.prepareStatement(qSql);
			rs = ps.executeQuery();
			if (!rs.next()) {// 添加记录
				String iSql = "insert into tb_report(project_id,software_id,state,flag,user_id,context,create_date) values(?,?,?,?,?,?,now())";
				ps = conn.prepareStatement(iSql);
				ps.setInt(1, projectId);
				ps.setInt(2, softwareId);
				ps.setInt(3, state);
				ps.setInt(4, 1);
				ps.setInt(5, userId);
				ps.setString(6, context);
				// ps.setDate(7, new java.sql.Date(new Date().getTime()));
				result = ps.executeUpdate();
			} else {// 修改记录
				// String sql = "update tb_report set state=" + state
				// + ",context='" + context + "',create_date='"
				// + new java.sql.Date(new Date().getTime())
				// + "' where project_id="
				// + projectId
				// + " and software_id=" + softwareId;
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append("update tb_report set state=").append(state)
						.append(",context='").append(context).append("'");
				if (state == ReportState.COMPLETE) {
					sqlBuff.append(",end_date=now()");
				}
				sqlBuff.append(" where project_id=").append(projectId)
						.append(" and software_id=").append(softwareId);
				ps = conn.prepareStatement(sqlBuff.toString());
				result = ps.executeUpdate();
			}
			if (result > 0 && state == ReportState.COMPLETE) {// 运行完成如果需要关联数据要添加数据报告
				String reSql = "select run_data from tb_software where software_id=?";
				ps = conn.prepareStatement(reSql);
				ps.setInt(1, softwareId);
				rs = ps.executeQuery();
				int runData = 1;
				if (rs.next()) {
					runData = rs.getInt("run_data");
				}
				if (runData == 0) {// 0代表可以直接运行数据
					List<String> dataArr = new ArrayList<String>();
					String dataIdsSql = "select file_id from tb_data_project_relat where project_id=?";
					ps = conn.prepareStatement(dataIdsSql);
					ps.setInt(1, projectId);
					rs = ps.executeQuery();
					while (rs.next()) {
						dataArr.add(String.valueOf(rs.getInt("file_id")));
					}
					if (dataArr.size() > 0) {
						for (String dataId : dataArr) {
							String slDRSql = "select * from tb_report where file_id=? and software_id=? and flag=0";
							ps = conn.prepareStatement(slDRSql);
							ps.setInt(1, Integer.parseInt(dataId));
							ps.setInt(2, softwareId);
							rs = ps.executeQuery();
							if (rs.next()) {// update
								String upSql = "update tb_report set state=?,end_date=now() where file_id=? and software_id=?";
								ps = conn.prepareStatement(upSql);
								ps.setInt(1, ReportState.COMPLETE);
								ps.setInt(2, Integer.parseInt(dataId));
								ps.setInt(3, softwareId);
								ps.executeUpdate();
							} else {// insert
								String upSql = "insert into tb_report(file_id,software_id,state,flag,user_id,create_date) values(?,?,?,?,?,now())";
								ps = conn.prepareStatement(upSql);
								ps.setInt(1, Integer.parseInt(dataId));
								ps.setInt(2, softwareId);
								ps.setInt(3, ReportState.COMPLETE);
								ps.setInt(4, 0);
								ps.setInt(5, userId);
								ps.executeUpdate();
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int saveQcProDataReport(int userId, List<Data> dataList,
			int softwareId) {
		log.info("添加项目数据Qc报告信息");
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (Data data : dataList) {
				// 添加数据报告之前先查询是否存在，如果存在的话因为是新增，所以直接修改状态为正在运行但是还没有产生报告状态1，否则新增一条记录
				String qSql = "select * from tb_report where user_id=? and software_id=? and file_id=? and flag=0";
				ps = conn.prepareStatement(qSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, data.getFileId());
				rs = ps.executeQuery();
				if (rs.next()) {
					String uSql = "update tb_report set state=1 where user_id=? and software_id=? and file_id=? and flag=0";
					ps = conn.prepareStatement(uSql);
					ps.setInt(1, userId);
					ps.setInt(2, softwareId);
					ps.setInt(3, data.getFileId());
					ps.executeUpdate();
				} else {
					String insertSql = "insert into tb_report(user_id,software_id,file_id,state,create_date) values(?,?,?,1,now())";
					ps = conn.prepareStatement(insertSql);
					ps.setInt(1, userId);
					ps.setInt(2, softwareId);
					ps.setInt(3, data.getFileId());
					ps.executeUpdate();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("添加项目数据报告Qc信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int updateReportState(int userId, int fileId, int softwareId,
			int state) {
		int result = 1;
		String sql = "update tb_report set state=" + state + " where user_id="
				+ userId + " and file_id=" + fileId + " and software_id="
				+ softwareId + " and flag=0";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int updateReportState(int fileId, int softwareId, int state) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String reSql = "select run_data from tb_software where software_id=?";
			ps = conn.prepareStatement(reSql);
			ps.setInt(1, softwareId);
			rs = ps.executeQuery();
			int runData = 1;
			if (rs.next()) {
				runData = rs.getInt("run_data");
			}
			if (runData == 0) {
				String slDRSql = "select * from tb_report where file_id=? and software_id=? and flag=0";
				ps = conn.prepareStatement(slDRSql);
				ps.setInt(1, fileId);
				ps.setInt(2, softwareId);
				rs = ps.executeQuery();
				if (rs.next()) {// update
					String upSql = "update tb_report set state=? where file_id=? and software_id=?";
					ps = conn.prepareStatement(upSql);
					ps.setInt(1, ReportState.COMPLETE);
					ps.setInt(2, fileId);
					ps.setInt(3, softwareId);
					ps.executeUpdate();
				} else {// insert
					String upSql = "insert into tb_report(file_id,software_id,state,flag,create_date) values(?,?,?,?,now())";
					ps = conn.prepareStatement(upSql);
					ps.setInt(1, fileId);
					ps.setInt(2, softwareId);
					ps.setInt(3, ReportState.COMPLETE);
					ps.setInt(4, 0);
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int updateReportByDataKeyProjectId(List<Data> dataList,
			int projectId, int softwareId, int state) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (Data data : dataList) {
				String dsql = "update tb_report set state=" + state
						+ " where file_id=" + data.getFileId()
						+ " and software_id=" + softwareId + " and flag=0";
				ps = conn.prepareStatement(dsql);
				ps.executeUpdate();
			}
			String psql = "update tb_report set state=" + state
					+ " where project_id=" + projectId + " and software_id="
					+ softwareId + " and flag=1";
			ps = conn.prepareStatement(psql);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int getUserIdBySoftIdDataKey(int softwareId, int fileId) {
		int userId = 0;
		String sql = "select user_id from tb_report where file_id=? and software_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fileId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return userId;
	}

	@Override
	public List<String> checkProsReport(String proIds) {
		StringBuffer sb = new StringBuffer();
		for (String proId : proIds.split(",")) {
			if (proId != null && !proId.equals("")) {
				sb.append(proId + ",");
			}
		}
		String rProIds = sb.toString();
		if (null == rProIds || "".equals(rProIds) || "null".equals(rProIds)) {
			return null;
		} else {
			rProIds = rProIds.substring(0, rProIds.length() - 1);
		}
		List<String> proNames = new ArrayList<String>();
		String sql = "select project_name from tb_project where project_id in(select project_id from tb_report where project_id in("
				+ rProIds + ") and state>" + ReportState.UNRUN + ")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				proNames.add(rs.getString("project_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return proNames;
	}

	@Override
	public boolean checkDatamiRNAReportState(String dataKey, int softwareId) {
		boolean complete = true;
		int fileId = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String dataSql = "select file_id from tb_file where data_key=?";
			ps = conn.prepareStatement(dataSql);
			ps.setString(1, dataKey);
			rs = ps.executeQuery();
			if (rs.next()) {
				fileId = rs.getInt("file_id");
			}
			String rSql = "select state from tb_report where flag=0 and file_id=? and software_id=?";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, fileId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			int state = 0;
			if (rs.next()) {
				state = rs.getInt("state");
			}
			if (state < ReportState.COMPLETE) {
				complete = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return complete;
	}

	@Override
	public boolean checkPromiRNAReportState(int projectId, int softwareId) {
		boolean complete = true;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String rSql = "select state from tb_report where flag=1 and project_id=? and software_id=?";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, projectId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			int state = 0;
			if (rs.next()) {
				state = rs.getInt("state");
			}
			if (state < ReportState.COMPLETE) {
				complete = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return complete;
	}

	@Override
	public List<String> checkDatasReportState(String dataIds, int softwareId) {
		List<String> fileNames = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			for (String dataId : dataIds.split(",")) {
				String rSql = "select f.file_name,r.state from tb_report r,tb_file f where r.file_id=f.file_id and r.flag=0 and r.file_id=? and r.software_id=?";
				ps = conn.prepareStatement(rSql);
				ps.setInt(1, Integer.parseInt(dataId));
				ps.setInt(2, softwareId);
				rs = ps.executeQuery();
				int state = 0;
				if (rs.next()) {
					String fileName = rs.getString("file_name");
					state = rs.getInt("state");
					if (state < ReportState.COMPLETE) {
						fileNames.add(fileName);
					}
				} else {
					String dataSql = "select file_name from tb_file where file_id=?";
					ps = conn.prepareStatement(dataSql);
					ps.setInt(1, Integer.parseInt(dataId));
					rs = ps.executeQuery();
					if (rs.next()) {
						String fileName = rs.getString("file_name");
						fileNames.add(fileName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return fileNames;
	}

	@Override
	public int addDatasReport(int userId, int softwareId, String dataIds,
			int state) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (String dataId : dataIds.split(",")) {
				// 添加数据报告之前先查询是否存在，如果存在的话因为是新增，所以直接修改状态为正在运行但是还没有产生报告状态1，否则新增一条记录
				String qSql = "select * from tb_report where user_id=? and software_id=? and file_id=? and flag=0";
				ps = conn.prepareStatement(qSql);
				ps.setInt(1, userId);
				ps.setInt(2, softwareId);
				ps.setInt(3, Integer.parseInt(dataId));
				rs = ps.executeQuery();
				if (rs.next()) {
					String uSql = "update tb_report set state=? where user_id=? and software_id=? and file_id=? and flag=0";
					if (state > ReportState.UNRUN) {
						uSql = "update tb_report set state=?,create_date=now() where user_id=? and software_id=? and file_id=? and flag=0";
					}
					ps = conn.prepareStatement(uSql);
					ps.setInt(1, state);
					ps.setInt(2, userId);
					ps.setInt(3, softwareId);
					ps.setInt(4, Integer.parseInt(dataId));
					ps.executeUpdate();
				} else {
					String insertSql = "insert into tb_report(user_id,software_id,file_id,state,create_date) values(?,?,?,?,now())";
					if (state > ReportState.UNRUN) {
						insertSql = "insert into tb_report(user_id,software_id,file_id,state,create_date) values(?,?,?,?,now())";
					}
					ps = conn.prepareStatement(insertSql);
					ps.setInt(1, userId);
					ps.setInt(2, softwareId);
					ps.setInt(3, Integer.parseInt(dataId));
					ps.setInt(4, state);
					ps.executeUpdate();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			result = 0;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("添加项目数据报告信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public List<String> getFileNamesByDataIds(String dataIds, Integer appId) {
		List<String> fileNames = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			conn.setAutoCommit(false);
			for (String dataId : dataIds.split(",")) {
				String relateSql = "select r.project_id from tb_data_project_relat r,tb_project p where r.file_id=? and p.state = ? and r.project_id = p.project_id";
				ps = conn.prepareStatement(relateSql);
				ps.setInt(1, Integer.parseInt(dataId));
				ps.setInt(2, DataState.ACTIVE);
				rs = ps.executeQuery();
				List<Integer> proIds = new ArrayList<Integer>();
				while (rs.next()) {
					proIds.add(rs.getInt("project_id"));
				}
				if (proIds.size() > 0) {
					for (Integer proId : proIds) {
						String proReportSql = "select * from tb_report where project_id=? and software_id=? and flag=1 and (state=? or state=?)";
						ps = conn.prepareStatement(proReportSql);
						ps.setInt(1, proId);
						ps.setInt(2, appId);
						ps.setInt(3, ReportState.HAVEREPORT);
						ps.setInt(4, ReportState.NOREPORT);
						rs = ps.executeQuery();
						if (rs.next()) {
							String dataSql = "select file_name from tb_file where file_id=?";
							ps = conn.prepareStatement(dataSql);
							ps.setInt(1, Integer.parseInt(dataId));
							rs = ps.executeQuery();
							if (rs.next()) {
								fileNames.add(rs.getString("file_name"));
							}
						}
					}
				}
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.info("检查数据所在项目报告信息失败！");
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return fileNames;
	}

	@Override
	public List<Software> getSiftListByProId(int projectId) {
		List<Software> softList = new ArrayList<Software>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select software_id from tb_report where project_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Software soft = new Software();
				int softId = rs.getInt("software_id");
				soft.setSoftwareId(softId);
				String sql1 = "select param from tb_software where software_id=?";
				ps1 = conn.prepareStatement(sql1);
				ps1.setInt(1, softId);
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					soft.setParam(rs1.getInt("param"));
				}
				softList.add(soft);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
			ConnectManager.free(conn, ps1, rs1);
		}
		return softList;
	}

	@Override
	public int updateReportReadStateByData(int fileId, int softwareId) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			String rSql = "update tb_report set readed=1 where software_id=? and file_id=? and flag=0";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, softwareId);
			ps.setInt(2, fileId);
			ps.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public int updateReportReadStateByPro(int projectId, int softwareId) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectManager.getConnection();
			String rSql = "update tb_report set readed=1 where software_id=? and project_id=? and flag=1";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, softwareId);
			ps.setInt(2, projectId);
			ps.executeUpdate();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public PageList<UserReport> getDataReportList(int userId, int appId,
			String start, String end, String fileName, Page page) {
		if (page == null) {
			page = new Page();
			page.setCurrentPage(1);
			page.setPageSize(10);
		}
		String sql = "select * from (select r.file_id,r.state,r.readed,r.create_date as create_date,date_format(r.create_date,'%Y-%m-%d') as compare_date,file_name,data_key,r.software_id,s.software_name"
				+ " from"
				+ " (select * from tb_report"
				+ " where file_id in"
				+ " (select file_id from celloud.tb_file_share where state = 0 and user_id = "
				+ userId
				+ " union select file_id from celloud.tb_file where user_id = "
				+ userId
				+ ")) as r,"
				+ " tb_file f,tb_software s where s.software_id=r.software_id and f.file_id=r.file_id) result ";

		if (fileName != null && !fileName.equals("")
				&& !fileName.equals("null")) {
			sql += " where result.file_name like '%" + fileName
					+ "%' or result.data_key like '%" + fileName + "%'";
		} else {
			boolean appIdState = appId != 0;
			boolean dateState = start != null && !start.equals("")
					&& !start.equals("null");
			if (appIdState && dateState) {
				sql += " where result.software_id = " + appId
						+ " and result.compare_date <= '" + end
						+ "' and result.compare_date>=" + "'" + start + "'";
			}
			if (appIdState && !dateState) {
				sql += " where result.software_id = " + appId + "";
			}
			if (!appIdState && dateState) {
				sql += " where result.compare_date <= '" + end
						+ "' and result.compare_date>=" + "'" + start + "'";
			}
		}

		sql += " order by result.create_date desc limit "
				+ (page.getCurrentPage() - 1) * page.getPageSize() + ","
				+ page.getPageSize();
		String countSql = sql.substring(0, sql.indexOf("order by"))
				.replaceFirst("\\*", "count(\\*)");
		PageList<UserReport> reportDataPageList = new PageList<UserReport>();
		List<UserReport> reportList = new ArrayList<UserReport>();
		UserReport userReport = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalNum = 0;
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				userReport = new UserReport();
				userReport.setFileId(rs.getInt("file_id"));
				userReport.setState(rs.getInt("state"));
				userReport.setReaded(rs.getInt("readed"));
				userReport.setCreateDate(rs.getTimestamp("create_date"));
				userReport.setFileName(rs.getString("file_name"));
				userReport.setDataKey(rs.getString("data_key"));
				userReport.setSoftwareId(rs.getInt("software_id"));
				userReport.setSoftwareName(rs.getString("software_name"));
				userReport.setShareState(ShareState.SELF);
				reportList.add(userReport);
			}

			ps = conn.prepareStatement(countSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				totalNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		page.make(totalNum);
		reportDataPageList.setDatas(reportList);
		reportDataPageList.setPage(page);
		return reportDataPageList;
	}

	@Override
	public PageList<UserReport> getAllDataReport(int userId, String fileName,
			Page page) {
		PageList<UserReport> reportDataPageList = new PageList<UserReport>();
		List<UserReport> reportList = new ArrayList<UserReport>();
		List<UserReport> temp = new ArrayList<UserReport>();
		UserReport userReport = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String rSql = "select * from(select r.file_id,r.state,r.readed,r.create_date,file_name,data_key,r.software_id,s.software_name from tb_file f,tb_report r,tb_software s where s.software_id=r.software_id and f.file_id=r.file_id and r.flag=0 and r.state>? and r.user_id=?) t";
			if (fileName != null && !fileName.equals("")
					&& !fileName.equals("null")) {
				rSql += " where t.file_name like '%" + fileName
						+ "%' or t.data_key like '%" + fileName + "%'";
			}
			rSql += " order by t.create_date desc";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, ReportState.UNRUN);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userReport = new UserReport();
				userReport.setFileId(rs.getInt("file_id"));
				userReport.setState(rs.getInt("state"));
				userReport.setReaded(rs.getInt("readed"));
				userReport.setFileName(rs.getString("file_name"));
				userReport.setDataKey(rs.getString("data_key"));
				userReport.setSoftwareId(rs.getInt("software_id"));
				userReport.setSoftwareName(rs.getString("software_name"));
				userReport.setCreateDate(rs.getTimestamp("create_date"));
				userReport.setState(rs.getInt("state"));
				userReport.setShareState(ShareState.SELF);
				reportList.add(userReport);
			}

			// 获取共享给当前用户的数据报告
			String srSql = "select * from(select r.file_id,r.state,r.readed,r.create_date,f.file_name,f.data_key,r.software_id,s.software_name from tb_file f,tb_report r,tb_software s where s.software_id=r.software_id and f.file_id=r.file_id and r.state>? and r.file_id in(select file_id from tb_file_share where user_id=? and state=0)) t";
			if (fileName != null && !fileName.equals("")
					&& !fileName.equals("null")) {
				srSql += " where t.file_name like '%" + fileName
						+ "%' or t.data_key like '%" + fileName + "%'";
			}
			ps = conn.prepareStatement(srSql);
			ps.setInt(1, ReportState.UNRUN);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userReport = new UserReport();
				userReport.setFileId(rs.getInt("file_id"));
				userReport.setState(rs.getInt("state"));
				userReport.setReaded(rs.getInt("readed"));
				userReport.setFileName(rs.getString("file_name"));
				userReport.setDataKey(rs.getString("data_key"));
				userReport.setSoftwareId(rs.getInt("software_id"));
				userReport.setSoftwareName(rs.getString("software_name"));
				userReport.setCreateDate(rs.getTimestamp("create_date"));
				userReport.setState(rs.getInt("state"));
				userReport.setShareState(ShareState.SHARE);
				reportList.add(userReport);
			}

			if (reportList.size() > 0) {
				Map<Integer, String> fileIdMap = new HashMap<Integer, String>();
				for (UserReport ur : reportList) {
					fileIdMap.put(ur.getFileId(), ur.getFileName());
				}
				List<Report> repList = null;
				Report report = null;
				for (Map.Entry<Integer, String> entry : fileIdMap.entrySet()) {
					repList = new ArrayList<Report>();
					String dataKey = null;
					for (UserReport ur : reportList) {
						if (ur.getFileId() == entry.getKey()) {
							dataKey = ur.getDataKey();
							report = new Report();
							report.setState(ur.getState());
							report.setDataKey(ur.getDataKey());
							report.setSoftwareId(ur.getSoftwareId());
							report.setSoftwareName(ur.getSoftwareName());
							report.setReaded(ur.getReaded());
							report.setCreateDate(ur.getCreateDate());
							report.setShareState(ur.getShareState());
							if (ur.getSoftwareId() != 11) {
								repList.add(report);
							}
						}
					}
					userReport = new UserReport();
					userReport.setFileId(entry.getKey());
					userReport.setFileName(entry.getValue());
					userReport.setDataKey(dataKey);
					userReport.setReportList(repList);
					temp.add(userReport);
				}

				if (page == null) {
					page = new Page();
					page.setCurrentPage(1);
					page.setPageSize(10);
				}
				page.make(temp.size());
				int start = (page.getCurrentPage() - 1) * page.getPageSize();
				int pageSize = page.getPageSize();
				for (int i = 0; i < temp.size(); i++) {
					if (i < start || i > start + pageSize - 1) {
						temp.get(i).setShow(1);// 1表示不显示，0表示显示
					} else {
						temp.get(i).setShow(0);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		reportDataPageList.setDatas(temp);
		reportDataPageList.setPage(page);
		return reportDataPageList;
	}

	@Override
	public int deleteReportByFileSoftId(int fileId, int softwareId) {
		int result = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectManager.getConnection();
			String rSql = "select state from tb_report where flag=0 and file_id=? and software_id=?";
			ps = conn.prepareStatement(rSql);
			ps.setInt(1, fileId);
			ps.setInt(2, softwareId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int state = rs.getInt("state");
				if (state > ReportState.UNRUN && state < ReportState.COMPLETE) {
					result = 2;
					return result;
				} else {
					String delSql = "delete from tb_report where flag=0 and file_id=? and software_id=?";
					ps = conn.prepareStatement(delSql);
					ps.setInt(1, fileId);
					ps.setInt(2, softwareId);
					result = ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return result;
	}

	@Override
	public int updatePrintContext(int userId, int softwareId, int id, int flag,
			String context) {
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			conn = ConnectManager.getConnection();
			String con = flag == 0 ? ("file_id=" + id) : ("project_id=" + id);
			String sql = "update tb_report set print_context = ? where user_id=? and software_id=? and "
					+ con + " and flag=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, context);
			ps.setInt(2, userId);
			ps.setInt(3, softwareId);
			ps.setInt(4, flag);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	@Override
	public String getPrintContext(int userId, int softwareId, int id, int flag) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String report = null;
		try {
			conn = ConnectManager.getConnection();
			String con = flag == 0 ? ("file_id=" + id) : ("project_id=" + id);
			String sql = "select print_context from tb_report where user_id=? and software_id=? and "
					+ con + " and flag=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, softwareId);
			ps.setInt(3, flag);
			rs = ps.executeQuery();
			if (rs.next()) {
				report = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return report;
	}

	// TODO sql拼接需优化
	@Override
	public PageList<Map<String, String>> getReportList(int userId, Page page,
			String proName, String start, String end, int appId) {
		if (page == null) {
			page = new Page();
			page.setCurrentPage(1);
			page.setPageSize(10);
		}
		Connection conn = ConnectManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PageList<Map<String, String>> pageList = new PageList<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.*,r.end_date,r.context,r.state,s.software_id,s.software_name from (select project_id,project_name,create_date,num,size,share,user_id,'no_one' as userName from tb_project where user_id = ? and state = 0 union all select p.project_id,project_name,p.create_date,num,size,0 as share,s.sharer_id as user_id,u.username as userName from tb_project p,tb_file_share s,tb_user u where p.state=0 and p.project_id=s.project_id and s.user_id = ? and s.state = 1 and u.user_id = s.sharer_id ) as p,tb_report as r,tb_software s");
		sql.append(" where r.project_id = p.project_id and r.software_id = s.software_id ");
		if (StringUtils.isNotEmpty(proName)) {
			sql.append(" and r.context like '%").append(proName).append("%' ");
		}
		if (StringUtils.isNotEmpty(start)) {
			sql.append(" and p.create_date<='").append(end).append("' and p.create_date>='").append(start).append("' ");
		}
		if (appId != 0) {
			sql.append(" and s.software_id=").append(appId);
		}
		String sqlC = sql.toString();
		sql.append(" order by p.create_date desc limit ?,?");
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, (page.getCurrentPage() - 1) * page.getPageSize());
			ps.setInt(4, page.getPageSize());
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i < num + 1; i++) {
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
			ps = conn.prepareStatement(sqlC);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				rs.last(); // 移到最后一行
				page.make(rs.getRow());// 得到当前行号，也就是记录数
			}
			pageList.setPage(page);
			pageList.setDatas(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return pageList;
	}

	@Override
	public String getFilePath(int fileId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String path = null;
		try {
			conn = ConnectManager.getConnection();

			String sql = "select path from tb_file where file_id= ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fileId);

			rs = ps.executeQuery();
			if (rs.next()) {
				path = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return path;
	}

	@Override
	public PageList<Map<String, String>> getReportList(int userId, Page page) {
		Connection conn = ConnectManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PageList<Map<String, String>> pageList = new PageList<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sqlC = "select p.*,r.end_date,r.context,r.state,s.software_id,s.software_name from ( select project_id,project_name,create_date,num,size,share,user_id,'no_one' as userName from tb_project where user_id = ? and state = 0 union all select p.project_id,project_name,p.create_date,num,size,0 as share,s.sharer_id as user_id,u.username as userName from tb_project p,tb_file_share s,tb_user u where p.state=0 and p.project_id=s.project_id and s.user_id = ? and s.state = 1 and u.user_id = s.sharer_id ) as p,tb_report as r,tb_software s where r.project_id = p.project_id and r.software_id = s.software_id";
		String sqlQ = sqlC + " order by p.create_date desc,project_id asc limit ?,?";
		try {
			ps = conn.prepareStatement(sqlQ);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, (page.getCurrentPage() - 1) * page.getPageSize());
			ps.setInt(4, page.getPageSize());
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int num = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i < num + 1; i++) {
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
			ps = conn.prepareStatement(sqlC);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				rs.last(); // 移到最后一行
				page.make(rs.getRow());// 得到当前行号，也就是记录数
			}
			pageList.setPage(page);
			pageList.setDatas(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			ConnectManager.free(conn, ps, rs);
		}
		return pageList;
	}

	@Override
	public SoftCMPGene getCMPGeneByName(String gname) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SoftCMPGene cmpGene = new SoftCMPGene();
		String sql = "select * from tb_cmp_gene where cmp_name like '" + gname
				+ "%';";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				cmpGene.setCmpId(rs.getInt("cmp_id"));
				cmpGene.setCmpName(rs.getString("cmp_name"));
				cmpGene.setCmpDescription(rs.getString("cmp_description"));
				cmpGene.setCmpTreatment(rs.getString("cmp_treatment"));
				cmpGene.setCmpTreatmentDesc(rs.getString("cmp_treatment_desc"));
				cmpGene.setCmpImage(rs.getString("cmp_image"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return cmpGene;
	}

	@Override
	public int addCmpDrug(CmpDrug cmpDrug) {
		int addReportNum = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into tb_cmp_drug(gene,location,drug) values(?,?,?);";
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, cmpDrug.getGene());
			ps.setString(2, cmpDrug.getLocation());
			ps.setString(3, cmpDrug.getDrug());
			addReportNum = ps.executeUpdate();
		} catch (SQLException e) {
			log.info("添加个性化用药失败");
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return addReportNum;
	}
}