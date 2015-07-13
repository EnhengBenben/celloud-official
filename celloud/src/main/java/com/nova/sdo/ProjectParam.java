package com.nova.sdo;

import java.io.Serializable;

/**
 * 项目参数实体类
 * @author zl
 *
 */
public class ProjectParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private int projectId;
	private int softwareId;
	private String strain;
	private String adaptor3;
	private String adaptor5;
	private String sampleList;
	private String diffList;
	private String denovo;
	private int flag;
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public int getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}
	public String getStrain() {
		return strain;
	}
	public void setStrain(String strain) {
		this.strain = strain;
	}
	public String getAdaptor3() {
		return adaptor3;
	}
	public void setAdaptor3(String adaptor3) {
		this.adaptor3 = adaptor3;
	}
	public String getAdaptor5() {
		return adaptor5;
	}
	public void setAdaptor5(String adaptor5) {
		this.adaptor5 = adaptor5;
	}
	public String getSampleList() {
		return sampleList;
	}
	public void setSampleList(String sampleList) {
		this.sampleList = sampleList;
	}
	public String getDiffList() {
		return diffList;
	}
	public void setDiffList(String diffList) {
		this.diffList = diffList;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getDenovo() {
		return denovo;
	}
	public void setDenovo(String denovo) {
		this.denovo = denovo;
	}
	
}
