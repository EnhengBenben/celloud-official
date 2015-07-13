package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IDataFormatDao;
import com.nova.sdo.DataFormat;
import com.nova.service.IDataFormatService;

public class DataFormatServiceImpl implements IDataFormatService {
	@Inject
	private IDataFormatDao dataFormatDao;

	@Override
	public int getDataFormatIdByName(String formatName) {
		return dataFormatDao.getDataFormatIdByName(formatName);
	}

	@Override
	public List<DataFormat> getDataFormatList() {
		return dataFormatDao.getDataFormatList();
	}

	@Override
	public String getDataFormatNameById(int dataFormatId) {
		return dataFormatDao.getDataFormatNameById(dataFormatId);
	}
}
