package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告实体
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-24下午2:13:08
 * @version Revision: 1.0
 */
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long reportId;
    private Long fileId;
    private Long projectId;
    private Long softwareId;
    private Integer userId;
    /** 报告运行状态 */
    private Integer state;
    /** 标志位 0:数据报告；1项目报告 */
    private Integer flag;
    private Date createDate;
    private Date endDate;
    /** 是否已读： 0-未读， 1-已读 */
    private Integer readed;
    /** 是否删除： 0-未删除， 1-已删除 */
    private Integer isdel;

    public Long getReportId() {
	return reportId;
    }

    public void setReportId(Long reportId) {
	this.reportId = reportId;
    }

    public Long getFileId() {
	return fileId;
    }

    public void setFileId(Long fileId) {
	this.fileId = fileId;
    }

    public Long getProjectId() {
	return projectId;
    }

    public void setProjectId(Long projectId) {
	this.projectId = projectId;
    }

    public Long getSoftwareId() {
	return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
	this.softwareId = softwareId;
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    public Integer getFlag() {
	return flag;
    }

    public void setFlag(Integer flag) {
	this.flag = flag;
    }
    public Date getCreateDate() {
	return createDate;
    }
    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public Integer getReaded() {
	return readed;
    }

    public void setReaded(Integer readed) {
	this.readed = readed;
    }

    public Integer getIsdel() {
	return isdel;
    }

    public void setIsdel(Integer isdel) {
	this.isdel = isdel;
    }
}
