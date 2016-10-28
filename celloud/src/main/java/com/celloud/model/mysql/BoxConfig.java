package com.celloud.model.mysql;

import java.util.Date;

public class BoxConfig {
	private Integer id;

	private String name;

	private String serialNumber;

	private String location;

	private String intranetAddress;

	private String extranetAddress;

	private Integer port;

	private String context;

	private String version;

	private Date lastAlive;

	private Integer alive;

	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber == null ? null : serialNumber.trim();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location == null ? null : location.trim();
	}

	public String getIntranetAddress() {
		return intranetAddress;
	}

	public void setIntranetAddress(String intranetAddress) {
		this.intranetAddress = intranetAddress == null ? null : intranetAddress.trim();
	}

	public String getExtranetAddress() {
		return extranetAddress;
	}

	public void setExtranetAddress(String extranetAddress) {
		this.extranetAddress = extranetAddress == null ? null : extranetAddress.trim();
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context == null ? null : context.trim();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getLastAlive() {
		return lastAlive;
	}

	public void setLastAlive(Date lastAlive) {
		this.lastAlive = lastAlive;
	}

	public Integer getAlive() {
		return alive;
	}

	public void setAlive(Integer alive) {
		this.alive = alive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}