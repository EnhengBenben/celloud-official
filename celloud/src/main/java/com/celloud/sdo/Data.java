package com.celloud.sdo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 上传的文件数据模型
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午2:55:27
 * @version Revision: 1.0
 */
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 文件编号 */
    private Integer fileId;
    /** 用户编号 提交者 */
    private Integer userId;
    /** 数据编号 */
    private String dataKey;
    /** 文件名字 */
    private String fileName;
    /** 文件别名 */
    private String anotherName;
    /** 物种 */
    private String strain;
    /** 数据标签 */
    private String dataTags;
    /** 大小 单位：b */
    private Long size;
    /** 提交时间 */
    private Date createDate;
    /** 更新时间 */
    private Date updateDate;
    /** 文件路径 */
    private String path;
    /** 状态 0:未删除，1:已删除 */
    private Integer state;
    /** 文件格式：1：峰图文件ab1，2：压缩文件，3：fasta文件,4：fastq文件,5:bam格式，6：gz格式 */
    private Integer fileFormat;
    /** 样本类型 */
    private String sample;
    /** 文件的md5值 */
    private String md5;
    /** 用于客户端上传 */
    private Integer blocks;
    // 以下字段仅供查询
    /** 报告数量 */
    private int reportNum;
    /** 是否正在运行，0 否，>0 是 */
    private Integer isRunning;
    /** 是否已分配项目 */
    private boolean inProject;
    /** 是否运行完成，0 否，>0 是 */
    private Integer isRunned;
    /** 用户输入过的数据物种信息列表--用于select2显示 */
    private List<String> strainList;

    public Integer getFileId() {
	return fileId;
    }

    public void setFileId(Integer fileId) {
	this.fileId = fileId;
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
	this.dataKey = dataKey;
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
    public String getStrain() {
	return strain;
    }

    public void setStrain(String strain) {
	this.strain = strain;
    }
    public String getDataTags() {
	return dataTags;
    }

    public void setDataTags(String dataTags) {
	this.dataTags = dataTags;
    }
    public Long getSize() {
	return size;
    }

    public void setSize(Long size) {
	this.size = size;
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
    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }
    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    public Integer getFileFormat() {
	return fileFormat;
    }

    public void setFileFormat(Integer fileFormat) {
	this.fileFormat = fileFormat;
    }

    public String getSample() {
	return sample;
    }

    public void setSample(String sample) {
	this.sample = sample;
    }

    public String getMd5() {
	return md5;
    }

    public void setMd5(String md5) {
	this.md5 = md5;
    }

    public Integer getBlocks() {
	return blocks;
    }

    public void setBlocks(Integer blocks) {
	this.blocks = blocks;
    }
    public int getReportNum() {
	return reportNum;
    }
    public void setReportNum(int reportNum) {
	this.reportNum = reportNum;
    }

    public Integer getIsRunning() {
	return isRunning;
    }

    public void setIsRunning(Integer isRunning) {
	this.isRunning = isRunning;
    }

    public boolean isInProject() {
	return inProject;
    }

    public void setInProject(boolean inProject) {
	this.inProject = inProject;
    }
    public Integer getIsRunned() {
	return isRunned;
    }
    public void setIsRunned(Integer isRunned) {
	this.isRunned = isRunned;
    }

    public List<String> getStrainList() {
	return strainList;
    }

    public void setStrainList(List<String> strainList) {
	this.strainList = strainList;
    }

}