package com.nova.sdo;

import java.io.Serializable;

public class FileTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer fileId;
    private String projectName;

    public Integer getFileId() {
	return fileId;
    }

    public void setFileId(Integer fileId) {
	this.fileId = fileId;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

}
