package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

public class Behavior implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;//用户名
	private Date logDate;//登录日期
	private String ip;//登录ip
	private String address;//登录地点
	private String browser;//登录的浏览器
	private String os;//登录使用的操作系统
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
