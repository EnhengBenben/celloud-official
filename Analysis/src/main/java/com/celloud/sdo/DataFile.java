package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class DataFile implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	/** 文件编号 */
	private Integer file_id;
	/** 用户编号 提交者 */
	private Integer user_id;
	private String user_name;
	/** 所属公司ID */
	private Integer company_id;
	/** 所属公司名称 */
	private String company_name;
	/** 数据编号 */
	private String dataKey;
	/** 文件名字 */
	private String file_name;
	/** 大小 单位：b */
	private Long size;
	/** 提交时间 */
	private Date create_date;
	/** 状态 0:未删除，1:已删除 */
	private Integer state;
	/**
	 * 文件格式：1：峰图文件ab1，2：压缩文件，3：fasta文件,4：fastq文件,5:bam格式，6：gz格式
	 */
	private Integer fileFormat;
	/** 文件别名 */
	private String another_name;

	private int fileNum;
	
	private int runNum;
	/** 字符串年月2015-12 */
	private String yearMonth;

	/** 周统计时用来保存周一：年月日 */
	private String weekDate;

	public Integer getFile_id() {
		return file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public int getRunNum() {
		return runNum;
	}

	public void setRunNum(int runNum) {
		this.runNum = runNum;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}


	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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

	public String getAnother_name() {
		return another_name;
	}

	public void setAnother_name(String another_name) {
		this.another_name = another_name;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	@Override
	public String toString() {
		return "DataFile [file_id=" + file_id + ", user_id=" + user_id + ", userName=" + user_name + ", companyId="
				+ company_id + ", company_name=" + company_name + ", dataKey=" + dataKey + ", fileName=" + file_name
				+ ", size=" + size + ", createDate=" + create_date + ", state=" + state + ", fileFormat=" + fileFormat
				+ ", anotherName=" + another_name + ", fileNum=" + fileNum + ", yearMonth=" + yearMonth + ", weekDate="
				+ weekDate + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
