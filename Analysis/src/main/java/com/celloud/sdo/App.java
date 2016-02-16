package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;

public class App implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer app_id;
	private String app_name;
	/** 人气指数 */
	private Integer bhri;
	/** 创建时间 */
	private Date create_date;;
	/** 软件应用类型，0：CS软件，1：BS软件，2：数据库系统 */
	private Integer type;
	private String englishName;
	/** 图片名称 */
	private String picture_name;
	/** 介绍 */
	private String intro;
	/** 是否添加 */
	private String isAdd;
	/** 运行次数 */
	private Integer runNum;
	/** 是否支持数据的运行，默认为支持 */
	private Integer run_data;
	/** 运行的文件格式 */
	private Integer fileFormat;
	/** 下线标志 */
	private Integer off_line;
	/** 所属公司ID */
	private Integer company_id;
	/** 所属公司名称*/
	private String company_name;
	/** 是否开放 0-所有人可见 1-所属公司旗下的用户可见 默认为0 */
	private Integer attribute;
	/** 最小运行的数据个数 */
	private Integer data_num;
	/** 可运行的数据类型 */
	private String dataType;
	/** 软件描述 */
	private String description;
	/**保存create_date的字符形式 2015-06*/
	private String yearMonth;
	/**保存周的开始时间：*/
	private String weekDate;
	/**用户名*/
	private String user_name;

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
		return user_name;
	}

	public void setUserName(String userName) {
		this.user_name = userName;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public Integer getBhri() {
		return bhri;
	}

	public void setBhri(Integer bhri) {
		this.bhri = bhri;
	}


	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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


	public Integer getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(Integer fileFormat) {
		this.fileFormat = fileFormat;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getRun_data() {
		return run_data;
	}

	public void setRun_data(Integer run_data) {
		this.run_data = run_data;
	}

	public Integer getOff_line() {
		return off_line;
	}

	public void setOff_line(Integer off_line) {
		this.off_line = off_line;
	}

	public Integer getData_num() {
		return data_num;
	}

	public void setData_num(Integer data_num) {
		this.data_num = data_num;
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



	public String getPicture_name() {
		return picture_name;
	}

	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Override
	public String toString() {
		return "App [app_id=" + app_id + ", app_name=" + app_name + ", bhri=" + bhri + ", createDate=" + create_date
				+ ", type=" + type + ", englishName=" + englishName + ", pictureName=" + picture_name + ", intro="
				+ intro + ", isAdd=" + isAdd + ", runNum=" + runNum + ", run_data=" + run_data + ", fileFormat="
				+ fileFormat + ", off_line=" + off_line + ", company_id=" + company_id + ", company_name="
				+ company_name + ", attribute=" + attribute + ", data_num=" + data_num + ", dataType=" + dataType
				+ ", description=" + description + ", yearMonth=" + yearMonth + ", weekDate=" + weekDate
				+ ", user_name=" + user_name + "]";
	}
}
