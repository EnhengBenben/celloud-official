package com.celloud.backstage.model;

import java.util.Date;

public class Upload {
    private Integer uploadId;

    private String md5;

    private Date createDate;

    private Integer userId;

    private String dataKey;

    private Integer num;

    private Double position;

    public Integer getUploadId() {
        return uploadId;
    }

    public void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey == null ? null : dataKey.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }
}