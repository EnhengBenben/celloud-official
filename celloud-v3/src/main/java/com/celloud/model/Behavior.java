package com.celloud.model;

import java.io.Serializable;
import java.util.Date;

public class Behavior implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;// 用户名
    private Date logDate;// 登录日期
    private String ip;// 登录ip
    private String address;// 登录地点
    private String browserName;// 登录的浏览器
    private String browserVersion;
    private String osName;// 登录使用的操作系统

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
        return this.browserName+"  "+this.browserVersion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }


    @Override
    public String toString() {
        return "{browser:" + getBrowser() + ",os:" + this.osName + ",ip:" + this.ip + ",address:" + this.address + "}";
    }
}
