package com.celloud.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.service.DataService;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("data")
@Results({
		@Result(name = "allUserDataNum", location = "../../pages/datalist.jsp"),
		@Result(name = "userMonthData", location = "../../pages/dataUserMonth.jsp"),
		@Result(name = "usersMonthDataList", location = "../../pages/dataMonthList.jsp"),
		@Result(name = "userMonthDetail", location = "../../pages/dataUserMonthDetail.jsp"),
		@Result(name = "userDataInMonth", location = "../../pages/dataAllUserInMonth.jsp"),
		@Result(name = "success", type = "json", params = { "root", "fileName"})
		})
public class DataAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(DataAction.class);
	@Inject
	private DataService dataService;
	private List<Map<String, Object>> list;
	private Integer userId;
	private String month;
	private String fileName;
	private String userIds;//多用户id，如：2,3,4,5
	private String start;//开始时间
	private String end;//结束时间

	/**
	 * 导出数据运行状态
	 * 
	 * @return
	 */
	public String outputData() {
		log.info("导出" + userIds + "从" + start + "到" + end + "的数据运行状态");
		list = dataService.getUserFileRunState(userIds, start, end);
		fileName = new Date().getTime() + ".xls";
		String path = PropertiesUtil.outputPath + fileName;
		FileTools.createFile(path);
		StringBuffer sb = new StringBuffer( "user_id\tusername\tdata_key\tfile_name\tcreate_date\tpath\tsoft\n");
		for (Map<String, Object> data : list) {
			sb.append(data.get("user_id")) .append("\t") .append(data.get("username")) .append("\t")
					.append(data.get("data_key")) .append("\t") .append(data.get("file_name")) .append("\t")
					.append(data.get("create_date").toString().substring(0, 10))
					.append("\t").append(data.get("path")).append("\t") .append(data.get("soft")).append("\n");
		}
		FileTools.appendWrite(path, sb.toString());
		return SUCCESS;
	}
	
	public void download() {
		if (fileName != null) {
			FileTools.fileDownLoad(ServletActionContext.getResponse(), PropertiesUtil.outputPath + fileName);
		}
	}

	public String getAllUsersDataNum() {
		Integer compId = (Integer) getCid();
		log.info("获取大客户" + compId + "所有用户及上传数据总量");
		if (compId != null) {
			list = dataService.getUserList(compId);
		}
		return "allUserDataNum";
	}

	public String getUsersMonthDataList() {
		Integer compId = (Integer) getCid();
		log.info("获取大客户" + compId + "所有用户每月上传数据量");
		if (compId != null) {
			list = dataService.getUserMonthDataList(compId);
		}
		return "usersMonthDataList";
	}

	public String getUserMonthData() {
		Integer compId = (Integer) getCid();
		log.info("获取大客户" + compId + "用户" + userId + "每月上传数据量");
		if (compId != null) {
			list = dataService.getUserMonthData(userId, compId);
		}
		return "userMonthData";
	}

	public String getUserMonthDetail() {
		Integer compId = (Integer) getCid();
		log.info("获取大客户" + compId + "用户" + userId + "在" + month + "上传数据量");
		if (compId != null) {
			list = dataService.getMonthDataList(userId, month, compId);
		}
		return "userMonthDetail";
	}

	public String getUserDataInMonth() {
		Integer compId = (Integer) getCid();
		log.info("获取大客户" + compId + "在" + month + "上传数据量");
		if (compId != null) {
			list = dataService.getAllUserDataInMonth(compId, month);
		}
		return "userDataInMonth";
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
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

}
