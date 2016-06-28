package com.celloud.backstage.model;

import java.math.BigDecimal;
import java.util.Date;

public class Expenses {
    private Integer id;

    private Integer userId;

    private Integer itemId;

    private Byte itemType;

    private BigDecimal amount;

    private BigDecimal balances;

    private Date createDate;

    private String remark;

    // 查询字段
    private String fileInfos;
    private String appName;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Byte getItemType() {
        return itemType;
    }

    public void setItemType(Byte itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getPrice() {
        return amount;
    }

    public void setPrice(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(String fileInfos) {
        this.fileInfos = fileInfos;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public BigDecimal getBalances() {
        return balances;
    }

    public void setBalances(BigDecimal balances) {
        this.balances = balances;
    }
}