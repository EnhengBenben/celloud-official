package com.nova.service.impl;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.nova.dao.IReportDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.CmpDrug;
import com.nova.sdo.Data;
import com.nova.sdo.Report;
import com.nova.sdo.SoftCMPGene;
import com.nova.sdo.Software;
import com.nova.sdo.UserReport;
import com.nova.service.IReportService;

public class ReportServiceImpl implements IReportService {
	@Inject
	private IReportDao reportDao;

	@Override
	public int addReportInfo(Report report) {
		return reportDao.addReportInfo(report);
	}

	@Override
	public int getTotalReportNum(int userId) {
		return reportDao.getTotalReportNum(userId);
	}

	@Override
	public int getPrivateReportNum(int userId) {
		return reportDao.getPrivateReportNum(userId);
	}

	@Override
	public int addProReportInfo(Report report) {
		return reportDao.addProReportInfo(report);
	}

	@Override
	public List<Report> getReportListByProId(int projectId) {
		return reportDao.getReportListByProId(projectId);
	}

	@Override
	public int updateReportState(int reportId, int state) {
		int result = 1;
		try {
			reportDao.updateReportState(reportId, state);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	@Override
	public int goToRunApp(int userId, String softwareId, String dataId,
			int reportId) {
		int result = 1;
		try {
			// 修改报告状态
			updateReportState(reportId, 2);
		} catch (Exception e) {
			result = 0;
			System.err.println("app运行失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getReportIdByUserFileId(String userId, String fileId) {
		return reportDao.getReportIdByUserFileId(userId, fileId);
	}

	@Override
	public int saveTreeProject(Integer userId, String dataIds, int softwareId) {
		return reportDao.saveTreeProject(userId, dataIds, softwareId);
	}

	@Override
	public boolean existsReport(int userId, int softwareId, int dataId) {
		return reportDao.existsReport(userId, softwareId, dataId);
	}

	@Override
	public int saveProDataReport(int userId, List<Data> dataList, int softwareId) {
		return reportDao.saveProDataReport(userId, dataList, softwareId);
	}

	@Override
	public int getSoftwareIdByProjectId(int projectId) {
		return reportDao.getSoftwareIdByProjectId(projectId);
	}

	@Override
	public int updateReportStateByProjectId(int projectId, int state) {
		return reportDao.updateReportStateByProjectId(projectId, state);
	}

	@Override
	public int delReport(int userId, List<Data> dataList, int softwareId) {
		return reportDao.delReport(userId, dataList, softwareId);
	}

	@Override
	public boolean proExistsReport(int userId, int projectId) {
		return reportDao.proExistsReport(userId, projectId);
	}

	@Override
	public boolean deleteProReport(int userId, int projectId) {
		return reportDao.deleteProReport(userId, projectId);
	}

	@Override
	public int saveSnpReport(int userId, int projectId, String dataIds,
			int softId) {
		return reportDao.saveSnpReport(userId, projectId, dataIds, softId);
	}

	@Override
	public boolean hasDataReport(int dataId, int softwareId) {
		return reportDao.hasDataReport(dataId, softwareId);
	}

	@Override
	public boolean hasProReport(int projectId, int softwareId) {
		return reportDao.hasProReport(projectId, softwareId);
	}

	@Override
	public boolean deleteReport(int reportId) {
		return reportDao.deleteReport(reportId);
	}

	@Override
	public int updateReportStateByProSoftId(int userId, int projectId,
			int softwareId, int state, String context) {
		return reportDao.updateReportStateByProSoftId(userId, projectId,
				softwareId, state, context);
	}

	@Override
	public int saveQcProDataReport(int userId, List<Data> dataList,
			int softwareId) {
		return reportDao.saveQcProDataReport(userId, dataList, softwareId);
	}

	@Override
	public int updateReportState(int userId, int fileId, int softwareId,
			int state) {
		return reportDao.updateReportState(userId, fileId, softwareId, state);
	}

	@Override
	public int updateReportState(int fileId, int softwareId, int state) {
		return reportDao.updateReportState(fileId, softwareId, state);
	}

	@Override
	public int updateReportByDataKeyProjectId(List<Data> dataList,
			int projectId, int softwareId, int state) {
		return reportDao.updateReportByDataKeyProjectId(dataList, projectId,
				softwareId, state);
	}

	@Override
	public int getUserIdBySoftIdDataKey(int softwareId, int fileId) {
		return reportDao.getUserIdBySoftIdDataKey(softwareId, fileId);
	}

	@Override
	public List<String> checkProsReport(String proIds) {
		return reportDao.checkProsReport(proIds);
	}

	@Override
	public boolean checkDatamiRNAReportState(String dataKey, int softwareId) {
		return reportDao.checkDatamiRNAReportState(dataKey, softwareId);
	}

	@Override
	public boolean checkPromiRNAReportState(int projectId, int softwareId) {
		return reportDao.checkPromiRNAReportState(projectId, softwareId);
	}

	@Override
	public List<String> checkDatasReportState(String dataIds, int softwareId) {
		return reportDao.checkDatasReportState(dataIds, softwareId);
	}

	@Override
	public List<String> checkProjectHasReport(int userId, int projectId) {
		return reportDao.checkProjectHasReport(userId, projectId);
	}

	@Override
	public int addDatasReport(int userId, int softwareId, String dataIds,
			int state) {
		return reportDao.addDatasReport(userId, softwareId, dataIds, state);
	}

	@Override
	public List<String> getFileNamesByDataIds(String dataIds, Integer appId) {
		return reportDao.getFileNamesByDataIds(dataIds, appId);
	}

	@Override
	public List<Software> getSiftListByProId(int projectId) {
		return reportDao.getSiftListByProId(projectId);
	}

	@Override
	public boolean deleteReportByProSoftId(int reportId, int projectId,
			int softwareId) {
		return reportDao.deleteReportByProSoftId(reportId, projectId,
				softwareId);
	}

	@Override
	public int updateReportReadStateByData(int fileId, int softwareId) {
		return reportDao.updateReportReadStateByData(fileId, softwareId);
	}

	@Override
	public int updateReportReadStateByPro(int projectId, int softwareId) {
		return reportDao.updateReportReadStateByPro(projectId, softwareId);
	}

	@Override
	public PageList<UserReport> getAllDataReport(int userId, String fileName,
			Page page) {
		return reportDao.getAllDataReport(userId, fileName, page);
	}

	@Override
	public int deleteReportByFileSoftId(int fileId, int softwareId) {
		return reportDao.deleteReportByFileSoftId(fileId, softwareId);
	}

	@Override
	public PageList<UserReport> getDataReportList(int userId, int appId,
			String start, String end, String fileName, Page page) {
		return reportDao.getDataReportList(userId, appId, start, end, fileName,
				page);
	}

	@Override
	public int updatePrintContext(int userId, int softwareId, int id, int flag,
			String context) {
		return reportDao.updatePrintContext(userId, softwareId, id, flag,
				context);
	}

	@Override
	public String getPrintContext(int userId, int softwareId, int id, int flag) {
		return reportDao.getPrintContext(userId, softwareId, id, flag);
	}

	@Override
	public PageList<Map<String, String>> getReportList(int userId, Page page,
			String proName, String start, String end, int appId) {
		return reportDao
				.getReportList(userId, page, proName, start, end, appId);
	}

	@Override
	public PageList<Map<String, String>> getReportList(int userId, Page page) {
		return reportDao.getReportList(userId, page);
	}

	/**
	 * 根据文件编号,获得文件路径
	 * 
	 * @param fileId
	 * @param
	 * @return
	 */
	public String getFilePath(int fileId) {
		return this.reportDao.getFilePath(fileId);
	}

	@Override
	public SoftCMPGene getCMPGeneByName(String gname) {
		return reportDao.getCMPGeneByName(gname);
	}

	@Override
	public int addCmpDrug(CmpDrug cmpDrug) {
		return reportDao.addCmpDrug(cmpDrug);
	}

}
