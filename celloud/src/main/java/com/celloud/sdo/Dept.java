package com.celloud.sdo;

import java.io.Serializable;

/**
 * 部门模型
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28上午11:26:22
 * @version Revision: 1.0
 */
public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer deptId;
    private String deptName;
    private String englishName;
    private String deptIcon;
    private String tel;
    private Integer companyId;
    /** 是否删除：0-未删除，1-已删除 */
    private Integer state;

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
