package com.nova.sdo;

import java.io.Serializable;

public class DataType implements Serializable {
    private static final long serialVersionUID = 1L;
    // 类型编号
    private int typeId;
    // 类型描述
    private String typeDesc;

    public int getTypeId() {
	return typeId;
    }

    public void setTypeId(int typeId) {
	this.typeId = typeId;
    }

    public String getTypeDesc() {
	return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
	this.typeDesc = typeDesc;
    }

}
