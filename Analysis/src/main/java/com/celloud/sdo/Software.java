package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class Software implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer softwareId;
	private String softwareName;
	/** 人气指数 */
	private Integer bhri;
	/** 创建时间 */
	private Date createDate;
	/** 软件应用类型，0：CS软件，1：BS软件，2：数据库系统 */
	private Integer type;
	private String englishName;
	/** 图片名称 */
	private String pictureName;
	/** 介绍 */
	private String intro;
	/** 是否添加 */
	private String isAdd;
	/** 运行次数 */
	private Integer runNum;
	/** 是否支持数据的运行，默认为支持 */
	private Integer runData;
	/** 运行的文件格式 */
	private Integer fileFormat;
	/** 下线标志 */
	private Integer offLine;
	/** 所属公司ID */
	private Integer companyId;
	/** 所属公司名称*/
	private String company_name;
	/** 是否开放 0-所有人可见 1-所属公司旗下的用户可见 默认为0 */
	private Integer attribute;
	/** 最小运行的数据个数 */
	private Integer dataNum;
	/** 可运行的数据类型 */
	private String dataType;
	/** 软件描述 */
	private String description;
	/**保存create_date的字符形式 2015-06*/
	private String yearMonth;
	/**保存周的开始时间：*/
	private String weekDate;
	/**用户名*/
	private String userName;
	/**医院*/
	private String companyName;

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWeekDate() {
		return weekDate;
	}
	
	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public Integer getBhri() {
		return bhri;
	}

	public void setBhri(Integer bhri) {
		this.bhri = bhri;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public Integer getRunNum() {
		return runNum;
	}

	public void setRunNum(Integer runNum) {
		this.runNum = runNum;
	}

	public Integer getRunData() {
		return runData;
	}

	public void setRunData(Integer runData) {
		this.runData = runData;
	}

	public Integer getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(Integer fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Integer getOffLine() {
		return offLine;
	}

	public void setOffLine(Integer offLine) {
		this.offLine = offLine;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getDataNum() {
		return dataNum;
	}

	public void setDataNum(Integer dataNum) {
		this.dataNum = dataNum;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Override
	public String toString() {
		return "Software [softwareId=" + softwareId + ", softwareName=" + softwareName + ", bhri=" + bhri + ", createDate=" + createDate + ", type=" + type
				+ ", englishName=" + englishName + ", pictureName=" + pictureName + ", intro=" + intro + ", isAdd=" + isAdd + ", runNum=" + runNum
				+ ", runData=" + runData + ", fileFormat=" + fileFormat + ", offLine=" + offLine + ", companyId=" + companyId + ", company_name="
				+ company_name + ", attribute=" + attribute + ", dataNum=" + dataNum + ", dataType=" + dataType + ", description=" + description
				+ ", yearMonth=" + yearMonth + "]";
	}
}
