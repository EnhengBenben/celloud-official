/**  */
package com.celloud.mongo.sdo;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

/**
 * 基本数据内容
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-9下午3:40:14
 * @version Revision: 1.0
 */
public class Base {
	@Id
	private ObjectId id;
	// --------------以下均为mysql表中信息-----------------
	/**
	 * 数据编号
	 */
	private String dataKey;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 报告创建时间
	 */
	private Date createDate;
	/**
	 * 软件id
	 */
	private Integer appId;
	/**
	 * 报告id
	 */
	private Integer projectId;
	/**
	 * 软件名称
	 */
	private String appName;
	/**
	 * 公司id
	 */
	private Integer companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司英文名称
	 */
	private String companyEngName;
	/**
	 * 公司logo
	 */
	private String companyIcon;
	/**
	 * 公司地址
	 */
	private String companyAddr;
	/**
	 * 公司英文地址
	 */
	private String companyEnAddr;
	/**
	 * 公司邮编
	 */
	private String zipCode;
	/**
	 * 公司电话
	 */
	private String companyTel;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门英语名称
	 */
	private String deptEngName;
	/**
	 * 部门logo
	 */
	private String deptIcon;
	/**
	 * 部门电话
	 */
	private String deptTel;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEngName() {
		return companyEngName;
	}

	public void setCompanyEngName(String companyEngName) {
		this.companyEngName = companyEngName;
	}

	public String getCompanyIcon() {
		return companyIcon;
	}

	public void setCompanyIcon(String companyIcon) {
		this.companyIcon = companyIcon;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyEnAddr() {
		return companyEnAddr;
	}

	public void setCompanyEnAddr(String companyEnAddr) {
		this.companyEnAddr = companyEnAddr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptEngName() {
		return deptEngName;
	}

	public void setDeptEngName(String deptEngName) {
		this.deptEngName = deptEngName;
	}

	public String getDeptIcon() {
		return deptIcon;
	}

	public void setDeptIcon(String deptIcon) {
		this.deptIcon = deptIcon;
	}

	public String getDeptTel() {
		return deptTel;
	}

	public void setDeptTel(String deptTel) {
		this.deptTel = deptTel;
	}
}
