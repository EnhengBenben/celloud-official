package com.celloud.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.dao.CompanyDao;
import com.celloud.dao.UserDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.TotalCount;
import com.celloud.sdo.User;
import com.celloud.service.AppService;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.HomeService;
import com.celloud.service.ReportService;
import com.celloud.service.UserService;
import com.celloud.utils.LogUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("home")
@Results({ @Result(name = "success", location = "../../pages/home.jsp"),
		@Result(name = "toCompanyBigUesr", location = "../../pages/companyBigUser.jsp"),
		@Result(name = "toBigUser", location = "../../pages/dataBigUser.jsp"),
		@Result(name = "toBigUserOne", location = "../../pages/dataBigUserOne.jsp"),
		@Result(name = "companyReportList", location = "../../pages/companyReportList.jsp"),
		@Result(name = "companyDataList", location = "../../pages/companyDataList.jsp"),
		@Result(name = "getPreDataView", location = "../../pages/dataPreView.jsp"),
		@Result(name = "getPreCompanyView", location = "../../pages/companyPreView.jsp"),

		@Result(name = "browserCount", type = "json", params = { "root", "browserList" }),
		@Result(name = "historyList", type = "json", params = { "root", "historyList" }),
		@Result(name = "softList", type = "json", params = { "root", "totalSoftList" }),
		@Result(name = "AppList", type = "json", params = { "root", "appList" }),
		@Result(name = "loginList", type = "json", params = { "root", "logList" }),
		@Result(name = "DataList", type = "json", params = { "root", "dataList" }),
		@Result(name = "cmpList", type = "json", params = { "root", "cmpList" }),
		@Result(name = "resultMap", type = "json", params = { "root", "resultMap" }) })
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
	private CompanyDao companyDao;
	@Inject
	private UserDao userDao;
	@Inject
	private ReportService reportService;
	@Inject
	private HomeService homeService;
	@Inject
	private AppService appService;
	private Map<String, Object> resultMap;
	private List<LoginLog> logList;
	private List<DataFile> dataList;
	private List<App> appList;
	private Date endDate;
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
	private List<App> userSoftList;
	/** 前20用户运行A之 */
	private List<App> totalSoftList;
	/** 每天上传数据大小统计 **/
	private List<DataFile> eachDataList;
	private List<Company> cmpList;
	private String orderby; // 1.文件数量;2.文件大小
	private List<Map<String, Object>> mapList;
	private int num;
	private int companyId;

	/**
	 * @return
	 */
	public String toHome() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer companyId = (Integer) getCid();
		try {
			resultMap = homeService.toHome(companyId, role);
		} catch (Exception e) {
			e.printStackTrace();
		} /** 历史比较 */

		return "success";
	}

	public String getPreDataView() {
		Integer compId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		resultMap = dataService.getPreDataView(compId, role);

		return "getPreDataView";
	}

	public String getPreDataViewBigUesrNewCmp() {
		try {
			resultMap = homeService.getPreDataViewBigUesrNewCmp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultMap";
	}

	public String toPreCompanyView() {
		Integer cmpId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		try {
			resultMap = homeService.companyPreView(cmpId, role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getPreCompanyView";
	}

	public String toCompanyDataList() {
		Integer cid = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		cmpList = companyService.getCompanyDetailById(cid, role, null);
		return "companyDataList";
	}

	public String toCompanyReportList() {
		Integer cid = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		try {
			Map<String, Object> map = homeService.toCompanyReport(cid, role);
			appList = (List<App>) map.get("listApps");
			mapList = (List<Map<String, Object>>) map.get("userAppRun");
			cmpList = (List<Company>) map.get("cmpList");
			LogUtil.info(log, " AppList:" + appList.size());
			LogUtil.info(log, "mapList:" + mapList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "companyReportList";
	}

	public String toBigUserCount() {
		try {
			dataList = dataService.getBigUserData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toBigUser";
	}

	public String getBigUserCountJson() {
		dataList = dataService.getBigUserData();
		return "DataList";
	}

	public String toBigUserOne() {
		dataList = dataService.getBigUserDataFile(companyId);
		LogUtil.info(log, dataList.size());
		return "toBigUserOne";
	}

	/**
	 * 用户运行app前20的用户和APP
	 * 
	 * @return
	 */
	public String appRunNum() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer companyId = (Integer) getCid();
		totalSoftList = appService.getAppRunNum(role, companyId);
		return "softList";
	}

	/**
	 * 总的用户运行APP统计
	 * 
	 * @return
	 */
	public String userRunNum() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer companyId = (Integer) getCid();
		appList = appService.getUserRunNum(role, companyId);
		return "AppList";
	}

	public String toCompanyBigUesr() {
		cmpList = companyService.BigUserList();
		return "toCompanyBigUesr";
	}

	public String toCompanyBigUserJson() {
		cmpList = companyService.BigUserList();
		return "cmpList";
	}

	/**
	 * 所有登陆次数排序
	 * 
	 * @return
	 */
	public String loginNum() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer companyId = (Integer) getCid();
		try {
			logList = appService.getTotalUserLogin(role, companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loginList";
	}

	/**
	 * 每周的历史比较
	 * 
	 * @return
	 */
	public String getHistory() {
		// historyList = userService.getCountInHistory();
		return "historyList";
	}

	/**
	 * 总的客户端浏览器使用统计
	 * 
	 * @return
	 */
	public String totalBrowser() {
		browserList = appService.getBrowerCount();
		return "browserCount";
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public List<Company> getCmpList() {
		return cmpList;
	}

	public void setCmpList(List<Company> cmpList) {
		this.cmpList = cmpList;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getOrderby() {
		return orderby;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
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

	public List<DataFile> getEachDataList() {
		return eachDataList;
	}

	public void setEachDataList(List<DataFile> eachDataList) {
		this.eachDataList = eachDataList;
	}

	public List<LoginLog> getLogList() {
		return logList;
	}

	public void setLogList(List<LoginLog> logList) {
		this.logList = logList;
	}

	public List<DataFile> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataFile> dataList) {
		this.dataList = dataList;
	}

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
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

	public List<App> getTotalSoftList() {
		return totalSoftList;
	}

	public void setTotalSoftList(List<App> totalSoftList) {
		this.totalSoftList = totalSoftList;
	}

	public List<LoginLog> getBrowserList() {
		return browserList;
	}

	public void setBrowserList(List<LoginLog> browserList) {
		this.browserList = browserList;
	}

	public List<App> getUserSoftList() {
		return userSoftList;
	}

	public void setUserSoftList(List<App> userSoftList) {
		this.userSoftList = userSoftList;
	}
}
