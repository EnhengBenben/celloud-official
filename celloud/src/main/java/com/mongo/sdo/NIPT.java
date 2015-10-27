/**  */
package com.mongo.sdo;

import java.util.Date;
import java.util.List;

/**
 * NIPT sdo
 * 
 * @author lin
 */
public class NIPT extends Base {
    private static final long serialVersionUID = 1L;
    private Integer fileId;
    private String readsPercent;
    private String minipng;
    private Long size;
    private String mappingPercent;
    private Date uploadDate;
    private String mappingReads;
    private String totalReads;
    private String readsLength;
    private String fileName;
    private String anotherName;
    private String pdf;
    private List<List<String>> detail;

    public Integer getFileId() {
	return fileId;
    }

    public void setFileId(Integer fileId) {
	this.fileId = fileId;
    }

    public String getReadsPercent() {
	return readsPercent;
    }

    public void setReadsPercent(String readsPercent) {
	this.readsPercent = readsPercent;
    }

    public String getMinipng() {
	return minipng;
    }

    public void setMinipng(String minipng) {
	this.minipng = minipng;
    }

    public Long getSize() {
	return size;
    }

    public void setSize(Long size) {
	this.size = size;
    }

    public String getMappingPercent() {
	return mappingPercent;
    }

    public void setMappingPercent(String mappingPercent) {
	this.mappingPercent = mappingPercent;
    }

    public Date getUploadDate() {
	return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
	this.uploadDate = uploadDate;
    }

    public String getMappingReads() {
	return mappingReads;
    }

    public void setMappingReads(String mappingReads) {
	this.mappingReads = mappingReads;
    }

    public String getTotalReads() {
	return totalReads;
    }

    public void setTotalReads(String totalReads) {
	this.totalReads = totalReads;
    }

    public String getReadsLength() {
	return readsLength;
    }

    public void setReadsLength(String readsLength) {
	this.readsLength = readsLength;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getAnotherName() {
	return anotherName;
    }

    public void setAnotherName(String anotherName) {
	this.anotherName = anotherName;
    }

    public String getPdf() {
	return pdf;
    }

    public void setPdf(String pdf) {
	this.pdf = pdf;
    }

    public List<List<String>> getDetail() {
	return detail;
    }

    public void setDetail(List<List<String>> detail) {
	this.detail = detail;
    }
}