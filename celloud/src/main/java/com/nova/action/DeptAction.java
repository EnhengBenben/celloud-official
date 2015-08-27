package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Dept;
import com.nova.service.IDeptService;

@ParentPackage("celloud-default")
@Action("dept")
@Results({
	@Result(name = "byComId", type = "json", params = { "root", "list" }),
	@Result(name = "success", type = "json", params = { "root", "flag" }),
	@Result(name = "getOne", type = "json", params = { "root", "dept" }) })
public class DeptAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private IDeptService ids;
    private List<Dept> list;
    private Integer companyId;
    private Integer deptId;
    private Dept dept;
    private Integer flag;

    /**
     * 修改部门状态
     * 
     * @return
     */
    public String update() {
	flag = ids.updateDept(dept);
	return SUCCESS;
    }

    /**
     * 修改部门状态
     * 
     * @return
     */
    public String updateState() {
	flag = ids.updateState(dept);
	return SUCCESS;
    }

    /**
     * 获取公司下的部门
     * 
     * @return
     */
    public String getByComId() {
	list = ids.getDeptAll(companyId);
	return "byComId";
    }

    /**
     * 单查
     * 
     * @return
     */
    public String getOne() {
	dept = ids.getDept(deptId);
	return "getOne";
    }

    /**
     * 增加
     * 
     * @return
     */
    public String add() {
	flag = ids.add(dept);
	return SUCCESS;
    }

    public List<Dept> getList() {
	return list;
    }

    public void setList(List<Dept> list) {
	this.list = list;
    }

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

    public Integer getDeptId() {
	return deptId;
    }

    public void setDeptId(Integer deptId) {
	this.deptId = deptId;
    }

    public Dept getDept() {
	return dept;
    }

    public void setDept(Dept dept) {
	this.dept = dept;
    }

    public Integer getFlag() {
	return flag;
    }

    public void setFlag(Integer flag) {
	this.flag = flag;
    }
}