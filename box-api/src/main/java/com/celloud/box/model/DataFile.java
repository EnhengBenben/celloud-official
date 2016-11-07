package com.celloud.box.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataFile {
	private Integer userId;
	private Integer fileId;
	private String filename;
	private String anotherName;
	private String dataKey;
	private Integer needSplit;
	private Integer tagId;
	private String batch;
	private String objectKey;
	private String newName;
	private String ext;
	private Long fileSize;
	private String md5;
	private String path;
	private boolean splited;
	private boolean uploaded;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public Integer getNeedSplit() {
		return needSplit;
	}

	public void setNeedSplit(Integer needSplit) {
		this.needSplit = needSplit;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSplited() {
		return this.needSplit == null || splited;
	}

	public void setSplited(boolean splited) {
		this.splited = splited;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	public boolean serialize(String path) {
		return serialize(new File(path));
	}

	public boolean serialize() {
		Validate.notNull(path, "没有设置文件路径，不能存储其信息.");
		return serialize(new File(path + ".json"));
	}

	public boolean serialize(File file) {
		try {
			if (this.needSplit == null) {
				this.splited = true;
			}
			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE).writeValue(file, this);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static DataFile load(String path) {
		return load(new File(path));
	}

	public static DataFile load(File file) {
		DataFile dataFile = null;
		try {
			dataFile = new ObjectMapper().readValue(file, DataFile.class);
		} catch (IOException e) {
		}
		return dataFile;
	}

	public boolean createFile() {
		boolean result = false;
		if (path != null && new File(path).exists()) {
			try {
				result = new File(path + ".json").createNewFile();
			} catch (IOException e) {

			}
		}
		return result;
	}
}
