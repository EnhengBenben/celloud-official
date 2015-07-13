package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Company;
import com.nova.service.ICompanyService;

@ParentPackage("celloud-default")
@Action("company")
@Results({
		@Result(name = "all", type = "json", params = { "root", "list" }),
		@Result(name = "one", location = "../../pages/admin/editCompany.jsp"),
		@Result(name = "success", type = "json", params = { "root", "flag" }),
		@Result(name = "getPageList", location = "../../pages/admin/hospitalList.jsp") })
public class CompanyAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Inject
	private ICompanyService cs;
	private List<Company> list;
	private Company company;
	private PageList<Company> pageList;
	private Page page;
	private int flag;
	private Integer fromId;// 软件提供方的公司id
	private Integer toId;// 软件接收方的公司id

	/**
	 * 修改公司状态
	 * 
	 * @return
	 */
	public String updateState() {
		flag = cs.updateCompany(company.getCompanyId(), company.getState());
		return SUCCESS;
	}

	/**
	 * 单查
	 * 
	 * @return
	 */
	public String getOne() {
		company = cs.getCompany(company.getCompanyId());
		return "one";
	}

	/**
	 * 全查
	 * 
	 * @return
	 */
	public String getAll() {
		list = cs.getAllCompany();
		return "all";
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String addCompany() {
		flag = cs.addCompany(company);
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String updateCompany() {
		flag = cs.updateCompany(company);
		return SUCCESS;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public String getPageListCompany() {
		pageList = cs.getCompanyPage(page);
		list = cs.getAllCompany();
		return "getPageList";
	}

	public List<Company> getList() {
		return list;
	}

	public void setList(List<Company> list) {
		this.list = list;
	}

	public PageList<Company> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Company> pageList) {
		this.pageList = pageList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}
}