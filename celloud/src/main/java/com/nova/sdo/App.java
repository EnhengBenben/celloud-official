package com.nova.sdo;

import java.io.Serializable;

public class App implements Serializable {
	private static final long serialVersionUID = 1L;
	// appId对应softwareId
	private String appId;
	// appName对应softwareName
	private String appName;
	// appImg对应picture_name
	private String appImg;
	// iframeSrc对应BS软件的 host
	private String iframeSrc;
	// flag对应software的flag
	private String flag;
	// state对应software的type
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppImg() {
		return appImg;
	}

	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}

	public String getIframeSrc() {
		return iframeSrc;
	}

	public void setIframeSrc(String iframeSrc) {
		this.iframeSrc = iframeSrc;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
