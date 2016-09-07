package com.celloud.box.model;

import java.util.Map;

public class Newfile {
	private String dataKey;
	private Integer fileId;
	private String newName;
	private String ext;

	public Newfile(String content) {

	}

	public Newfile(Map<String, Object> values) {
		init(values);
	}

	private void init(Map<String, Object> values) {
		this.dataKey = (String) values.get("dataKey");
		this.newName = (String) values.get("newName");
		this.ext = (String) values.get("ext");
		try {
			this.fileId = Integer.parseInt(String.valueOf(values.get("fileId")));
		} catch (Exception e) {
		}
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

}
