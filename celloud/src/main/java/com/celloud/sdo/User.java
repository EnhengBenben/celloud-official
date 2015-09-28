package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据模型
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28下午1:29:05
 * @version Revision: 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String cellPhone;
    private Date createDate;
    /** 真实姓名 */
    private String truename;
    /** 角色：0:生物信息公司 1:医院 2:超级管理员 3:CDC用户 */
    private Integer role;
    /** 备注 */
    private String remark;
    private Integer navigation;// 用户导航
    /** 是否删除：0-未删除，1 已删除 */
    private Integer state = 0;
    /** 部门编号 */
    private Integer deptId;
    /** 提供app的公司id */
    private Integer companyId;

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getCellPhone() {
	return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
	this.cellPhone = cellPhone;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public String getTruename() {
	return truename;
    }

    public void setTruename(String truename) {
	this.truename = truename;
    }

    public Integer getRole() {
	return role;
    }

    public void setRole(Integer role) {
	this.role = role;
    }

    public String getRemark() {
	return remark;
    }

    public void setRemark(String remark) {
	this.remark = remark;
    }

    public Integer getNavigation() {
	return navigation;
    }

    public void setNavigation(Integer navigation) {
	this.navigation = navigation;
    }

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    public Integer getDeptId() {
	return deptId;
    }

    public void setDeptId(Integer deptId) {
	this.deptId = deptId;
    }

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

}
