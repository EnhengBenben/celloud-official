package com.nova.action;

import java.util.List;

import com.google.inject.Inject;
import com.nova.sdo.DataFormat;
import com.nova.service.IDataFormatService;

public class DataFormatAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Inject
	private IDataFormatService dataFormatService;
	private List<DataFormat> typeList;

	public String getAllDataType() {
		typeList = dataFormatService.getDataFormatList();
		return SUCCESS;
	}

	public List<DataFormat> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DataFormat> typeList) {
		this.typeList = typeList;
	}

}
