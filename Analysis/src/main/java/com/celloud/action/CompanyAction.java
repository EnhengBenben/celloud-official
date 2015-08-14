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
		@Result(name = "success", location = "../../pages/hospitalActivity.jsp"),
		@Result(name = "companyDetail", location = "../../pages/hospitalList.jsp"),
		@Result(name = "oneCompany", location = "../../pages/hospitalOne.jsp"),
		@Result(name = "resultMap", type = "json", params = { "root",
				"resultMap" }) })
public class CompanyAction extends BaseAction {
	Logger log = Logger.getLogger(CompanyAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private CompanyService companyService;
	private Map<String, Object> resultMap;
	private List<Company> complist;
	private Company company;

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

}
