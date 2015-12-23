package com.celloud.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.celloud.sdo.*;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.LoginLogService;
import com.celloud.service.ReportService;
import com.celloud.service.SoftwareService;
import com.celloud.service.UserService;
import com.celloud.utils.DateUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("home")
@Results({ @Result(name = "success", location = "../../pages/home.jsp"),
		@Result(name = "toWeekReport", location = "../../pages/weekExport.jsp"),
		
		@Result(name = "browserCount", type = "json", params = { "root", "browserList" }),
		@Result(name = "userRunNum", type = "json", params = { "root", "userSoftList" }),
		@Result(name = "historyList", type = "json", params = { "root", "historyList" }),
		
//		@Result(name = "userAppTop20", type = "json", params = { "root", "totalSoftList" }),
//		@Result(name = "loginNumQueue", type = "json", params = { "root", "totalLogList" }),
//		@Result(name = "eachDayLoginNum", type = "json", params = { "root", "totalLogList" }),
//		@Result(name = "eachDayAppRunNum", type = "json", params = { "root", "totalSoftList" }),
//		@Result(name = "eachDayAppRunNum", type = "json", params = { "root", "totalSoftList" }),
		@Result(name = "softList", type = "json", params = { "root", "totalSoftList" }),
		
		@Result(name = "loginList", type = "json", params = { "root", "logList" }),
		@Result(name = "eachDayDataSize", type = "json", params = { "root", "eachDataList" }),
		@Result(name = "userFileList", type = "json", params = { "root", "userDataList" }),
		@Result(name = "eachUserRunApp", type = "json", params = { "root", "eachAppList" }),

})
public class HomeAction extends BaseAction {
	Logger log = Logger.getLogger(HomeAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private UserService userService;
	@Inject
	private DataService dataService;
	@Inject
	private CompanyService companyService;
	@Inject
	private ReportService reportService;
	@Inject
	private LoginLogService loginService;
	@Inject
	private SoftwareService softwareService;
	private Map<String, Object> resultMap;
	private List<LoginLog> logList;
	private List<Data> dataList;
	private List<Software> softList;
	private Date startDate;
	private Date endDate;
	private String uids; // userId,..
	private int orderType; // 1文件数量,2数据大小
	private int topN = 10;
	private String groupType; // 按周、月分组、
	/** 历史比较 */
	private List<TotalCount> historyList;
	/** 总登陆次数排序统计 */
	private List<LoginLog> totalLogList;
	/** 各浏览器使用 */
	private List<LoginLog> browserList;
	/** 各用户运行app次数 */
	private List<Software> userSoftList;
	/** 新用户活跃度统计 */
	private List<LoginLog> newUserList;
	/** 前20用户运行A之 */
	private List<Software> totalSoftList;
	/** 每天运行APP的次数 **/
	private List<Software> eachAppList;
	/** 每天上传数据个数、大小统计 **/
	private List<Data> userDataList;
	/** 每天上传数据大小统计 **/
	private List<Data> eachDataList;

	/**
	 * @return
	 */
	public String toHome() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer companyId = (Integer) getCid();
		log.info("role:" + role + "-company_id:" + companyId);

		resultMap = new HashMap<String, Object>();
		Object userNum = userService.getBigUsersUserNum(companyId, role);
		Object dataNum = dataService.getBigUserDataNum(companyId, role);
		Double dataSize = dataService.getBigUserDataSize(companyId, role);
		Object companyNum = companyService.getBigUserCompanyNum(companyId, role);
		Object reportNum = reportService.getBigUserReportNum(companyId, role);
		Object appNum = softwareService.getBigUserAPPNum(companyId, role);

		resultMap.put("userNum", userNum);
		resultMap.put("dataNum", dataNum);
		resultMap.put("dataSize", dataSize / (1024 * 1024 * 1024));
		resultMap.put("companyNum", companyNum);
		resultMap.put("reportNum", reportNum);
		resultMap.put("appNum", appNum);

		/** 历史比较 */
		historyList = userService.getCountInHistory();
		log.info("historyList" + historyList.size());
//		/*** 前20运行app统计 */
//		topN = 20;
//		totalSoftList = softwareService.getTotalAppRunNum(topN);
//		log.info("totalSoftList" + totalSoftList.size());
//		totalLogList = softwareService.getTotalUserLogin(0);
//		log.info("totalLogList" + totalLogList.size());
//		browserList = softwareService.getBrowerCount();
//		log.info("totalSoftList" + totalSoftList.size());
//		userSoftList = softwareService.getTotalAppRunNum(0);
//		log.info("browserList" + browserList.size());
		//
		return "success";
	}

