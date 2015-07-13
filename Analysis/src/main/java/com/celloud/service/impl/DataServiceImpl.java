package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import com.celloud.dao.DataDao;
import com.celloud.service.DataService;
import com.google.inject.Inject;

public class DataServiceImpl implements DataService {
	@Inject
	private DataDao dataDao;
	@Override
	public List<Map<String, Object>> getUserList(Integer companyId) {
		return dataDao.getUserList(companyId);
	}

	@Override
	public List<Map<String, Object>> getUserMonthDataList(Integer companyId) {
		return dataDao.getUserMonthDataList(companyId);
	}

	@Override
	public List<Map<String, Object>> getUserMonthData(Integer userId,
			Integer companyId) {
		return dataDao.getUserMonthData(userId, companyId);
	}

	@Override
	public List<Map<String, Object>> getMonthDataList(Integer userId,
			String month, Integer companyId) {
		return dataDao.getMonthDataList(userId, month, companyId);
	}

	@Override
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId,
			String month) {
		return dataDao.getAllUserDataInMonth(companyId, month);
	}

	@Override
	public Object getBigUserDataNum(Integer companyId) {
		return dataDao.getBigUserDataNum(companyId);
	}

	@Override
	public Double getBigUserDataSize(Integer companyId) {
		return (Double) dataDao.getBigUserDataSize(companyId);
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds,
			String start, String end) {
		return dataDao.getUserFileRunState(userIds, start, end);
	}

}
