/**    
 * @Title: FileData.java  
 * @Package com.nova.sdo  
 * @author summer    
 * @date 2012-6-20 下午02:31:18  
 * @version V1.0    
 */
package com.nova.sdo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: FileData
 * @Description: (用户上传数据服务对象)
 * @author summer
 * @date 2012-6-20 下午02:31:18
 * 
 */
public class FileData implements Serializable {
	private static final long serialVersionUID = 1L;
	private int fileId;// 文件编号
	private String key;// 系统数据key
	private String committer;// 提交者
	private String fileName;// 文件名
	private String fileFormat;// 文件格式
	private String fileTags;// 文件标签
	private Date createDate;// 上传时间
	private Date updateDate;// 标签编辑时间
	private String description;// 描述
	private String path;// 上传路径

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCommitter() {
		return committer;
	}

	public void setCommitter(String committer) {
		this.committer = committer;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileTags() {
		return fileTags;
	}

	public void setFileTags(String fileTags) {
		this.fileTags = fileTags;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
