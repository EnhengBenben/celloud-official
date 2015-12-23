package com.celloud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.utils.EntryUtil;
import com.celloud.dao.DataDao;
import com.celloud.sdo.Data;
import com.celloud.service.DataService;
import com.google.inject.Inject;

public class DataServiceImpl implements DataService {
	@Inject
	private DataDao dataDao;

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId,Integer role) {
		return dataDao.getUserList(companyId,role);
	}

	@Override
	public List<Data> getUserMonthDataList(Integer companyId,Integer role) {
		List<Data> list = dataDao.getUserMonthDataList(companyId,role);
		List<Data> res = EntryUtil.toInsert(list);
		return res;
	}

	@Override
	public List<Data> getUserMonthData(Integer userId, Integer companyId) {
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
	public Object getBigUserDataNum(Integer companyId,int role) {
		return dataDao.getBigUserDataNum(companyId,role);
	}

	@Override
	public Double getBigUserDataSize(Integer companyId,int role) {
		return (Double) dataDao.getBigUserDataSize(companyId,role);
	}

	@Override
	public List<Map<String, Object>> getUserFileRunState(String userIds,
			String start, String end) {
		return dataDao.getUserFileRunState(userIds, start, end);
	}

	@Override
	public List<Data> getUserMonthDataJson(Integer userId, Integer companyId) {
		List<Data> list = dataDao.getUserMonthData(userId, companyId);
		List<Data> res = null;
		res = EntryUtil.toInsert(list);
		return res;
	}

	@Override
	public List<Data> getUserWeekData(Date start) {
		return dataDao.getUserWeekData(start);
	}

	@Override
	public List<Data> getEachDayData(Date start) {
		return dataDao.getEachDayData(start);
	}

}
