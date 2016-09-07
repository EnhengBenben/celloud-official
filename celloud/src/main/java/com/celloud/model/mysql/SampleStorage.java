package com.celloud.model.mysql;

import java.util.Date;

public class SampleStorage {
    private Integer id;

    private Integer userId;

    private String storageName;

    private String sindex;

    private Date createDate;

    private Integer sampleNum;

    private Boolean state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName == null ? null : storageName.trim();
    }

    public String getSindex() {
        return sindex;
    }

    public void setSindex(String sindex) {
        this.sindex = sindex == null ? null : sindex.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(Integer sampleNum) {
        this.sampleNum = sampleNum;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}