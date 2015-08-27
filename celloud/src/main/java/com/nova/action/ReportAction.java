package com.nova.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.CmpDrug;
import com.nova.sdo.Data;
import com.nova.sdo.Project;
import com.nova.sdo.Report;
import com.nova.sdo.SoftCMPGene;
import com.nova.sdo.Software;
import com.nova.sdo.UserReport;
import com.nova.service.IDataService;
import com.nova.service.IProjectService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.utils.Base64Util;
import com.nova.utils.PropertiesUtil;

/**
 * 数据报告的显示管理
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-31上午09:08:54
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("report")
@Results({
	@Result(name = "getReportPageList", location = "../../pages/report/projectReportList.jsp"),
	@Result(name = "getAppList", type = "json", params = { "root",
		"softList" }),
	@Result(name = "clickItemDataReport", type = "json", params = { "root",
		"state" }),
	@Result(name = "prevDataReport", type = "json", params = { "root",
		"state" }),
	@Result(name = "updateContext", type = "json", params = { "root",
		"flag" }),
	@Result(name = "nextDataReport", type = "json", params = { "root",
		"state" }),
	@Result(name = "cmpGene", type = "json", params = { "root", "cmpGene" }) })
public class ReportAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(ReportAction.class);
    @Inject
    private IReportService reportService;
    @Inject
    private IDataService dataService;
    @Inject
    private IProjectService projectService;
    @Inject
    private ISoftwareService softwareService;

    private Project project;

    private String softwareIds;
    private String dataIds;
    private String softName;
    private Report report;
    private int state;
    private List<String> proNames;
    private List<String> fileNames;
    private List<Software> softList;
    private String start;
    private String end;

    private int fileId;

    private List<Report> proReportList;
    private String proToolsPath;
    private String proDownloadPath;
    private String proStage;

    private int proUserId;
    private int proReportFlag;

    /**
     * 获取所有的报告列表
     * 
     * @return
     */
    private Page page;
    private PageList<UserReport> reportList;
    private PageList<Map<String, String>> list;

    /**
     * 直接点击邮箱链接查看报告
     * 
     * @return
     */
    private Integer userId;
    private int appId;
    private String fileName;
    private String dataKey;
    private String projectId;
    private String sampleList;
    private String param;

    private String context;

    private String url;
    private String reportResult;

    /**
     * 获取我的报告数量
     */
    private String myReportNum;

    // 运行app
    private int softwareId;
    private String dataId;
    private int reportId;
    private int flag;
    private String dataKeys;

    private boolean result;
    // 允许app返回的结果
    private String appResult;
    private SoftCMPGene cmpGene;
    private String gname;

    public String getCmpGeneInfo() {
	cmpGene = reportService.getCMPGeneByName(gname);
	return "cmpGene";
    }

    public String addCmpDrug() {
	if (context != null && !("").equals(context)) {
	    String[] str1 = StringUtils.splitByWholeSeparatorPreserveAllTokens(
		    context, "@");
	    for (int i = 0; i < str1.length - 1; i++) {
		CmpDrug cmpDrug = new CmpDrug();
		String[] str2 = StringUtils
			.splitByWholeSeparatorPreserveAllTokens(str1[i], "#");
		cmpDrug.setGene(getArray(str2, 0));
		cmpDrug.setLocation(getArray(str2, 1));
		cmpDrug.setDrug(getArray(str2, 2));
		flag = reportService.addCmpDrug(cmpDrug);
	    }
	}
	return "updateContext";
    }

    /**
     * 获取app列表
     * 
     * @return
     */
    public String getAppList() {
	softList = softwareService.getRunDataApp();
	return "getAppList";
    }

    public String getAppListById() {
	userId = (Integer) super.session.get("userId");
	softList = softwareService.getRunDataAppById(userId);
	return "getAppList";
    }

    /**
     * 获取数据报告列表
     * 
     * @return
     */
    public String getReportPageList() {
	userId = (Integer) super.session.get("userId");
	list = reportService.getReportList(userId, page);
	return "getReportPageList";
    }

    /**
     * 根据条件检索报告列表
     * 
     * @return String
     */
    public String getReportPageListByCondition() {
	userId = (Integer) super.session.get("userId");
	// fileName实际传入的是文件名的模糊查询条件
	list = reportService.getReportList(userId, page, fileName, start, end,
		appId);
	return "getReportPageList";
    }

    /********************************** 以上为新增报告模块 ****************************************/
    public String updateContext() {
	flag = reportService.updatePrintContext(userId, appId, fileId, flag,
		context);
	return "updateContext";
    }

    public String viewReportFromEmail() {
	HttpURLConnection conn = null;
	BufferedReader br = null;
	url = PropertiesUtil.toolsPath + "Procedure!readReport";
	reportResult = null;
	if (param == null || param.equals("") || param.equals("null")) {
	    return "viewReportFromEmail";
	} else {
	    try {
		param = Base64Util.decode(param).substring(4);
		param = param.substring(0, param.length() - 5);
		url += "?" + param;
		log.info("url:" + url);
		System.err.println("url:" + url);
		conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setConnectTimeout(120000);
		conn.setReadTimeout(240000);
		conn.connect();
		br = new BufferedReader(new InputStreamReader(
			conn.getInputStream()));
		StringBuffer buff = new StringBuffer();
		String lineStr = null;
		while ((lineStr = br.readLine()) != null) {
		    buff.append(lineStr);
		}
		reportResult = buff.toString();
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    } catch (MalformedURLException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    } finally {
		if (br != null)
		    try {
			br.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
	    }
	    return "viewReportFromEmail";
	}
    }

    /**
     * 点击数据报告列表查看报告
     * 
     * @return String
     */
    public String clickItemDataReport() {
	log.info("点击数据报告列表查看报告");
	return "clickItemDataReport";
    }

    /**
     * 上一页数据报告
     * 
     * @return String
     */
    public String prevDataReport() {
	log.info("点击数据报告列表查看上一页数据报告");
	return "prevDataReport";
    }

    /**
     * 下一页数据报告
     * 
     * @return String
     */
    public String nextDataReport() {
	log.info("点击数据报告列表查看下一页数据报告");
	return "nextDataReport";
    }

    /**
     * 获取数据/项目报告
     * 
     * @return
     */
    public String getDataReport() {
	HttpURLConnection conn = null;
	BufferedReader br = null;
	try {
	    log.info("url:" + url);
	    conn = (HttpURLConnection) new URL(url).openConnection();
	    conn.setRequestMethod("GET");
	    conn.setDoOutput(true);
	    conn.setConnectTimeout(120000);
	    conn.setReadTimeout(240000);
	    conn.connect();

	    br = new BufferedReader(
		    new InputStreamReader(conn.getInputStream()));
	    StringBuffer buff = new StringBuffer();
	    String lineStr = null;
	    while ((lineStr = br.readLine()) != null) {
		buff.append(lineStr);
	    }
	    reportResult = buff.toString();
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null)
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	return SUCCESS;
    }

    /**
     * 获取项目添加的app
     * 
     * @return
     */
    public String getProjectSoftList() {
	softList = reportService
		.getSiftListByProId(Integer.parseInt(projectId));
	return SUCCESS;
    }

    /**
     * 检查数据所在项目报告状态
     * 
     * @return
     */
    public String checkDatasInProReportState() {
	fileNames = reportService.getFileNamesByDataIds(dataIds, softwareId);
	return SUCCESS;
    }

    /**
     * 添加数据报告
     * 
     * @return
     */
    public String addDatasReport() {
	userId = (Integer) super.session.get("userId");
	if (StringUtils.isNotEmpty(dataKeys)) {
	    dataIds = String.valueOf(dataService.getDataByKey(dataKeys)
		    .getFileId());
	}
	state = reportService
		.addDatasReport(userId, softwareId, dataIds, state);
	return SUCCESS;
    }

    /**
     * 根据数据编号检查数据miRNA报告是否运行完成
     * 
     * @return
     */
    public String checkDatasReportState() {
	fileNames = reportService.checkDatasReportState(dataIds, softwareId);
	return SUCCESS;
    }

    /**
     * 检查项目miRNA报告是否运行完成
     * 
     * @return
     */
    public String checkPromiRNAReportState() {
	result = reportService.checkPromiRNAReportState(
		Integer.parseInt(projectId), softwareId);
	return SUCCESS;
    }

    /**
     * 检查数据miRNA报告是否运行完成
     * 
     * @return
     */
    public String checkDatamiRNAReportState() {
	result = reportService.checkDatamiRNAReportState(dataKeys, softwareId);
	return SUCCESS;
    }

    /**
     * 检查项目是否有报告
     * 
     * @return
     */
    public String checkProReport() {
	proNames = reportService.checkProsReport(projectId);
	return SUCCESS;
    }

    public String getAppResult() {
	return appResult;
    }

    public void setAppResult(String appResult) {
	this.appResult = appResult;
    }

    public String getUserIdBySoftIdDataKey() {
	int fileId = dataService.getDataByKey(dataKeys).getFileId();
	userId = reportService.getUserIdBySoftIdDataKey(softwareId, fileId);
	return SUCCESS;
    }

    /**
     * 根据数据编号修改报告状态，参数dataKeys、softwareIds、state
     * 
     * @return
     */
    public String updateReportByDataKey() {
	userId = (Integer) super.session.get("userId");
	int fileId = dataService.getDataByKey(dataKeys).getFileId();
	flag = reportService.updateReportState(userId, fileId, softwareId,
		state);
	return SUCCESS;
    }

    /**
     * Qc修改报告状态（项目、数据均需要修改）
     * 
     * @参数：dataKeys、softwareId、projectId、state
     * @return
     */
    public String updateReportByDataKeyProjectId() {
	ServletActionContext.getResponse().setHeader(
		"Access-Control-Allow-Origin", "*");
	List<Data> dataList = dataService.getDataListByProjectId(Integer
		.parseInt(projectId));
	flag = reportService.updateReportByDataKeyProjectId(dataList,
		Integer.parseInt(projectId), softwareId, state);
	return SUCCESS;
    }

    /**
     * 修改数据报告
     * 
     * @return
     */
    public String updateReportByDataKeySoftId() {
	ServletActionContext.getResponse().setHeader(
		"Access-Control-Allow-Origin", "*");
	int fileId = dataService.getDataByKey(dataKeys).getFileId();
	flag = reportService.updateReportState(fileId, softwareId, state);
	return SUCCESS;
    }

    /**
     * 新增项目下的数据报告，参数projectId、softName
     * 
     * @return
     */
    public String saveProjectDataListReport() {
	userId = (Integer) super.session.get("userId");
	softwareId = softwareService.getSoftIdByName(softName);
	List<Data> dataList = dataService.getDataListByProjectId(Integer
		.parseInt(projectId));
	flag = reportService.saveQcProDataReport(userId, dataList, softwareId);
	return SUCCESS;
    }

    /**
     * 修改项目报告 需要三个参数:projectId、softwareIds、state
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String updateReportByProSoftId() throws UnsupportedEncodingException {
	ServletActionContext.getResponse().setHeader(
		"Access-Control-Allow-Origin", "*");
	if (context != null) {
	    context = context.replaceAll(" ", "+");
	    context = Base64Util.decrypt(context);
	}
	flag = reportService.updateReportStateByProSoftId(userId,
		Integer.parseInt(projectId), Integer.parseInt(softwareIds),
		state, context);
	return SUCCESS;
    }

    public String saveSnpReport() {
	int softId = softwareService.getSoftIdByName(softName);
	int projectId = projectService.getProjectIdByName(project
		.getProjectName());
	userId = (Integer) super.session.get("userId");
	flag = reportService.saveSnpReport(userId, projectId, dataIds, softId);
	return SUCCESS;
    }

    public String delReportByProjectId() {
	flag = 1;
	try {
	    reportService.updateReportStateByProjectId(
		    Integer.parseInt(projectId), 0);
	    int softwareId = reportService.getSoftwareIdByProjectId(Integer
		    .parseInt(projectId));
	    List<Data> dataList = dataService.getDataListByProjectId(Integer
		    .parseInt(projectId));
	    userId = (Integer) super.session.get("userId");
	    reportService.delReport(userId, dataList, softwareId);
	} catch (Exception e) {
	    flag = 0;
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    public String addProDataReport() {
	List<Data> dataList = dataService.getDataListByProjectId(Integer
		.parseInt(projectId));
	userId = (Integer) super.session.get("userId");
	flag = reportService.saveProDataReport(userId, dataList, softwareId);
	return SUCCESS;
    }

    public String updateReportStateByReportId() {
	// 修改状态为1，即正在运行但是没有没有报告的状态
	flag = reportService.updateReportState(reportId, state);
	return SUCCESS;
    }

    public String existsReport() {
	softwareId = softwareService.getSoftIdByName(projectId);
	int dataId1 = dataService.getDataByKey(dataId).getFileId();
	userId = (Integer) super.session.get("userId");
	result = reportService.existsReport(userId, softwareId, dataId1);
	return SUCCESS;
    }

    public String updateReportState() {
	userId = (Integer) super.session.get("userId");
	if (StringUtils.isNotEmpty(dataKeys) && (!"null".equals(dataKeys))) {
	    String[] dataKeyArr = dataKeys.split(",");
	    for (String key : dataKeyArr) {
		String dataId = dataService.getDataByKey(key).getFileId() + "";
		String reportId = reportService.getReportIdByUserFileId(
			userId.toString(), dataId);
		reportService.updateReportState(Integer.parseInt(reportId), 2);
	    }
	    flag = 1;
	} else {
	    flag = 0;
	}
	return SUCCESS;
    }

    /**
     * 运行app
     * 
     * @return
     */
    public String goToRunApp() {
	userId = (Integer) super.session.get("userId");
	flag = reportService.goToRunApp(userId, softwareId + "", dataId,
		reportId);
	return SUCCESS;
    }

    public String getMyReportNumInfo() {
	userId = (Integer) super.session.get("userId");
	int totalReportNum = reportService.getTotalReportNum(userId);// 共享给我的报告个数
	int privateReportNum = reportService.getPrivateReportNum(userId);
	myReportNum = totalReportNum + "," + privateReportNum;
	return "totalReportNum";
    }

    public String addProReportInfo() {
	log.info("添加项目报告");
	userId = (Integer) super.session.get("userId");
	Report report = new Report();
	int projectId = 0;
	if (project.getProjectName() == null
		|| project.getProjectName().equals("")) {
	    projectId = project.getProjectId();
	} else {
	    projectId = projectService.getProjectIdByName(project
		    .getProjectName());
	}
	int mRNAId = softwareService.getSoftIdByName(softName);
	report.setProjectId(projectId);
	report.setUserId(userId);
	report.setSoftwareId(mRNAId);
	report.setState(0);// 0：未运行
	report.setFlag(1);// 1:项目报告
	flag = reportService.addReportInfo(report);
	return "addProReport";
    }

    public String addManyDataReport() {
	String softwareId[] = softwareIds.split(",");
	userId = (Integer) super.session.get("userId");
	for (int i = 0; i < softwareId.length; i++) {
	    boolean hasReport = reportService.hasDataReport(
		    Integer.parseInt(dataId), Integer.parseInt(softwareId[i]));
	    if (!hasReport) {
		Report report = new Report();
		report.setFileId(Integer.parseInt(dataId));
		// report.setProjectId(Integer.parseInt(projectId));
		report.setUserId(userId);
		report.setSoftwareId(Integer.parseInt(softwareId[i]));
		report.setState(0);// 0：未运行
		report.setFlag(0);// 0:数据报告
		flag += reportService.addReportInfo(report);
	    }
	}
	return "addManyDataReport";
    }

    public String getProSoftwareList() {
	proStage = "";
	// 获取项目所有人的userId,不管是当前登陆人还是共享项目的所有人
	proUserId = projectService.getUserIdByProjectId(project.getProjectId());
	proReportList = reportService.getReportListByProId(project
		.getProjectId());
	for (Report report : proReportList) {
	    proStage += report.getState() + "," + report.getReportId() + ","
		    + report.getUserId() + "," + report.getSoftwareName() + ","
		    + report.getProjectId() + ";";
	}
	proToolsPath = PropertiesUtil.toolsOutPath;
	proDownloadPath = ServletActionContext.getServletContext().getRealPath(
		"/pages/upload");
	return "prosoftwareList";
    }

    public String checkProjectHasReport() {
	userId = (Integer) super.session.get("userId");
	proNames = reportService.checkProjectHasReport(userId,
		Integer.parseInt(projectId));
	return "checkProjectHasReport";
    }

    public String checkProHasReport() {
	userId = (Integer) super.session.get("userId");
	result = reportService.proExistsReport(userId,
		Integer.parseInt(projectId));
	return "checkProHasReport";
    }

    public String deleteProReport() {
	userId = (Integer) super.session.get("userId");
	result = reportService.deleteProReport(userId,
		Integer.parseInt(projectId));
	return "deleteProReport";
    }

    /**
     * 删除指定报告
     * 
     * @return
     */
    public String deleteReport() {
	result = reportService.deleteReport(reportId);
	return "deleteReport";
    }

    public String deleteReportByProSoftId() {
	result = reportService.deleteReportByProSoftId(reportId,
		Integer.parseInt(projectId), softwareId);
	return "deleteReportByProSoftId";
    }

    private static String getArray(String[] n, int num) {
	return n == null ? null : (n.length > num ? n[num] : null);
    }

    public String getProToolsPath() {
	return proToolsPath;
    }

    public void setProToolsPath(String proToolsPath) {
	this.proToolsPath = proToolsPath;
    }

    public String getProDownloadPath() {
	return proDownloadPath;
    }

    public void setProDownloadPath(String proDownloadPath) {
	this.proDownloadPath = proDownloadPath;
    }

    public String getProStage() {
	return proStage;
    }

    public void setProStage(String proStage) {
	this.proStage = proStage;
    }

    /**
     * 获取session中的用户编号
     * 
     * @return
     */
    public int getUserId() {
	userId = (Integer) super.session.get("userId");
	return userId;
    }

    public String getMyReportNum() {
	return myReportNum;
    }

    public void setMyReportNum(String myReportNum) {
	this.myReportNum = myReportNum;
    }

    public List<Report> getProReportList() {
	return proReportList;
    }

    public void setProReportList(List<Report> proReportList) {
	this.proReportList = proReportList;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public int getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(int softwareId) {
	this.softwareId = softwareId;
    }

    public String getDataId() {
	return dataId;
    }

    public void setDataId(String dataId) {
	this.dataId = dataId;
    }

    public int getReportId() {
	return reportId;
    }

    public void setReportId(int reportId) {
	this.reportId = reportId;
    }

    public int getFlag() {
	return flag;
    }

    public void setFlag(int flag) {
	this.flag = flag;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public String getDataKeys() {
	return dataKeys;
    }

    public void setDataKeys(String dataKeys) {
	this.dataKeys = dataKeys;
    }

    public String getProjectId() {
	return projectId;
    }

    public void setProjectId(String projectId) {
	this.projectId = projectId;
    }

    public boolean isResult() {
	return result;
    }

    public void setResult(boolean result) {
	this.result = result;
    }

    public String getSoftwareIds() {
	return softwareIds;
    }

    public void setSoftwareIds(String softwareIds) {
	this.softwareIds = softwareIds;
    }

    public String getDataIds() {
	return dataIds;
    }

    public void setDataIds(String dataIds) {
	this.dataIds = dataIds;
    }

    public String getSoftName() {
	return softName;
    }

    public void setSoftName(String softName) {
	this.softName = softName;
    }

    public Report getReport() {
	return report;
    }

    public void setReport(Report report) {
	this.report = report;
    }

    public int getState() {
	return state;
    }

    public void setState(int state) {
	this.state = state;
    }

    public int getProUserId() {
	return proUserId;
    }

    public void setProUserId(int proUserId) {
	this.proUserId = proUserId;
    }

    public List<String> getProNames() {
	return proNames;
    }

    public void setProNames(List<String> proNames) {
	this.proNames = proNames;
    }

    public List<String> getFileNames() {
	return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
	this.fileNames = fileNames;
    }

    public List<Software> getSoftList() {
	return softList;
    }

    public void setSoftList(List<Software> softList) {
	this.softList = softList;
    }

    public int getProReportFlag() {
	return proReportFlag;
    }

    public void setProReportFlag(int proReportFlag) {
	this.proReportFlag = proReportFlag;
    }

    public String getReportResult() {
	return reportResult;
    }

    public void setReportResult(String reportResult) {
	this.reportResult = reportResult;
    }

    public int getAppId() {
	return appId;
    }

    public void setAppId(int appId) {
	this.appId = appId;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getDataKey() {
	return dataKey;
    }

    public void setDataKey(String dataKey) {
	this.dataKey = dataKey;
    }

    public String getSampleList() {
	return sampleList;
    }

    public void setSampleList(String sampleList) {
	this.sampleList = sampleList;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getParam() {
	return param;
    }

    public void setParam(String param) {
	this.param = param;
    }

    public PageList<UserReport> getReportList() {
	return reportList;
    }

    public void setReportList(PageList<UserReport> reportList) {
	this.reportList = reportList;
    }

    public Page getPage() {
	return page;
    }

    public void setPage(Page page) {
	this.page = page;
    }

    public int getFileId() {
	return fileId;
    }

    public void setFileId(int fileId) {
	this.fileId = fileId;
    }

    public String getEnd() {
	return end;
    }

    public void setEnd(String end) {
	this.end = end;
    }

    public String getStart() {
	return start;
    }

    public void setStart(String start) {
	this.start = start;
    }

    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }

    public PageList<Map<String, String>> getList() {
	return list;
    }

    public void setList(PageList<Map<String, String>> list) {
	this.list = list;
    }

    public SoftCMPGene getCmpGene() {
	return cmpGene;
    }

    public void setCmpGene(SoftCMPGene cmpGene) {
	this.cmpGene = cmpGene;
    }

    public String getGname() {
	return gname;
    }

    public void setGname(String gname) {
	this.gname = gname;
    }

}
