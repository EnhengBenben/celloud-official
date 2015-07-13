package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String username;
	private String password;
	/** 邮箱 */
	private String email;
	private Date createDate;
	/** 角色，0，1 普通用户，2超级管理员，3管理员 */
	private Integer role;
	/** 真实姓名 */
	private String truename;
	/** 0 正常用户，1 已删除 */
	private Integer state = 0;
	/** 所属部门的ID */
	private Integer deptId;
	/** 判断是否登录过客户端 */
	private String loginUuid;
	/** 提供app的公司id */
	private Integer company_id;
	/** 数据量（个数） */
	private Integer fileNum;
	/** 验证码 **/
	private String kaptchaCode;

	public Integer getFileNum() {
		return fileNum;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
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
	public String getLoginUuid() {
		return loginUuid;
	}
	public void setLoginUuid(String loginUuid) {
		this.loginUuid = loginUuid;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getKaptchaCode() {
		return kaptchaCode;
	}

	public void setKaptchaCode(String kaptchaCode) {
		this.kaptchaCode = kaptchaCode;
	}

}