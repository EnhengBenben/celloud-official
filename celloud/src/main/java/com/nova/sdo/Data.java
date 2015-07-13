package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 文件编号
	 */
	private Integer fileId;

	/**
	 * 用户编号 提交者
	 */
	private Integer userId;

	private Integer userRole;

	/**
	 * 数据编号
	 */
	private String dataKey;

	/**
	 * 文件名字
	 */
	private String fileName;

	/**
	 * 物种
	 */
	private String strain;

	/**
	 * 数据标签
	 */
	private String dataTags;

	/**
	 * 大小 单位：b
	 */
	private Long size;

	/**
	 * 提交时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 文件路径
	 */
	private String path;

	/**
	 * 状态 0:未删除，1:已删除
	 */
	private Integer state;

	/**
	 * 是否已分配项目
	 */
	private boolean inProject;

	/**
	 * 所有人
	 */
	private String owner;

	/**
	 * 数据所在项目
	 */
	private int projectId;

	private int shareFileId;

	/**
	 * 文件格式：1：峰图文件ab1，2：压缩文件，3：fasta文件,4：fastq文件,5:bam格式，6：gz格式
	 */
	private int fileFormat;

	private String sample;// 样本

	/**
	 * 共享次数
	 */
	private int sharedCount;

	private int reportNum;
    /**
     * 文件别名
     */
    private String anotherName;

	/**
	 * 是否正在运行，0 否，>0 是
	 */
	private Integer isRunning;
	/**
	 * 是否运行完成，0 否，>0 是
	 */
	private Integer isRunned;
	/**
	 * 文件的md5值
	 */
	private String md5;
	private Integer blocks;

	public int getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}

	public int getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(int fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * @param userId
	 *            用户编号 提交者
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 用户编号 提交者
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param dataKey
	 *            数据编号
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	/**
	 * 数据编号
	 */
	public String getDataKey() {
		return dataKey;
	}

	/**
	 * @param fileName
	 *            文件名字
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 文件名字
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param strain
	 *            物种
	 */
	public void setStrain(String strain) {
		this.strain = strain;
	}

	/**
	 * 物种
	 */
	public String getStrain() {
		return strain;
	}

	/**
	 * @param dataTags
	 *            数据标签
	 */
	public void setDataTags(String dataTags) {
		this.dataTags = dataTags;
	}

	/**
	 * 数据标签
	 */
	public String getDataTags() {
		return dataTags;
	}

	/**
	 * @param size
	 *            大小
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * 大小
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param createDate
	 *            提交时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 提交时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param updateDate
	 *            更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param path
	 *            文件路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 文件路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param state
	 *            状态 0:未归档，1:已归档
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 状态 0:未归档，1:已归档
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 是否已分配项目
	 * 
	 * @return
	 */
	public boolean isInProject() {
		return inProject;
	}

	public void setInProject(boolean inProject) {
		this.inProject = inProject;
	}

	/**
	 * 文件编号
	 * 
	 * @return
	 */
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public int getShareFileId() {
		return shareFileId;
	}

	public void setShareFileId(int shareFileId) {
		this.shareFileId = shareFileId;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public int getReportNum() {
		return reportNum;
	}

	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}

    public String getAnotherName() {
        return anotherName;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
    }

	public Integer getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Integer isRunning) {
		this.isRunning = isRunning;
	}

	public Integer getIsRunned() {
		return isRunned;
	}

	public void setIsRunned(Integer isRunned) {
		this.isRunned = isRunned;
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
}