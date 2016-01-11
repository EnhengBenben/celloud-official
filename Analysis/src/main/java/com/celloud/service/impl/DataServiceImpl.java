package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import com.celloud.dao.DataDao;
import com.celloud.sdo.DataFile;
import com.celloud.service.DataService;
import com.celloud.utils.EntryUtil;
import com.google.inject.Inject;

public class DataServiceImpl implements DataService {
	@Inject
	private DataDao dataDao;

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId, Integer role, String orderType) {
		return dataDao.getUserList(companyId, role, orderType);
	}

	@Override
	public List<DataFile> getUserMonthDataList(Integer companyId, Integer role) {
		List<DataFile> list = dataDao.getUserMonthDataList(companyId, role);
		List<DataFile> res = EntryUtil.toInsert(list);
		return res;
	}

	@Override
	public List<DataFile> getUserMonthData(Integer userId, Integer companyId) {
		return dataDao.getUserMonthData(userId, companyId);
	}

	@Override
	public List<Map<String, Object>> getMonthDataList(Integer userId, String month, Integer companyId) {
		return dataDao.getMonthDataList(userId, month, companyId);
	}

	@Override
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId, String month) {
		return dataDao.getAllUserDataInMonth(companyId, month);
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds, String start, String end) {
		return dataDao.getUserFileRunState(userIds, start, end);
	}

	@Override
	public List<DataFile> getUserMonthDataJson(Integer userId, Integer companyId) {
		List<DataFile> list = dataDao.getUserMonthData(userId, companyId);
		List<DataFile> res = null;
		res = EntryUtil.toInsert(list);
		return res;
	}

	@Override
	public List<DataFile> getBigUserDataFile(Integer companyId) {
		// List<DataFile> res = null;
		// res = EntryUtil.toInsert(dataDao.getBigUserDataFile(companyId));
		return dataDao.getBigUserDataFile(companyId);
	}

	@Override
	public List<DataFile> getBigUserData() {

		return dataDao.getBigUserData();
	}

}
