package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class DataFile implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	/** 文件编号 */
	private Integer fileId;
	/** 用户编号 提交者 */
	private Integer userId;
	private String userName;
	/** 所属公司ID */
	private Integer companyId;
	/** 所属公司名称 */
	private String company_name;
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
	
	private int fileNum;
	/**字符串年月2015-12*/
	private String yearMonth;
	
	/**周统计时用来保存周一：年月日*/
	private String weekDate;
	
	

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public int getFileNum() {
		return fileNum;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

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

	@Override
	public String toString() {
		return "Data [fileId=" + fileId + ", userId=" + userId + ", userName=" + userName + ", companyId=" + companyId + ", company_name=" + company_name
				+ ", dataKey=" + dataKey + ", fileName=" + fileName + ", size=" + size + ", createDate=" + createDate + ", state=" + state + ", fileFormat="
				+ fileFormat + ", anotherName=" + anotherName + ", fileNum=" + fileNum + ", yearMonth=" + yearMonth + "]";
	}
	 @Override 
     public Object clone() throws CloneNotSupportedException { 
         return super.clone(); 
     } 
}
