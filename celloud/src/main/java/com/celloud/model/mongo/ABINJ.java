package com.celloud.model.mongo;

import java.util.Date;

/**
 * ABINJ存入mongodb 的字段
 * 
 * @author lin
 * @date 2016年4月13日 下午4:01:21
 */
public class ABINJ extends Base {
	private static final long serialVersionUID = 1L;

	private String resultPng;
	private Date uploadDate;// 提交时间
	private String fileName;// 文件名
	private Long size;
	private String anotherName;
	private String title;
	private Integer fileId;

	public String getResultPng() {
		return resultPng;
	}

	public void setResultPng(String resultPng) {
		this.resultPng = resultPng;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}
