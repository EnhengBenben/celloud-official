package com.celloud.model;

import java.util.Date;

public class ActionLog {
    private Integer userId;
    private String userName;

    private String operate;

    private String message;

    private Date logDate;

    private String browser;

    private String browserVersion;

    private String os;

    private String osVersion;

    private String ip;

    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion == null ? null : browserVersion.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion == null ? null : osVersion.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{ browser:" + getBrowser() + " " + getBrowserVersion() + ",os:" + this.os + " " + getOsVersion()
                + ",ip:" + this.ip + ",address:" + this.address + ",userId:" + this.userId + ",username:"
                + this.userName + ",operate:" + this.operate + ",message:" + this.message + " }";
    }

    public String toResume() {
        return "{userId:" + this.userId + " ,username:" + this.userName + ",browser:" + getBrowser() + " "
                + getBrowserVersion() + ",os:" + this.os + " " + getOsVersion() + ",ip:" + this.ip + ",address:"
                + this.address + "}";
    }
}