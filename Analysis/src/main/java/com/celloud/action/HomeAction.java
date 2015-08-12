package com.celloud.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.ReportService;
import com.celloud.service.SoftwareService;
import com.celloud.service.UserService;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("home")
@Results({ @Result(name = "success", location = "../../pages/home.jsp") })
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
	private SoftwareService softwareService;
	private Map<String, Object> resultMap;
	
	public String toHome() {
		log.info("进入统计首页");
		Integer companyId = (Integer) getCid();
		resultMap = new HashMap<String, Object>();
		Object userNum = userService.getBigUsersUserNum(companyId);
		Object dataNum = dataService.getBigUserDataNum(companyId);
		Double dataSize = dataService.getBigUserDataSize(companyId);
		Object companyNum = companyService.getBigUserCompanyNum(companyId);
		Object reportNum = reportService.getBigUserReportNum(companyId);
		Object appNum = softwareService.getBigUserAPPNum(companyId);
		resultMap.put("userNum", userNum);
		resultMap.put("dataNum", dataNum);
		resultMap.put("dataSize", dataSize
				/ (1024 * 1024 * 1024));
		resultMap.put("companyNum", companyNum);
		resultMap.put("reportNum", reportNum);
		resultMap.put("appNum", appNum);
		return "success";
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}
