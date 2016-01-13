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

import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
import com.celloud.service.CompanyService;
import com.celloud.utils.DateUtil;
import com.celloud.utils.LogUtil;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("company")
@Results({ @Result(name = "success", location = "../../pages/form.jsp"),
		@Result(name = "companyDetail", location = "../../pages/hospitalList.jsp"),
		@Result(name = "oneCompany", location = "../../pages/hospitalOne.jsp"),
		@Result(name = "content", location = "../../pages/activity.jsp"),

		@Result(name = "list", type = "json", params = { "root", "list" }),
		@Result(name = "resultMap", type = "json", params = { "root", "resultMap" }),
		@Result(name = "companyDetailJson", type = "json", params = { "root", "complist" }),

		/***** 活跃度统计－－－ 医院活跃度统计 登陆、上传文件、app运行次数 ******/
		@Result(name = "LogList", type = "json", params = { "root", "logList" }),
		@Result(name = "DataList", type = "json", params = { "root", "dataList" }),
		@Result(name = "SoftWareList", type = "json", params = { "root", "runList" }),

})

public class CompanyAction extends BaseAction {
	Logger log = Logger.getLogger(CompanyAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private CompanyService companyService;
	private Map<String, Object> resultMap;
	private List<Map<String, Object>> list;
	private List<Company> complist;
	private Company company;
	private List<App> runList;
	private List<DataFile> dataList;
	private List<LoginLog> logList;
	private User user;
	private Date startDate;
	private Date endDate;
	private int topN;
	private List<Company> companyList;
	private List<Integer> companyIds; // id1,id2
	private String orderby; // 1.文件数量;2.文件大小
	private List<DataFile> userDataList;

	@SuppressWarnings("unchecked")
	public String getContent() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		try {
			@SuppressWarnings("rawtypes")
			Map<String, List> result = companyService.getList(role, cmpId, startDate, endDate, topN);
			runList = (List<App>) result.get("appRun");
			userDataList = (List<DataFile>) result.get("uFile");
			dataList = (List<DataFile>) result.get("hFile");
			log.info(result);
			log.info(userDataList);
			log.info(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "content";
	}

	/**
	 * 查询医院时间内的排序，取前N条记录
	 * 
	 * @return JSON
	 */
	public String getActivityList() {
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		Integer cmpId = (Integer) getCid();
		try {
			if (startDate == null)
				startDate = DateUtil.DAY_START_OF_MONTH();
			if (endDate == null)
				endDate = new Date();
			LogUtil.info(log, topN);
			resultMap = companyService.getCompanyFile(role, cmpId, startDate, endDate, topN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultMap";
	}

	/**
	 * 大客户下客户app运行次数统计
	 * 
	 * @return
	 */
	public String getHospitaAppRunNum() {
		runList = companyService.getCompanyRunAppNumByCId(company.getCompany_id());
		return "SoftWareList";
	}

	/**
	 * 取App运行次数按月统计
	 * 
	 * @return JSON [{'2012-12':55}]' object is Software
	 */
	public String getHospitaAppRunGroupByMonth() {
		runList = companyService.getCompanyRunAppNumGroupByMonth(company.getCompany_id());
		return "SoftWareList";
	}

	/**
	 * 取App运行次数按月统计
	 * 
	 * @return JSON [{'2012-12':55}]' object is Software
	 */
	public String getHospitaAppRunGroupByWeek() {
		runList = companyService.getCompanyRunAppNumGroupByMonth(company.getCompany_id());
		return "SoftWareList";
	}

	/**
	 * 本月大客户下每个医院上传文件个数、文件大小按月统计
	 * 
	 * @return JSON
	 */
	public String getHospitaMonthUpload() {
		dataList = companyService.getCompanyUpLoadGroupMonthByCId(company.getCompany_id());
		return "DataList";
	}

	/**
	 * 大客户下每个医院上传的文件个数、文件大小按周统计
	 * 
	 * @return JSON
	 */
	public String getHospitsWeekUpload() {
		dataList = companyService.getCompanyUpLoadGroupWeekByCId(company.getCompany_id());
		return "DataList";
	}

	public String toActivity() {
		return "success";
	}

	public String getProvince() {
		Integer companyId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		list = companyService.getProvince(companyId, role);
		return "list";
	}

	/**
	 * 统计每个月医院的
	 * 
	 * @return
	 */
	public String getCompanyNumEveryMonth() {
		Integer companyId = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		resultMap = companyService.getCompanyNumEveryMonth(companyId, role);
		return "resultMap";
	}

	/**
	 * 医院统计－－医院详细信息 获取医院数据个数、大小、报告个数
	 * 
	 * @return
	 */
	public String getCompanyDetail() {
		Integer cid = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		complist = companyService.getCompanyDetailById(cid, role, orderby);
		return "companyDetail";
	}

	/**
	 * 获取医院数据个数、大小、报告个数
	 * 
	 * @return json 格式数据
	 */
	public String getCompanyDetailJson() {
		Integer cid = (Integer) getCid();
		Integer role = (Integer) super.session.get(User.USER_ROLE);
		complist = companyService.getCompanyDetailById(cid, role, orderby);
		return "companyDetailJson";
	}

	/**
	 * 医院统计－－医院详细－－单个医院信息
	 * 
	 * @return
	 */
	public String getOneCompany() {
		company = companyService.getCompanyById(company.getCompany_id());
		return "oneCompany";
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public List<Company> getComplist() {
		return complist;
	}

	public void setComplist(List<Company> complist) {
		this.complist = complist;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public List<DataFile> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataFile> dataList) {
		this.dataList = dataList;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public List<App> getRunList() {
		return runList;
	}

	public void setRunList(List<App> runList) {
		this.runList = runList;
	}

	public List<LoginLog> getLogList() {
		return logList;
	}

	public void setLogList(List<LoginLog> logList) {
		this.logList = logList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public Date getStartDate() {
		return startDate;
	}

	public List<Integer> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Integer> companyIds) {
		this.companyIds = companyIds;
	}

	public void setStartDate(String startDate) {
		log.info(startDate);
		SimpleDateFormat sdf = null;
		if (startDate != null && startDate.length() == 7)// yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
		else if (startDate != null & startDate.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.startDate = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<DataFile> getUserDataList() {
		return userDataList;
	}

	public void setUserDataList(List<DataFile> userDataList) {
		this.userDataList = userDataList;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getTopN() {
		return topN;
	}

	public void setTopN(int topN) {
		this.topN = topN;
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

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

}
