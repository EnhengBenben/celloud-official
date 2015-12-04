package com.nova.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.constants.DataState;
import com.nova.constants.FileFormat;
import com.nova.constants.ReportState;
import com.nova.constants.ReportType;
import com.nova.email.EmailService;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Data;
import com.nova.sdo.Project;
import com.nova.sdo.Report;
import com.nova.sdo.Software;
import com.nova.sdo.User;
import com.nova.service.IDataService;
import com.nova.service.IProjectService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.service.IUserService;
import com.nova.utils.DataUtil;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;
import com.nova.utils.TemplateUtil;

@ParentPackage("celloud-default")
@Action("data")
@Results({
		@Result(name = "getAppDatas", type = "json", params = { "root",
				"privateDataPageList" }),
		@Result(name = "privateData", location = "../../pages/data/myData.jsp"),
		@Result(name = "info", type = "json", params = { "root", "result" }),
		@Result(name = "getSoftList", type = "json", params = { "root",
				"softwareList" }),
		@Result(name = "success", type = "json", params = { "root", "flag" }),
		@Result(name = "userData", location = "../../pages/admin/dataList.jsp"),
		@Result(name = "getDataByKey", type = "json", params = { "root", "data" }) })
public class DataAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(DataAction.class);
	@Inject
	private IDataService dataService;
	@Inject
	private IProjectService projectService;
	@Inject
	private IReportService reportService;
	@Inject
	private IUserService userService;
	@Inject
	private ISoftwareService softwareService;
	private PageList<Data> privateDataPageList;
	private PageList<Data> sharedDataPageList;

	private Page page;
	private String dataTag;
	private String strain;
	private String dataNum;
	private List<Report> reportList;
	private List<User> userList;
	// 排序,type=1代表按照文件名进行排序,type=2代表按照上传时间排序，sort代表排序规则
	private int type;
	private String sort;

	private int fileId;
	private int projectId;
	private String projectName;
	private String fileName;

	private int flag;
	private String userIds;

	private String dataKey;
	private List<Data> fileList;

	private String toolsPath;
	private String downloadPath;
	private String stage;

	private String report;
	private List<Map<String, String>> sharedUserMapList;
	private String userNames;

	private Map<String, List<Map<String, String>>> strainMap;
	private int pageRecordNum;

	// 根据用户编号、数据类型获取数据列表
	private int userId;
	private String appName;

	private String dataIds;
	private String result;
	private String proIds;
	private List<Map<String, String>> strainMapList;
	private Data data;
	private String formats;

	// 前端拼接完成的需要投递到远机器上执行的命令
	private String requestUrl;

	private String start;
	private String end;
	/** 上传文件的最近一天的时间 */
	private Date lastDate;
	private Integer softwareId;
	// app端传过来的文本内容
	private String sequence;
	private List<Software> softwareList;

	/**
	 * 运行数据或者项目的流程APP
	 * 
	 * @return
	 */
	public void runAppForData() {
		RemoteRequests rr = new RemoteRequests();
		requestUrl = PropertiesUtil.toolsPath + "Procedure!runApp" + requestUrl;
		log.info("requestUrl:" + requestUrl);
		rr.run(requestUrl);
	}

	/**
	 * 后台数据清理用的方法，根据用户名获取其数据列表
	 * 
	 * @return
	 */
	public String getUserData() {
		userId = userService.getUserIdByName(userNames);
		privateDataPageList = dataService.getDataList(dataTag, page, userId,
				type, sort);
		return "userData";
	}

	/**
	 * 后台数据清理用的方法，删除数据本身和数据库记录
	 * 
	 * @return
	 */
	public String deleteData() {
		flag = dataService.delDatas(dataIds);
		if (flag == 1) {
			new File(PropertiesUtil.bigFilePath + fileName).delete();
		}
		return SUCCESS;
	}

	public String getDataByKey() {
		data = dataService.getDataByKey(dataKey);
		return "getDataByKey";
	}

    public String changeAnotherName() {
        String[] result = dataIds.split(";");
        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                String[] result1 = StringUtils
                        .splitByWholeSeparatorPreserveAllTokens(result[i], ",");
                int fileId = Integer.parseInt(result1[0]);
                data = new Data();
                data.setFileId(fileId);
                data.setAnotherName(result1[1]);
                flag = dataService.updateAnotherNameById(data);
            }
        }
        return SUCCESS;
    }

	/**
	 * 后台导出数据
	 * 
	 * @return
	 */
	public String outPutData() {
		fileName = String.valueOf(new Date().getTime() + ".txt");
		String path = "/share/data/output/" + fileName;
		try {
			new File(path).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataService.outPutData(userIds, start, end, path);
		return "outPutData";
	}

	/**
	 * 修改报告阅读状态
	 * 
	 * @return
	 */
	public String updateReportReadByDataKeySoftId() {
		Data data = dataService.getDataByKey(dataKey);
		flag = reportService.updateReportReadStateByData(data.getFileId(),
				Integer.parseInt(proIds));
		return SUCCESS;
	}

	/**
	 * 小工具调用 App端保存上传的数据并且运行
	 * 
	 * @return
	 */
	public String saveTextToFileAndRun() {
		int userId = (Integer) session.get("userId");
        String fname = appName + softwareId + ".fasta";
        Data data = new Data();
        data.setUserId(userId);
        data.setFileName(fname);
        data.setState(DataState.DEELTED);
        int dataId = dataService.addDataInfo(data);
        dataKey = DataUtil.getNewDataKey(dataId);
		File file = new File(PropertiesUtil.bigFilePath + dataKey + ".fasta");
		StringBuffer newSeq = new StringBuffer();
		try {
			if (!sequence.substring(0, 1).equals(">")) {
				newSeq.append(">").append(dataKey).append("\n");
				String[] seq = StringUtils.split(sequence, ">");
				for (int j = 0; j < seq.length; j++) {
					String seq_j = seq[j];
					String state_seq = "";
					if (j == 0) {
						state_seq = seq_j.replaceAll("\n", "");
					} else {
						int n_num = seq_j.indexOf("\n");
						newSeq.append(">").append(seq_j.substring(0, n_num))
								.append("\n");
						state_seq = seq_j.substring(n_num, seq_j.length())
								.replaceAll("\n", "");
					}
					for (int i = 1; i < state_seq.length(); i++) {
						if (state_seq.length() - i > 80) {
							if (i % 80 == 0) {
								newSeq.append(state_seq.substring(i - 80, i))
										.append("\n");
							}
						} else {
							newSeq.append(state_seq.substring(i,
									state_seq.length()));
							break;
						}
					}
					if (j < seq.length) {
						newSeq.append("\n");
					}
				}
			} else {
				String[] seq = StringUtils.split(sequence, ">");
				for (int j = 0; j < seq.length; j++) {
					String seq_j = seq[j];
					int n_num = seq_j.indexOf("\n");
					newSeq.append(">").append(seq_j.substring(0, n_num))
							.append("\n");
					String state_seq = seq_j.substring(n_num, seq_j.length())
							.replaceAll("\n", "");
					for (int i = 1; i < state_seq.length(); i++) {
						if (state_seq.length() - i > 80) {
							if (i % 80 == 0) {
								newSeq.append(state_seq.substring(i - 80, i))
										.append("\n");
							}
						} else {
							newSeq.append(state_seq.substring(i,
									state_seq.length()));
							break;
						}
					}
					if (j < seq.length) {
						newSeq.append("\n");
					}
				}
			}
			// 写入文件并且保存文件信息
			FileUtils.writeStringToFile(file, newSeq.toString());
			data.setSize((long) sequence.length());
			data.setFileFormat(FileFormat.FA);
			data.setDataKey(dataKey);
			data.setPath(PropertiesUtil.bigFilePath + dataKey + ".fasta");
			data = dataService.saveDataInfo(data);

			// 创建一个项目
			Project project = new Project();
			project.setUserId(userId);
            String projectName = dataKey;
			project.setProjectName(projectName);
			int projectId = projectService.createProject(project);

			// 创建项目和数据管理
			dataService
					.allocateDatasToProject(data.getFileId() + "", projectId);

			// 创建项目报告
			Report report = new Report();
			report.setProjectId(projectId);
			report.setUserId(userId);
			report.setFileId(0);
			report.setSoftwareId(softwareId);
			report.setState(ReportState.UNRUN);
			report.setFlag(ReportType.PROJECT);
			reportService.addProReportInfo(report);

			// 创建数据报告
			report = new Report();
			report.setUserId(userId);
			report.setFileId(data.getFileId());
			report.setSoftwareId(softwareId);
			report.setState(ReportState.UNRUN);
			report.setFlag(ReportType.DATA);
			reportService.addReportInfo(report);

			// 调用app运行
			String url = PropertiesUtil.toolsPath + "Procedure!runApp?userId="
					+ userId + "&appId=" + softwareId + "&appName=" + appName
					+ "&projectName=" + projectName + "&email="
					+ session.get("email") + "&dataKeyList=" + dataKey + ","
					+ dataKey + ".fasta" + "," + dataKey + ".fasta;&projectId="
					+ projectId;
			RemoteRequests rr = new RemoteRequests();
			rr.run(url);
			flag = 1;
		} catch (IOException e) {
			flag = 0;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private int fileFormat;

	/**
	 * 根据数据编号获取文件格式
	 * 
	 * @return
	 */
	public String getFileFormatById() {
		fileFormat = dataService.getDataById(dataIds.split(",")[0])
				.getFileFormat();
		return "getFileFormatById";
	}

	/**
	 * 批量删除数据
	 */
	public String delDatas() {
		flag = dataService.delDatas(dataIds);
		return "delDatas";
	}

	/**
	 * 验证数据类型是否一致
	 * 
	 * @return
	 */
	public String validateDataType() {
		flag = 1;
		int dataType = 0;
		String[] newDataIds = dataIds.split(",");
		for (int i = 0; i < newDataIds.length; i++) {
			int newDataType = dataService.getDataById(newDataIds[i])
					.getFileFormat();
			if (i == 0) {
				dataType = newDataType;
			} else {
				if (newDataType != dataType) {
					flag = 0;
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 用于CMP运行数据监测 判断
	 * 
	 * @return
	 */
	public String checkCmpData() {
		flag = 0;
		String[] newDataIds = dataIds.split(",");
		String fileName;
		int dot;
		String newName;
		int length;
		List<String> list = new ArrayList<String>();
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < newDataIds.length; i++) {
			fileName = dataService.getDataById(newDataIds[i]).getFileName();
			dot = fileName.lastIndexOf('.');
			newName = fileName.substring(0, dot);
			length = newName.length();
			if (newName.endsWith("1") || newName.endsWith("2")) {
				list.add(newName.substring(0, length - 1));
				strBuf.append(newName).append(",");
			} else {
				flag = 1;
			}
		}
		for (String s : list) {
			int count = 0;
			Pattern p = Pattern.compile(s);
			Matcher m = p.matcher(strBuf);
			while (m.find()) {
				count++;
			}
			if (count < 2) {
				flag = 1;
				break;
			}
		}
		return SUCCESS;
	}

	/**
	 * 验证数据的数据类型与app数据类型是否一致
	 * 
	 * @return
	 */
	private String softwareName;

	public String validateDataAppType() {
		flag = 0;
		int softId = softwareService.getSoftIdByName(softwareName);
		List<Integer> formatList = softwareService
				.getTypeListBySoftwareId(softId);
		int dataType = dataService.getDataById(fileId + "").getFileFormat();
		for (Integer fileType : formatList) {
			if (fileType.intValue() == dataType) {
				flag = 1;
				return SUCCESS;
			}
		}
		return SUCCESS;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	// 数据共享发送邮件
	public String sendEmail() {
		String userName = (String) session.get("userName");
		if (!userNames.equals("")) {
			flag = 1;
			String[] userNameArr = userNames.split(",");
			for (String uName : userNameArr) {
				String email = userService.getEmailByUserName(uName);
				String emailTemplate = TemplateUtil.readTemplate(0);
				String sendMsg = emailTemplate
						.replaceAll("#shareType", "数据")
						.replaceAll("#userName", uName)
						.replaceAll("#shareUserName", userName)
						.replaceAll(
								"#dataName",
								dataService.getDataById(fileId + "")
										.getFileName())
						.replaceAll("#dataKey", fileId + "");
				EmailService.send(email, sendMsg, true);
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据文件编号获取文件名
	 * 
	 * @return
	 */
	public String getFileNameByDataKey() {
		result = dataService.getDataByKey(dataKey).getFileName();
		return SUCCESS;
	}

	/**
	 * 根据项目编号获取数据
	 * 
	 * @return
	 */
	private String proType;

	public String getDataInfoListByProjectId() {
		fileList = dataService.getDataListByProjectId(projectId);
		return SUCCESS;
	}

	/**
	 * 根据文件编号获取数据app列表
	 * 
	 * @return
	 */
	private String proToolsPath;
	private int proUserId;
	private int dataReportFlag;

	public int getProUserId() {
		return proUserId;
	}

	public void setProUserId(int proUserId) {
		this.proUserId = proUserId;
	}

	public String getProToolsPath() {
		return proToolsPath;
	}

	public void setProToolsPath(String proToolsPath) {
		this.proToolsPath = proToolsPath;
	}

	/**
	 * 获取私有数据
	 * 
	 * @return
	 */
	public String getMyOwnData() {
		userId = (Integer) super.session.get("userId");
		if (null == page) {
			pageRecordNum = 10;
		} else {
			pageRecordNum = page.getPageSize();
		}
		long start = new Date().getTime();
		privateDataPageList = dataService.getDataList(dataTag, page, userId,
				type, sort);
		long mid = new Date().getTime();
		System.out.println("获取私有数据用时：" + (mid - start));
		lastDate = dataService.getLatestDate(userId);
		long end = new Date().getTime();
		System.out.println("获取用户最近输入数据的日期用时：" + (end - mid));
		return "privateData";
	}

	/**
	 * 根据文件编号获取文件名
	 * 
	 * @return
	 */
	public String getDataNameById() {
		fileName = dataService.getDataById(fileId + "").getFileName();
		return SUCCESS;
	}

	/**
	 * 获取APP中的数据列表
	 * 
	 * @return
	 */
	public String getAppDataList() {
		userId = (Integer) super.session.get("userId");
		String formats = softwareService.getFormatsByAppId(softwareId);
		privateDataPageList = dataService.getAppDataById(userId, page, formats,
				softwareId);
		return "getAppDatas";
	}

	public PageList<Data> getPrivateDataPageList() {
		return privateDataPageList;
	}

	public void setPrivateDataPageList(PageList<Data> privateDataPageList) {
		this.privateDataPageList = privateDataPageList;
	}

	public PageList<Data> getSharedDataPageList() {
		return sharedDataPageList;
	}

	public void setSharedDataPageList(PageList<Data> sharedDataPageList) {
		this.sharedDataPageList = sharedDataPageList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getDataTag() {
		return dataTag;
	}

	public void setDataTag(String dataTag) {
		this.dataTag = dataTag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDataNum() {
		return dataNum;
	}

	public void setDataNum(String dataNum) {
		this.dataNum = dataNum;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public List<Data> getFileList() {
		return fileList;
	}

	public void setFileList(List<Data> fileList) {
		this.fileList = fileList;
	}

	public String getStrain() {
		return strain;
	}

	public void setStrain(String strain) {
		this.strain = strain;
	}

	public String getToolsPath() {
		return toolsPath;
	}

	public void setToolsPath(String toolsPath) {
		this.toolsPath = toolsPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Map<String, String>> getSharedUserMapList() {
		return sharedUserMapList;
	}

	public void setSharedUserMapList(List<Map<String, String>> sharedUserMapList) {
		this.sharedUserMapList = sharedUserMapList;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, List<Map<String, String>>> getStrainMap() {
		return strainMap;
	}

	public void setStrainMap(Map<String, List<Map<String, String>>> strainMap) {
		this.strainMap = strainMap;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getPageRecordNum() {
		return pageRecordNum;
	}

	public void setPageRecordNum(int pageRecordNum) {
		this.pageRecordNum = pageRecordNum;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getProIds() {
		return proIds;
	}

	public void setProIds(String proIds) {
		this.proIds = proIds;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<Map<String, String>> getStrainMapList() {
		return strainMapList;
	}

	public void setStrainMapList(List<Map<String, String>> strainMapList) {
		this.strainMapList = strainMapList;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public int getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(int fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getDataReportFlag() {
		return dataReportFlag;
	}

	public void setDataReportFlag(int dataReportFlag) {
		this.dataReportFlag = dataReportFlag;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Integer softwareId) {
		this.softwareId = softwareId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public List<Software> getSoftwareList() {
		return softwareList;
	}

	public void setSoftwareList(List<Software> softwareList) {
		this.softwareList = softwareList;
	}

}