package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IDataTypeDao;
import com.nova.sdo.DataType;
import com.nova.service.IDataTypeService;

public class DataTypeServiceImpl implements IDataTypeService {

	@Inject
	private IDataTypeDao dataTypeDao;

	@Override
	public List<DataType> getDataTypeList() {
		return dataTypeDao.getDataTypeList();
	}

}
