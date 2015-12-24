package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer company_id;
	private String company_name;
	private String address;
	private String tel;
	/** 用户数量 */
	private Integer userNum;
	/** 数据量（个数） */
	private Long fileNum;
	/** 数据大小(GB) */
	private Double fileSize;
	/** 报告量（个数） */
	private Long reportNum;
	/** 所包含用户 */
	private String username;
	/** 入驻部门 */
	private String dept_name;
	private Date create_date;
	
	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getFileNum() {
		return fileNum;
	}

	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
	}

	public Long getReportNum() {
		return reportNum;
	}

	public void setReportNum(Long reportNum) {
		this.reportNum = reportNum;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", company_name=" + company_name + ", address=" + address
				+ ", tel=" + tel + ", userNum=" + userNum + ", fileNum=" + fileNum + ", fileSize=" + fileSize
				+ ", reportNum=" + reportNum + ", username=" + username + ", dept_name=" + dept_name + ", create_date="
				+ create_date + "]";
	}
}
