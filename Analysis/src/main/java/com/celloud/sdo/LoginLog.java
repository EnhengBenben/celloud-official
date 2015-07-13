package com.celloud.sdo;

import java.io.Serializable;

public class LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 用户名 */
	private String userName;
	/** 登录时间 */
	private Data logDate;
	/** 浏览器 */
	private String browser;
	/** 操作系统 */
	private String os;
	/** IP地址 */
	private String ip;
	/** 每人/每天/每个浏览器的登录次数 */
	private Integer logNum;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Data getLogDate() {
		return logDate;
	}

	public void setLogDate(Data logDate) {
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
