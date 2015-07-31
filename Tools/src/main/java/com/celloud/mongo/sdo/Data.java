package com.celloud.mongo.sdo;


/**
 * 所运行文件信息
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-30上午10:53:20
 * @version Revision: 1.0
 */
public class Data {
	/**
	 * 文件编号
	 */
	private Integer fileId;
	/**
	 * 用户编号 提交者
	 */
	private Integer userId;
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
	 * 样本
	 */
	private String sample;
	/**
	 * 文件别名
	 */
	private String anotherName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

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

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}
}
