package com.celloud.model.mysql;

import java.io.Serializable;
import java.util.Date;

public class DataFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer fileId;

	private Integer userId;

	private String dataKey;

	private String fileName;

	private String strain;

	private Long size;

	private Date createDate;

	private Date updateDate;

	private String path;

	private Integer state;

	private Integer fileFormat;

	private String sample;

	private String anotherName;

	private String md5;

	private String dataTags;

	private String batch;

	private String ossPath;

	private Integer uploadState;

    private Integer isRun;

	/** 是否正在运行，0 否，>0 是 */
	private Integer isRunning;
	/** 报告数量 */
	private Integer reportNum;
	/**
	 * appId
	 */
	private Integer appId;
	private Integer tagId;
	/**
	 * tagName
	 */
	private String tagName;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey == null ? null : dataKey.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getStrain() {
		return strain;
	}

	public void setStrain(String strain) {
		this.strain = strain == null ? null : strain.trim();
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(Integer fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample == null ? null : sample.trim();
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName == null ? null : anotherName.trim();
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5 == null ? null : md5.trim();
	}

	public String getDataTags() {
		return dataTags;
	}

	public void setDataTags(String dataTags) {
		this.dataTags = dataTags == null ? null : dataTags.trim();
	}

	public Integer getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Integer isRunning) {
		this.isRunning = isRunning;
	}

	public Integer getReportNum() {
		return reportNum;
	}

	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getOssPath() {
		return ossPath;
	}

	public void setOssPath(String ossPath) {
		this.ossPath = ossPath;
	}

	public Integer getUploadState() {
		return uploadState;
	}

	public void setUploadState(Integer uploadState) {
		this.uploadState = uploadState;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

    public Integer getIsRun() {
        return isRun;
    }

    public void setIsRun(Integer isRun) {
        this.isRun = isRun;
    }

    @Override
    public String toString() {
        return "DataFile [fileId=" + fileId + ", userId=" + userId + ", dataKey=" + dataKey + ", fileName=" + fileName
                + ", strain=" + strain + ", size=" + size + ", createDate=" + createDate + ", updateDate=" + updateDate
                + ", path=" + path + ", state=" + state + ", fileFormat=" + fileFormat + ", sample=" + sample
                + ", anotherName=" + anotherName + ", md5=" + md5 + ", dataTags=" + dataTags + ", batch=" + batch
                + ", ossPath=" + ossPath + ", uploadState=" + uploadState + ", isRunning=" + isRunning + ", reportNum="
                + reportNum + ", appId=" + appId + ", tagId=" + tagId + ", tagName=" + tagName + "]";
    }

}