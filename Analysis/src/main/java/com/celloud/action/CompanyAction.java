package com.celloud.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.sdo.Company;
import com.celloud.service.CompanyService;
import com.google.inject.Inject;

@ParentPackage("json-default")
@Action("company")
@Results({
		@Result(name = "success", location = "../../pages/HospitalActivity.jsp"),
		@Result(name = "resultMap", type = "json", params = { "root",
				"resultMap" }),
		@Result(name = "companyDetail", location = "../../pages/hospitalList.jsp") })
public class CompanyAction extends BaseAction {
	Logger log = Logger.getLogger(CompanyAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private CompanyService companyService;
	private Map<String, Object> resultMap;
	private List<Company> complist;

	public String toActivity() {
		return "success";
	}

	public String getCompanyNumEveryMonth() {
		Integer companyId = (Integer) getCid();
		resultMap = companyService.getCompanyNumEveryMonth(companyId);
		return "resultMap";
	}

	public String getCompanyDetail() {
		Integer cid = (Integer) getCid();
		complist = companyService.getCompanyDetailById(cid);
		return "companyDetail";
	}

	private Object getCid() {
		Object cid = super.session.get("companyId");
		log.info("获取companyId:" + cid);
		if (cid == null) {
			log.error("后台session超时或者非法访问");
		}
		return cid;
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

}
