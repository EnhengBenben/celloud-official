package com.celloud.model.mysql;

import java.util.Date;

public class Sample {
    private Integer sampleId;

    private Integer userId;

    private Integer orderId;

    private String sampleName;

    private String experSampleName;

    private Boolean isAdd;

    private String type;

    private String sindex;

    private Date createDate;

    private Date updateDate;

    private Boolean state;

    private String remark;

    // 显示字段：
    private Integer tagId;
    /** 产品标签 */
    private String tagName;
    private Integer sampleLogId;
    /** 实验过程更新时间 */
    private Date logDate;
    /** 订单编号 */
    private String orderNo;
    /** 实验状态 */
    private Integer experState;

    /**
     * 病人信息外键
     */
    private Integer patientId;

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getExperSampleName() {
        return experSampleName;
    }

    public void setExperSampleName(String experSampleName) {
        this.experSampleName = experSampleName == null ? null
                : experSampleName.trim();
    }
    public Boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSindex() {
        return sindex;
    }

    public void setSindex(String sindex) {
        this.sindex = sindex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getSampleLogId() {
        return sampleLogId;
    }

    public void setSampleLogId(Integer sampleLogId) {
        this.sampleLogId = sampleLogId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getExperState() {
        return experState;
    }

    public void setExperState(Integer experState) {
        this.experState = experState;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

}