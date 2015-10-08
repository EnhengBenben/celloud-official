package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目数据模型
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-21上午11:19:03
 * @version Revision: 1.0
 */
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 项目编号 */
    private Long projectId;
    /** 项目名称 */
    private String projectName;
    /** 创建时间 */
    private Date createDate;
    /** 更新时间 */
    private String updateDate;
    /** 状态 删除标志位，默认为0，删除为1 */
    private Integer state;
    /** 项目所属人 */
    private Integer userId;
    /** 文件个数 */
    private Integer fileNum;
    /** 文件大小 */
    private String fileSize;
    /** 所属人姓名 */
    private String userName;
    /** 项目是否被共享 */
    private Integer share;

    public Long getProjectId() {
	return projectId;
    }

    public void setProjectId(Long projectId) {
	this.projectId = projectId;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
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

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getFileNum() {
	return fileNum;
    }

    public void setFileNum(Integer fileNum) {
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

    public Integer getShare() {
	return share;
    }

    public void setShare(Integer share) {
	this.share = share;
    }
}