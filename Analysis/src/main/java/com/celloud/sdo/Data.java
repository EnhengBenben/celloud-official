package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 文件编号 */
	private Integer fileId;
	/** 用户编号 提交者 */
	private Integer userId;
	private String userName;
	/** 数据编号 */
	private String dataKey;
	/** 文件名字 */
	private String fileName;
	/** 大小 单位：b */
	private Long size;
	/** 提交时间 */
	private Date createDate;
	/** 状态 0:未删除，1:已删除 */
	private Integer state;
	/**
	 * 文件格式：1：峰图文件ab1，2：压缩文件，3：fasta文件,4：fastq文件,5:bam格式，6：gz格式
	 */
	private Integer fileFormat;
	/** 文件别名 */
	private String anotherName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}
}
