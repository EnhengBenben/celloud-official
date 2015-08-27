package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 类名称：Report   类描述：报告实体类 创建人：zl   创建时间：2013-5-29 下午12:25:22    
 * 
 * @version      
 */
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    // 报告编号
    private int reportId;
    // 文件编号
    private int fileId;
    // 项目编号
    private int projectId;
    // 软件编号
    private int softwareId;
    // 软件名称
    private String softwareName;
    // 软件图标
    private String pictureName;
    // 报告运行状态
    private int state;
    // 标志位 0:数据报告；1项目报告
    private int flag;
    // 用户编号
    private int userId;

    private String dataKey;

    private String projectName;

    private String fileName;

    private int readed;

    private Date createDate;

    private int shareState;
    // 打印时填写相关信息后的打印页面所有信息
    private String printContext;

    /** 是否删除： 0-未删除， 1-已删除 */
    private int isdel;

    private Date endDate;

    public int getReportId() {
	return reportId;
    }

    public void setReportId(int reportId) {
	this.reportId = reportId;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public int getFileId() {
	return fileId;
    }

    public void setFileId(int fileId) {
	this.fileId = fileId;
    }

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

    public String getSoftwareName() {
	return softwareName;
    }

    public void setSoftwareName(String softwareName) {
	this.softwareName = softwareName;
    }

    public String getPictureName() {
	return pictureName;
    }

    public void setPictureName(String pictureName) {
	this.pictureName = pictureName;
    }

    public int getState() {
	return state;
    }

    public void setState(int state) {
	this.state = state;
    }

    public int getFlag() {
	return flag;
    }

    public void setFlag(int flag) {
	this.flag = flag;
    }

    public String getDataKey() {
	return dataKey;
    }

    public void setDataKey(String dataKey) {
	this.dataKey = dataKey;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public int getReaded() {
	return readed;
    }

    public void setReaded(int readed) {
	this.readed = readed;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public int getShareState() {
	return shareState;
    }

    public void setShareState(int shareState) {
	this.shareState = shareState;
    }

    public String getPrintContext() {
	return printContext;
    }

    public void setPrintContext(String printContext) {
	this.printContext = printContext;
    }

    public int getIsdel() {
	return isdel;
    }

    public void setIsdel(int isdel) {
	this.isdel = isdel;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

}
