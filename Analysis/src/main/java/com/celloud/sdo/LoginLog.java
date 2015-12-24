package com.celloud.sdo;

import java.io.Serializable;

public class LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 用户名 */
	private String user_name;
	/** 登录时间 */
	private String logDate;
	/** 浏览器 */
	private String browser;
	/** 操作系统 */
	private String os;
	/** IP地址 */
	private String ip;

	private String address;
	/** 每人/每天/每个浏览器的登录次数 */
	private Integer logNum;
	/** 登陆人所属医院 **/
	private String company_name;
	/** 按周统计时，周一 **/
	private String weekDate;
	/** 按月统计时、年月yyyy-MM **/
	private String yearMonth;

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getLogNum() {
		return logNum;
	}

	public void setLogNum(Integer logNum) {
		this.logNum = logNum;
	}
}
