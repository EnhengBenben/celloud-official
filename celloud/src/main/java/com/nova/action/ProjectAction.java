package com.nova.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.nova.email.EmailService;
import com.nova.pager.PageList;
import com.nova.sdo.Company;
import com.nova.sdo.Data;
import com.nova.sdo.DataType;
import com.nova.sdo.Project;
import com.nova.sdo.ProjectParam;
import com.nova.sdo.Report;
import com.nova.sdo.Software;
import com.nova.sdo.User;
import com.nova.service.ICompanyService;
import com.nova.service.IDataService;
import com.nova.service.IDataTypeService;
import com.nova.service.IProjectService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.service.IUserService;
import com.nova.utils.FileTools;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;
import com.nova.utils.TemplateUtil;
import com.nova.utils.XmlUtil;

/**
 * 项目管理
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-30上午08:54:48
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("project")
@Results({
		@Result(name = "RunProject", type = "json", params = { "root", "error" }),
		@Result(name = "success", type = "json", params = { "root", "userNames" }),
		@Result(name = "returnBoolean", type = "json", params = { "root",
				"flag" }),
		@Result(name = "toSaveRunedCmp", type = "json", params = { "root",
				"flag" }) })
public class ProjectAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ProjectAction.class);
	@Inject
	private IProjectService projectService;
	@Inject
	private IUserService userService;
	@Inject
	private IDataService dataService;
	@Inject
	private IReportService reportService;
	@Inject
	private IDataTypeService dataTypeService;
	@Inject
	private ISoftwareService softwareService;
	@Inject
	private ICompanyService companyService;
	private Integer userId;
	private int userid;
	private String userNames;
	private int shareUserId;
	private int dataSize;
	private Project project;
	private String projectName;
	private int dataId;
	private boolean flag;
	private int result;
	private String dataIds;
	private List<Project> proList;
	private Data data;
	// 共享项目时的提示内容
	private String errorMessage = "";
	private Map<String, List<Map<String, String>>> dataTypeMap;// 数据类型列表信息
	private List<Map<String, String>> strainMapList;
	private List<Map<String, String>> proMapList;
	private String appName;
	private String projectId;
	private ProjectParam proParam;
	private Map<String, Object> proParams;
	private String softwareId;
	private int error;
	private PageList<Project> myProNamePageList;
	private PageList<Project> sharedProNamePageList;
	private List<Data> proDataList;// 项目中的数据信息
	private String strain;
	private PageList<Project> privaProPageList;// 所有项目列表
	private PageList<Project> shareProPageList;// 共享给用户的项目列表
	private List<Project> proNameList;// 用户的项目名称列表
	private int sortByType;// 排序类型 1：按项目名称排序，2：按启动时间排序

	public String toSaveRunedCmp() {
		String source = "/share/data/webapps/Tools/upload/88/110/";
		File upload = new File(source);
		File[] files = upload.listFiles();
		Map<String, List<Data>> map = new HashMap<String, List<Data>>();
		for (File f : files) {
			String data1 = f.getName();
			if (data1.length() > 7) {
				String data2 = "";
				if (data1.equals("20150609090539")) {
					data2 = "20150609309453";
				} else if (data1.equals("20150609648660")) {
					data2 = "20150609886427";
				} else if (data1.equals("20150609918308")) {
					data2 = "20150609127108";
				} else if (data1.equals("20150610114501")) {
					data2 = "20150610888196";
				} else if (data1.equals("20150626658029")) {
					data2 = "20150626745382";
				} else if (data1.equals("20150717570992")) {
					data2 = "20150717944085";
				} else if (data1.equals("20150720053040")) {
					data2 = "20150720461290";
				}
				List<Data> dataList = dataService.getDataByDataKeys(data1 + ","
						+ data2, 88);
				map.put(data1, dataList);
				Company c = companyService.getCompanyByUserId(88);
				User user = userService.getUserById(88);

				String newPath = PropertiesUtil.toolsOutPath
						+ "Procedure!runApp?userId=" + 88 + "&appId=" + 110
						+ "&appName=CMP&dataKey=" + data1 + "&dataInfos="
						+ JSONObject.toJSONString(map) + "&company="
						+ JSONObject.toJSONString(c) + "&user="
						+ JSONObject.toJSONString(user);
				RemoteRequests rr = new RemoteRequests();
				rr.run(newPath);
			}
		}
		return "toSaveRunedCmp";
	}
	/**
	 * 下载项目pdf
	 */
	public String downPdf() {
		StringBuffer sb = new StringBuffer();
		sb.append(userId).append(";").append(softwareId).append(";")
				.append(projectId).append(";");
		List<Data> list = projectService.getAllFileInProject(Integer
				.parseInt(projectId));
		for (Data data : list) {
			sb.append(data.getDataKey()).append(",");
		}
		RemoteRequests rr = new RemoteRequests();
		String requestUrl = PropertiesUtil.toolsPath
				+ "Procedure!downPDF?dataKeyList="
				+ sb.toString();
		log.info("requestUrl:" + requestUrl);
		userNames = rr.pdf(requestUrl);
		return SUCCESS;
	}

	/**
	 * 在app页面运行时调用的方法
	 * 
	 * @return
	 */
	public String run() {
		log.info("----------------运行APP Begin----------");
		// 1.新建项目
		userId = (Integer) super.session.get("userId");
		projectName = new Date().getTime() + "";
		proList = projectService.getAllProNameList(userId);
		while (existsProName(projectName)) {
			projectName = new Date().getTime() + "";
		}
		project = new Project();
		project.setUserId(userId);
		project.setProjectName(projectName);
		// 根据softwareName获取对应的数据类型
		int dataFormat = dataService.getDataById(dataIds.split(",")[0])
				.getFileFormat();
		project.setDataFormat(dataFormat);
		flag = projectService.insertProject(project);
		if (!flag) {
			error = 1;
			log.error("创建项目失败");
			return "RunProject";
		}
		// 2.根据项目名称获取项目ID
		int proId = projectService.getProjectIdByName(projectName);
		// 3.新增数据项目关系
		int isError = dataService.allocateDatasToProject(dataIds, proId);
		if (isError == 0) {
			error = 2;
			log.error("创建项目数据关系失败");
			return "RunProject";
		}
		// 4.为该项目和app添加项目报告
		boolean hasReport = reportService.hasProReport(proId,
				Integer.parseInt(softwareId));
		if (!hasReport) {
			Report report = new Report();
			report.setProjectId(proId);
			report.setUserId(userId);
			report.setSoftwareId(Integer.parseInt(softwareId));
			report.setState(1);// 1：正在运行
			report.setFlag(1);// 1:项目报告
			reportService.addReportInfo(report);
		}
		// 5.根据 appIds 获取 datakeys
		StringBuffer dataResult = new StringBuffer();
		String[] dataIdArr = dataIds.split(",");
		if (dataIdArr.length > 0) {
			for (String dataId : dataIdArr) {
				if (StringUtils.isNotEmpty(dataId)) {
					Data data = dataService.getDataById(dataId);
					String filename = data.getFileName();
					String datakey = data.getDataKey();
					// int index = filename.lastIndexOf(".");
					String ext = FileTools.getExtName(filename);
					dataResult
							.append(datakey)
							.append(",")
							.append(datakey)
							.append(ext)
							.append(",")
							.append(filename)
							.append(",")
							.append(StringUtils.isEmpty(data.getAnotherName()) ? null
									: data.getAnotherName()).append(";");
				}
			}
		}
		Map<String, List<Data>> map = new HashMap<String, List<Data>>();
		if (Integer.parseInt(softwareId) == 110
				|| Integer.parseInt(softwareId) == 111) {
			String dataDetails = FileTools.dataListSort(dataResult.toString());
			String dataArray[] = dataDetails.split(";");
			for (int i = 0; i < dataArray.length; i = i + 2) {
				String[] dataDetail = dataArray[i].split(",");
				String[] dataDetail1 = dataArray[i + 1].split(",");
				System.out.println(FileTools.getArray(dataDetail, 0) + ","
						+ FileTools.getArray(dataDetail1, 0));
				List<Data> dataList = dataService.getDataByDataKeys(
						FileTools.getArray(dataDetail, 0) + ","
								+ FileTools.getArray(dataDetail1, 0), userId);
				System.out.println("------------dataArray length---------"
						+ dataList.size());
				map.put(FileTools.getArray(dataDetail, 0), dataList);
			}
		}
		Company com = companyService.getCompanyByUserId(userId);
		User user = userService.getUserById(userId);

		// 6.根据用户ID获取用户邮箱
		String email = userService.getEmailBySessionUserId(userId);
		// 7.根据软件id获取软件名称
		Software soft = softwareService.getSoftware(Integer
				.parseInt(softwareId));
		String newPath = PropertiesUtil.toolsOutPath
				+ "Procedure!runApp?userId=" + userId + "&appId=" + softwareId
				+ "&appName=" + soft.getSoftwareName() + "&projectName="
				+ projectName + "&email=" + email + "&dataKeyList="
				+ dataResult.toString() + "&projectId=" + proId + "&dataInfos="
				+ JSONObject.toJSONString(map) + "&company="
				+ JSONObject.toJSONString(com) + "&user="
				+ JSONObject.toJSONString(user);
		RemoteRequests rr = new RemoteRequests();
		rr.run(newPath);
		error = 0;
		return "RunProject";
	}

	public String updateReportReadStateByPro() {
		result = reportService.updateReportReadStateByPro(
				Integer.parseInt(projectId), Integer.parseInt(softwareId));
		return SUCCESS;
	}

	/**
	 * 获取某个项目的项目名、物种、数据类型信息
	 * 
	 * @return
	 */
	public String getProjectById() {
		project = projectService.getProjectById(project.getProjectId());
		return SUCCESS;
	}

	/**
	 * 获取所有项目列表
	 * 
	 * @return
	 */
	public String getAllProMapListByUserId() {
		userId = (Integer) super.session.get("userId");
		proList = projectService.getAllProNameList(userId);
		proMapList = new ArrayList<Map<String, String>>();
		// 添加数据的所有物种类型
		for (Project pro : proList) {
			Map<String, String> one = new HashMap<String, String>();
			one.put("id", pro.getProjectId() + "");
			one.put("text", pro.getProjectName());
			proMapList.add(one);
		}
		return SUCCESS;
	}

	/**
	 * 检查项目下某个app的参数
	 * 
	 * @return
	 */
	public String checkProAppParams() {
		String projectFileDir = ServletActionContext.getServletContext()
				.getRealPath("/pages/proParams/");
		proParams = XmlUtil.getProjectParams(Integer.parseInt(projectId),
				projectFileDir);
		return SUCCESS;
	}

	/**
	 * 项目参数保存,需要的参数信息projectId，strain,adaptor3,adaptor5,sampleList,diffList
	 * 
	 * @return
	 */
	public String saveProParams() {
		String projectFileDir = ServletActionContext.getServletContext()
				.getRealPath("/pages/proParams/");
		result = XmlUtil.createDocument(proParam, projectFileDir);
		return SUCCESS;
	}

	/**
	 * 项目删除
	 * 
	 * @return
	 */
	public String deleteProject() {
		result = projectService.deleteProject(Integer.parseInt(projectId));
		return "deleteProject";
	}

	/**
	 * 修改项目文件格式为初始状态0
	 * 
	 * @return
	 */
	public String updateProDataFormat() {
		result = projectService.updateDataFormatById(project.getProjectId(),
				project.getDataFormat());
		return "updateProDataFormat";
	}

	/**
	 * 获取项目下文件个数
	 * 
	 * @return
	 */
	public String getDataSizeInProject() {
		dataSize = dataService.getDataListByProjectId(project.getProjectId())
				.size();
		return "getDataSizeInProject";
	}

	/**
	 * 检查项目名是否重复
	 * 
	 * @return
	 */
	public String checkProjectName() {
		userId = (Integer) super.session.get("userId");
		int proId = projectService.checkProjectName(project.getProjectId(),
				project.getProjectName(), userId);
		flag = (proId != 0);
		return "checkProjectName";
	}

	/**
	 * 根据项目名获取项目编号
	 * 
	 * @return
	 */
	public String getProjectIdByName() {
		projectId = projectService.getProjectIdByName(projectName) + "";
		return SUCCESS;
	}

	/**
	 * 新建SNP项目
	 * 
	 * @return
	 */
	private String softwareName;

	public String createSnpTreeProject() {
		userId = (Integer) super.session.get("userId");
		projectName = new Date().getTime() + "";
		proList = projectService.getAllProNameList(userId);
		while (existsProName(projectName)) {
			projectName = new Date().getTime() + "";
		}
		project = new Project();
		project.setUserId(userId);
		project.setProjectName(projectName);
		// 根据softwareName获取对应的数据类型
		int dataFormat = dataService.getDataById(dataId + "").getFileFormat();
		project.setDataFormat(dataFormat);
		flag = projectService.insertProject(project);
		int result = flag ? 1 : 0;
		projectName = result + "," + projectName;
		return SUCCESS;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * 判断项目名是否重复
	 * 
	 * @param proName
	 * @return
	 */
	public boolean existsProName(String proName) {
		for (Project pro : proList) {
			if (pro.getProjectName().equals(projectName)) {
				projectName = new Date().getTime() + "";
				return true;
			}
		}
		return false;
	}

	public String getUserIdByProjectId() {
		shareUserId = projectService.getUserIdByProjectId(Integer
				.parseInt(projectId));
		return SUCCESS;
	}

	public String getAllProStrainList() {
		userId = (Integer) super.session.get("userId");
		List<Map<String, String>> proStrainList = projectService
				.getProStrainItem(userId);
		List<Map<String, String>> dataStrainList = dataService
				.getStrainItem(userId);
		strainMapList = new ArrayList<Map<String, String>>();
		proStrainList.addAll(dataStrainList);
		HashSet<Map<String, String>> h = new HashSet<Map<String, String>>(
				proStrainList);
		proStrainList.clear();
		proStrainList.addAll(h);
		// 添加数据的所有物种类型
		for (Map<String, String> map : proStrainList) {
			if (!"".equals(map.get("strain"))
					&& map.get("strain") != null) {
				Map<String, String> one = new HashMap<String, String>();
				one.put("id", map.get("strain"));
				one.put("text", map.get("strain"));
				strainMapList.add(one);
			}
		}
		return "strainMapList";
	}

	public String getAllDataTypeList() {
		dataTypeMap = new HashMap<String, List<Map<String, String>>>();
		List<DataType> typeList = dataTypeService.getDataTypeList();
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		for (int i = 0; i < typeList.size(); i++) {
			Map<String, String> one = new HashMap<String, String>();
			one.put("id", String.valueOf(typeList.get(i).getTypeId()));
			one.put("text", typeList.get(i).getTypeDesc());
			listMap.add(one);
		}
		dataTypeMap.put("tags", listMap);
		return "dataTypeList";
	}

	public String removeData() {
		flag = projectService.removeData(projectId, dataId);
		return SUCCESS;
	}

	public String addDataForPro() {
		result = projectService.addDataToPro(project.getProjectId(), dataId);
		// int fileType = dataService.getFileTypeById(dataId);
		// projectService.addProDataType(project.getProjectId(),
		// String.valueOf(fileType));
		return "addDataForPro";
	}

	/**
	 * 数据分配项目，获取项目列表
	 * 
	 * @return
	 */
	public String getProListForSel() {
		log.info("查询项目名称列表");
		getUserId();
		proNameList = projectService.getAllProNameList(userId);
		dataTypeMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		for (Project pro : proNameList) {
			Map<String, String> proMap = new HashMap<String, String>();
			proMap.put("id", pro.getProjectId() + "");
			proMap.put("text", pro.getProjectName());
			listMap.add(proMap);
		}
		dataTypeMap.put("tags", listMap);
		return SUCCESS;
	}

	public String getProListForUpload() {
		proNameList = projectService.getAllProNameList(userid);
		return "getProListForUpload";
	}

	private int fileId;

	public String getProListByFileId() {
		proNameList = projectService.getAllProNameListByFileId(fileId);
		dataTypeMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		for (Project pro : proNameList) {
			Map<String, String> proMap = new HashMap<String, String>();
			proMap.put("id", pro.getProjectId() + "");
			proMap.put("text", pro.getProjectName());
			listMap.add(proMap);
		}
		dataTypeMap.put("tags", listMap);
		return SUCCESS;
	}

	public String getProListForData() {
		getUserId();
		proNameList = projectService.getProListForData(userId,
				data.getFileFormat());
		dataTypeMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		for (Project pro : proNameList) {
			Map<String, String> proMap = new HashMap<String, String>();
			proMap.put("id", pro.getProjectId() + "");
			proMap.put("text", pro.getProjectName());
			listMap.add(proMap);
		}
		dataTypeMap.put("tags", listMap);
		return "getProListForData";
	}

	public String getAllDataInPro() {
		proDataList = projectService
				.getAllFileInProject(project.getProjectId());
		return "getDataInProSucc";
	}

	public String getDataSizeInPro() {
		proDataList = projectService
				.getAllFileInProject(project.getProjectId());
		dataSize = proDataList.size();
		return SUCCESS;
	}

	public String getStrainByProId() {
		log.info("获取指定项目的物种信息");
		strain = projectService.getStrainByProId(project.getProjectId());
		return "getStrainByProId";
	}

	private String proManageInfo; // 添加项目的提示
	private String userIds = "";

	public String addProject() {
		getUserId();
		String proName = project.getProjectName();
		project.setUserId(userId);
		List<Project> proNameList = projectService.getAllProNameList(userId);
		flag = true;
		for (int i = 0; i < proNameList.size(); i++) {
			if (proName.equals(proNameList.get(i).getProjectName())) {
				flag = false;
				proManageInfo = "项目名称已存在，建议使用" + proName + "_1";
			}
		}
		if (flag == true) {
			flag = projectService.insertProject(project);
			if (flag == true) {
				proManageInfo = "succ";
			}
		}
		return SUCCESS;
	}

	/**
	 * 取消项目共享
	 * 
	 * @return
	 */
	public String cancelProjectShare() {
		flag = projectService.deleteSharedPro(userId, project.getProjectId());
		return "returnBoolean";
	}

	public String saveProjectShare() {
		errorMessage = "";
		getUserId();
		if ((null != userNames) && (!userNames.equals(""))) {
			String username[] = userNames.split(",");
			for (String uname : username) {
				int userid = userService.getUserIdByName(uname);
				if (userid == 0) {
					errorMessage += uname + ",";
				} else {
					userIds += "" + userid + ",";
				}
			}
		}
		if (!errorMessage.equals("")) {
			errorMessage = "用户"
					+ errorMessage.substring(0, errorMessage.length() - 1)
					+ "不存在！";
			return "saveProjectShare";
		}
		if (!"".equals(userIds)) {
			projectService.deleteSharedPro(userId, project.getProjectId());
			errorMessage = projectService.insertShareProject(
					userIds.substring(0, userIds.length() - 1),
					project.getProjectId(), userId)
					+ "";
			Map<String, Integer> map = projectService.getAllErrorSharedPro(
					userId, project.getProjectId());
			if (map != null) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					projectService.deleteSharedProById(entry.getValue());
				}
			}
		} else {
			projectService.deleteSharedPro(userId, project.getProjectId());
			Map<String, Integer> map = projectService.getAllErrorSharedPro(
					userId, project.getProjectId());
			if (map != null) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					projectService.deleteSharedProById(entry.getValue());
				}
			}
			errorMessage = "2";
		}
		return "saveProjectShare";
	}

	public String sendProShareEmail() {
		if (!userNames.equals("")) {
			result = 1;
			for (String uName : userNames.split(",")) {
				String emailTemplate = TemplateUtil.readTemplate(0);
				String sendMsg = emailTemplate
						.replaceAll("#shareType", "项目")
						.replaceAll("#userName", uName)
						.replaceAll("#shareUserName",
								(String) super.session.get("userName"))
						.replaceAll(
								"#dataName",
								projectService.getProjectNameById(project
										.getProjectId()))
						.replaceAll("#dataKey", project.getProjectId() + "");
				String email = userService.getEmailByUserName(uName);
				// EmailUtil.sendMail(email,
				// "项目共享编号为：" + project.getProjectId());
				// EmailUtil.sendMail(email,sendMsg,true);
				EmailService.send(email, sendMsg, true);
			}
		}
		return "sendProShareEmail";
	}

	public String addProStrain() {
		String strain = project.getStrain();
		int proectid = project.getProjectId();
		flag = projectService.addProStrain(proectid, strain);
		return "addProStrain";
	}

	public String addProDataType() {
		String dataType = String.valueOf(project.getProDataType());
		flag = projectService.addProDataType(project.getProjectId(), dataType);
		return "addProDataType";
	}

	public String getProjectNameById() {
		projectName = projectService.getProjectNameById(project.getProjectId());
		return SUCCESS;
	}

	public String updateProjectNameById() {
		log.info("根据项目编号修改项目名称");
		flag = projectService.updateProName(project.getProjectId(),
				project.getProjectName());
		return "updateProjectNameById";
	}

	public String getDataTypeById() {
		log.info("根据项目编号获取数据类型");
		String dataType = projectService
				.getDataTypeById(project.getProjectId());
		project.setProDataType(Integer.parseInt(dataType));
		return "getDataTypeById";
	}

	public int getUserId() {
		userId = (Integer) super.session.get("userId");
		return userId;
	}

	public PageList<Project> getShareProPageList() {
		return shareProPageList;
	}

	public void setShareProPageList(PageList<Project> shareProPageList) {
		this.shareProPageList = shareProPageList;
	}

	public PageList<Project> getMyProNamePageList() {
		return myProNamePageList;
	}

	public void setMyProNamePageList(PageList<Project> myProNamePageList) {
		this.myProNamePageList = myProNamePageList;
	}

	public PageList<Project> getSharedProNamePageList() {
		return sharedProNamePageList;
	}

	public void setSharedProNamePageList(PageList<Project> sharedProNamePageList) {
		this.sharedProNamePageList = sharedProNamePageList;
	}

	public List<Data> getProDataList() {
		return proDataList;
	}

	public void setProDataList(List<Data> proDataList) {
		this.proDataList = proDataList;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProNameList() {
		return proNameList;
	}

	public void setProNameList(List<Project> proNameList) {
		this.proNameList = proNameList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getProManageInfo() {
		return proManageInfo;
	}

	public void setProManageInfo(String proManageInfo) {
		this.proManageInfo = proManageInfo;
	}

	public PageList<Project> getPrivaProPageList() {
		return privaProPageList;
	}

	public void setPrivaProPageList(PageList<Project> privaProPageList) {
		this.privaProPageList = privaProPageList;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getStrain() {
		return strain;
	}

	public void setStrain(String strain) {
		this.strain = strain;
	}

	public int getSortByType() {
		return sortByType;
	}

	public void setSortByType(int sortByType) {
		this.sortByType = sortByType;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Map<String, List<Map<String, String>>> getDataTypeMap() {
		return dataTypeMap;
	}

	public void setDataTypeMap(
			Map<String, List<Map<String, String>>> dataTypeMap) {
		this.dataTypeMap = dataTypeMap;
	}

	public List<Map<String, String>> getStrainMapList() {
		return strainMapList;
	}

	public void setStrainMapList(List<Map<String, String>> strainMapList) {
		this.strainMapList = strainMapList;
	}

	public int getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(int shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}

	public List<Project> getProList() {
		return proList;
	}

	public void setProList(List<Project> proList) {
		this.proList = proList;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public ProjectParam getProParam() {
		return proParam;
	}

	public void setProParam(ProjectParam proParam) {
		this.proParam = proParam;
	}

	public Map<String, Object> getProParams() {
		return proParams;
	}

	public void setProParams(Map<String, Object> proParams) {
		this.proParams = proParams;
	}

	public List<Map<String, String>> getProMapList() {
		return proMapList;
	}

	public void setProMapList(List<Map<String, String>> proMapList) {
		this.proMapList = proMapList;
	}

	public String getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

}
