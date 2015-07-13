package com.nova.action;

import org.apache.struts2.ServletActionContext;

import com.nova.utils.PropertiesUtil;

public class PathAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String toolsPath;
	private String toolsOutPath;
	private String downloadPath;
	private String totalPath;

	public String getPath() {
		toolsPath = PropertiesUtil.toolsPath;
		downloadPath = ServletActionContext.getServletContext().getRealPath(
				"/pages/upload");
		toolsOutPath = PropertiesUtil.toolsOutPath;
		downloadPath = ServletActionContext.getServletContext().getRealPath(
				"/pages/upload");
		totalPath = toolsPath + "," + downloadPath + "," + toolsOutPath;
		return SUCCESS;
	}

	/**
	 * 获取tools项目路径
	 * 
	 * @return
	 */
	public String getToolPath() {
		toolsPath = PropertiesUtil.toolsPath;
		return SUCCESS;
	}

	/**
	 * 获取tools项目路径
	 * 
	 * @return
	 */
	public String getDownloadFilePath() {
		downloadPath = ServletActionContext.getServletContext().getRealPath(
				"/pages/upload");
		return SUCCESS;
	}

	public String getToolsPath() {
		return toolsPath;
	}

	public void setToolsPath(String toolsPath) {
		this.toolsPath = toolsPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public String getTotalPath() {
		return totalPath;
	}

	public void setTotalPath(String totalPath) {
		this.totalPath = totalPath;
	}

	public String getToolsOutPath() {
		return toolsOutPath;
	}

	public void setToolsOutPath(String toolsOutPath) {
		this.toolsOutPath = toolsOutPath;
	}

}
