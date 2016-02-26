package com.celloud.model.mongo;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import com.celloud.model.mysql.DataFile;

public class TaskQueue implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private ObjectId id;
	/**
	 * 数据编号
	 */
	private String dataKey;

	/**
	 * 软件id
	 */
	private Integer appId;

	/**
	 * 报告id
	 */
	private Integer projectId;

	/**
	 * 所运行数据信息
	 */
	@Embedded
	private List<DataFile> dataList;

	/**
	 * 报告路径
	 */
	private String path;
	/**
	 * 运行基础命令
	 */
	private String command;

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<DataFile> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataFile> dataList) {
		this.dataList = dataList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
