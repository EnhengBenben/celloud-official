package com.celloud.mongo.sdo;

import java.io.Serializable;

public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer deptId;// 自增主键
	private String deptName;// 部门名称
	private String englishName;// 部门英语名称
	private String deptIcon;// 部门logo
	private String tel;// 电话
	private Integer companyId;// 公司编号
	private Integer state;// '是否删除：0-未删除，1-已删除'

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getDeptIcon() {
		return deptIcon;
	}

	public void setDeptIcon(String deptIcon) {
		this.deptIcon = deptIcon;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
