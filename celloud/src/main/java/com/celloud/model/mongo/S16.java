package com.celloud.model.mongo;

import java.util.Date;

/**
 * 16S存入mongodb 的字段
 * 
 * @author lin
 * @date 2016年4月28日 下午12:09:46
 */
public class S16 extends Base {
	private static final long serialVersionUID = 1L;

	private String resultTable;
	private Date uploadDate;// 提交时间
	private String fileName;// 文件名
	private Long size;
	private String anotherName;
	private String title;
	private Integer fileId;

	public String getResultTable() {
		return resultTable;
	}

	public void setResultTable(String resultTable) {
		this.resultTable = resultTable;
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
