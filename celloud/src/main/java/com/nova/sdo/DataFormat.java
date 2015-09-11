package com.nova.sdo;

import java.io.Serializable;

public class DataFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    // 文件格式编号
    private int formatId;
    // 文件格式描述
    private String formatDesc;

    public int getFormatId() {
	return formatId;
    }

    public void setFormatId(int formatId) {
	this.formatId = formatId;
    }

    public String getFormatDesc() {
	return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
	this.formatDesc = formatDesc;
    }

}