	/** 周统计报表 */
	public String toWeekReport() {
		log.info("toWeekReport");
		try {
			if (startDate == null||1==1) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = sdf.parse("2015-11-02");
				startDate = DateUtil.getLastMonday(d);
				endDate = DateUtil.getLastSunday(d);
			}
			log.info("startDate:" + startDate);
			log.info("endDate:" + endDate);
			/** Top 10统计 */
			dataList = userService.getUserDataTop(groupType, topN, startDate, endDate);
			log.info("Top 10统计dataList" + dataList.size());
			logList = userService.getLoginTop(groupType, topN, startDate, endDate);
			log.info("Top 10统计logList" + logList.size());
			softList = softwareService.getAppRunTop(groupType, topN, startDate, endDate);
			log.info("Top 10统计softList" + logList.size());

			/** 周每天登陆统计 */
			totalLogList = loginService.getUserLoginInWeek(startDate);
			log.info("totalLogList:" + totalLogList.size());
			/** 周每天APP运行次数统计 */
			totalSoftList = softwareService.getAppRunNumCount(startDate);
			log.info("totalSoftList:" + totalSoftList.size());

			/** 统计用户登陆次数 */
			logList = loginService.getUserLoginNum(startDate);
			log.info("logList:" + logList.size());

			/** 用户运行App情况统计 */
			eachAppList = softwareService.getAppUserCount(startDate);
			log.info("eachAppList:" + eachAppList.size());

			/** 客户端使用情况统计 */
			browserList = loginService.getBrowserInWeek(startDate);
			log.info("browserList:" + browserList.size());

			/** 用户上传数据统计 */
			userDataList = dataService.getUserWeekData(startDate);
			/** 每天 */
			eachDataList = dataService.getEachDayData(startDate);
			log.info("userDataList:" + userDataList.size());

			newUserList = userService.getLoginLog("week");
			log.info("newUserList:" + newUserList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "toWeekReport";
	}

	public String weekBrowser() {
		browserList = loginService.getBrowserInWeek(startDate);
		log.info("browserList:" + browserList.size());
		return "browserCount";
	}

	public String eachUserRunApp() {
		eachAppList = softwareService.getAppUserCount(startDate);
		log.info("eachAppList:" + eachAppList.size());
		return "eachUserRunApp";
	}

	public String userFileList() {
		/** 用户上传数据统计 */
		userDataList = dataService.getUserWeekData(startDate);
		return "userFileList";
	}

	/***
	 * 周内每上传数据大小统计
	 * 
	 * @return
	 */
	public String eachDayDataSize() {
		eachDataList = dataService.getEachDayData(startDate);
		return "eachDayDataSize";
	}

	/**
	 * 所有用在周时间内登陆次数排序
	 * 
	 * @return
	 */
	public String allUserLoginNunInWeek() {
		logList = loginService.getUserLoginNum(startDate);
		return "loginList";
	}

	/**
	 * 周每天App的运行次数
	 * 
	 * @return
	 */
	public String eachDayAppRunNum() {
		log.info(startDate);
		totalSoftList = softwareService.getAppRunNumCount(startDate);
		return "softList";
	}

	/**
	 * 周内每登陆次数
	 * 
	 * @return
	 */
	public String eachDayLoginNum() {
		log.info(startDate);
		totalLogList = loginService.getUserLoginInWeek(startDate);
		return "softList";
	}

	/**
	 * 所有登陆次数排序
	 * 
	 * @return
	 */
	public String loginNum() {
		logList = softwareService.getTotalUserLogin(0);
		return "loginList";
	}

	/**
	 * 每周的历史比较
	 * 
	 * @return
	 */
	public String getHistory() {
		historyList = userService.getCountInHistory();
		return "historyList";
	}

	/**
	 * 用户运行app前20的用户和APP
	 * 
	 * @return
	 */
	public String appRunNum() {
		totalSoftList = softwareService.getTotalAppRunNum(0);
		return "softList";
	}

	/**
	 * 总的客户端浏览器使用统计
	 * 
	 * @return
	 */
	public String totalBrowserDistribute() {
		browserList = softwareService.getBrowerCount();
		return "browserCount";
	}

	/**
	 * 新用户活跃度统计
	 * 
	 * @return
	 */
	public String newUserActivity() {
		newUserList = userService.getLoginLog("week");
		return "newUserActivity";
	}

	/**
	 * 总的用户运行APP统计
	 * 
	 * @return
	 */
	public String userRunNum() {
		userSoftList = softwareService.getTotalUserRunNum(0);
		return "userRunNum";
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public void setStartDate(String startDate) {
		SimpleDateFormat sdf = null;
		if (startDate != null && startDate.length() == 7) // yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if (startDate != null & startDate.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			this.startDate = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		SimpleDateFormat sdf = null;
		if (endDate != null && endDate.length() == 7)// yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if (endDate != null & endDate.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.endDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<Data> getEachDataList() {
		return eachDataList;
	}

	public void setEachDataList(List<Data> eachDataList) {
		this.eachDataList = eachDataList;
	}

	public List<Data> getUserDataList() {
		return userDataList;
	}

	public void setUserDataList(List<Data> userDataList) {
		this.userDataList = userDataList;
	}

	public List<LoginLog> getLogList() {
		return logList;
	}

	public void setLogList(List<LoginLog> logList) {
		this.logList = logList;
	}

	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}

	public List<Software> getSoftList() {
		return softList;
	}

	public void setSoftList(List<Software> softList) {
		this.softList = softList;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public List<LoginLog> getTotalLogList() {
		return totalLogList;
	}

	public void setTotalLogList(List<LoginLog> totalLogList) {
		this.totalLogList = totalLogList;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getTopN() {
		return topN;
	}

	public void setTopN(int topN) {
		this.topN = topN;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public List<TotalCount> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<TotalCount> historyList) {
		this.historyList = historyList;
	}

	public List<Software> getTotalSoftList() {
		return totalSoftList;
	}

	public void setTotalSoftList(List<Software> totalSoftList) {
		this.totalSoftList = totalSoftList;
	}

	public List<LoginLog> getBrowserList() {
		return browserList;
	}

	public void setBrowserList(List<LoginLog> browserList) {
		this.browserList = browserList;
	}

	public List<Software> getUserSoftList() {
		return userSoftList;
	}

	public void setUserSoftList(List<Software> userSoftList) {
		this.userSoftList = userSoftList;
	}

	public List<LoginLog> getNewUserList() {
		return newUserList;
	}

	public void setNewUserList(List<LoginLog> newUserList) {
		this.newUserList = newUserList;
	}

	public List<Software> getEachAppList() {
		return eachAppList;
	}

	public void setEachAppList(List<Software> eachAppList) {
		this.eachAppList = eachAppList;
	}

}
