package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int SYSTEM = 3;			// 系统管理员
	public static final int ADMIN = 2;			// 超级管理员
	public static final int BIG_USER = 1;		// 大客户
	public static final int USER = 0;				// 普通用户
	
	public static final String USER_ROLE = "userRole";

	private Integer user_id;
	private String username;
	private String password;
	/** 邮箱 */
	private String email;
	private Date createDate;
	/** 角色，0，1 大客户，2超级管理员，3管理员 */
	private Integer role;
	/** 真实姓名 */
	private String truename;
	/** 手机号码 */
	private String cellphone;
	/** 0 正常用户，1 已删除 */
	private Integer state = 0;
	/** 所属部门的ID */
	private Integer deptId;
	/** 判断是否登录过客户端 */
	private String loginUuid;
	/** 提供app的公司id */
	private Integer company_id;
	/** 数据量（个数） */
	private Long fileNum;
	/** 验证码 **/
	private String kaptchaCode;
	/** 数据大小（b） */
	private long size;
	/** 报告数量 */
	private Long reportNum;
	/** 所属医院名称 */
	private String company_name;
	/** 所属部门名称 */
	private String dept_name;
	
	private int runNum;

	public int getRunNum() {
		return runNum;
	}

	public void setRunNum(int runNum) {
		this.runNum = runNum;
	}

	public Long getFileNum() {
		return fileNum;
	}

	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
	}

	public void setReportNum(Long reportNum) {
		this.reportNum = reportNum;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Long getReportNum() {
		return reportNum;
	}


	
}