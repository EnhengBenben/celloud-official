package com.celloud.manager.model;

import java.util.Date;

public class Week {
    private Integer id;

    private Date startDate;

    private Date endDate;

    private Integer loginCount;

    private Integer activeUser;

    private Integer appRun;

    private Integer activeApp;

    private String dataSize;

    private String colonyUsed;

    private Integer fileCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Integer activeUser) {
        this.activeUser = activeUser;
    }

    public Integer getAppRun() {
        return appRun;
    }

    public void setAppRun(Integer appRun) {
        this.appRun = appRun;
    }

    public Integer getActiveApp() {
        return activeApp;
    }

    public void setActiveApp(Integer activeApp) {
        this.activeApp = activeApp;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize == null ? null : dataSize.trim();
    }

    public String getColonyUsed() {
        return colonyUsed;
    }

    public void setColonyUsed(String colonyUsed) {
        this.colonyUsed = colonyUsed == null ? null : colonyUsed.trim();
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

}