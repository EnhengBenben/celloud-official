package com.celloud.mongo.sdo;

/**
 * PGS报告——用户填写部分
 */
public class PGSFilling extends PatientBasic {

    /**
     * 就诊卡号
     */
    private String medicalNumber;

    /**
     * 编号
     */
    private String number;

    /**
     * 样本类型
     */
    private String sampleType;

    /**
     * 申请日期
     */
    private String applyDate;

    /**
     * 接收日期
     */
    private String receiveDate;

    /**
     * 样本状态
     */
    private String sampleStatus;

    /**
     * 结果解释
     */
    private String description;

    /**
     * 检测人
     */
    private String detection;

    /**
     * 复核人
     */
    private String review;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 日
     */
    private String day;

    public String getMedicalNumber() {
	return medicalNumber;
    }

    public void setMedicalNumber(String medicalNumber) {
	this.medicalNumber = medicalNumber;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getSampleType() {
	return sampleType;
    }

    public void setSampleType(String sampleType) {
	this.sampleType = sampleType;
    }

    public String getApplyDate() {
	return applyDate;
    }

    public void setApplyDate(String applyDate) {
	this.applyDate = applyDate;
    }

    public String getReceiveDate() {
	return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
	this.receiveDate = receiveDate;
    }

    public String getSampleStatus() {
	return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
	this.sampleStatus = sampleStatus;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDetection() {
	return detection;
    }

    public void setDetection(String detection) {
	this.detection = detection;
    }

    public String getReview() {
	return review;
    }

    public void setReview(String review) {
	this.review = review;
    }

    public String getAuditor() {
	return auditor;
    }

    public void setAuditor(String auditor) {
	this.auditor = auditor;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

    public String getMonth() {
	return month;
    }

    public void setMonth(String month) {
	this.month = month;
    }

    public String getDay() {
	return day;
    }

    public void setDay(String day) {
	this.day = day;
    }
}