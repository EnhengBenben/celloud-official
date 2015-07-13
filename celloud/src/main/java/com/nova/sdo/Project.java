package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目服务对象
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-20下午02:36:22
 * @version Revision: 1.0
 */
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 项目编号
	 */
	private int projectId;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 物种
	 */
	private String strain;

	/**
	 * 文件格式
	 */
	private int dataFormat;

	/**
	 * 数据类型
	 */
	private int proDataType;

	private String dataTypeShow;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private String updateDate;
	/**
	 * 状态 删除标志位，默认为0，删除为1
	 */
	private int state;
	/**
	 * 项目所属人
	 */
	private int userId;

	/**
	 * 文件个数
	 */
	private int fileNum;
	/**
	 * 文件大小
	 */
	private String fileSize;

	/**
	 * 所有人
	 */
	private String userName;

	/**
	 * 项目是否被共享
	 */
	private int share;
	
	private int reportNum;

	public String getDataTypeShow() {
		return dataTypeShow;
	}

	public void setDataTypeShow(String dataTypeShow) {
		this.dataTypeShow = dataTypeShow;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStrain() {
		return strain;
	}

	public void setStrain(String strain) {
		this.strain = strain;
	}

	public int getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(int dataFormat) {
		this.dataFormat = dataFormat;
	}

	public int getProDataType() {
		return proDataType;
	}

	public void setProDataType(int proDataType) {
		this.proDataType = proDataType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getReportNum() {
		return reportNum;
	}

	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}
	
}
