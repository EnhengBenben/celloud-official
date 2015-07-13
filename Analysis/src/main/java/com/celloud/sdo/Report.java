package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer reportId;
	/** 文件编号 */
	private Integer fileId;
	/** 项目编号 */
	private Integer projectId;
	/** 软件编号 */
	private Integer softwareId;
	/** 软件名称 */
	private String softwareName;
	/** 标志位 0:数据报告；1项目报告 */
	private Integer flag;
	/** 用户编号 */
	private Integer userId;
	/** 是否阅读 */
	private Integer readed;
	/** 创建时间 */
	private Date createDate;
	/** 是否共享 */
	private Integer shareState;
	/** 是否删除： 0-未删除， 1-已删除 */
	private Integer isdel;
	/** 结束时间 */
	private Date endDate;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Integer softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReaded() {
		return readed;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getShareState() {
		return shareState;
	}

	public void setShareState(Integer shareState) {
		this.shareState = shareState;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
