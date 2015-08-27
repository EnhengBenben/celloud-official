package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.DataType;
import com.nova.service.IDataTypeService;

@ParentPackage("celloud-default")
@Action("dataType")
@Results({ @Result(name = "success", type = "json", params = { "root",
	"dataTypeList" }) })
public class DataTypeAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private IDataTypeService dataTypeService;
    private List<DataType> dataTypeList;

    public String getAllDataTypeList() {
	dataTypeList = dataTypeService.getDataTypeList();
	return SUCCESS;
    }

    public List<DataType> getDataTypeList() {
	return dataTypeList;
    }

    public void setDataTypeList(List<DataType> dataTypeList) {
	this.dataTypeList = dataTypeList;
    }

}
