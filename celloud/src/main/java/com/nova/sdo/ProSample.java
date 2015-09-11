package com.nova.sdo;

import java.io.Serializable;
import java.util.List;

public class ProSample implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;// 样本名称
    private List<String> dataList;// 数据列表

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<String> getDataList() {
	return dataList;
    }

    public void setDataList(List<String> dataList) {
	this.dataList = dataList;
    }

}
