package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer report_id;
	/** 文件编号 */
	private Integer file_id;
	/** 项目编号 */
	private Integer project_id;
	/** 软件编号 */
	private Integer app_id;
	/** 软件名称 */
	private String app_name;
	/** 标志位 0:数据报告；1项目报告 */
	private Integer flag;
	/** 用户编号 */
	private Integer user_id;
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

	public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}

	public Integer getFile_id() {
		return file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public Integer getApp_id() {
		return app_id;
	}

	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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
