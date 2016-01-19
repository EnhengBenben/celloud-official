package com.celloud.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.celloud.dao.DataDao;
import com.celloud.dao.UserDao;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.User;
import com.celloud.service.DataService;
import com.celloud.utils.ConnectManager;
import com.celloud.utils.EntryUtil;
import com.google.inject.Inject;

public class DataServiceImpl implements DataService {
	@Inject
	private DataDao dataDao;
	@Inject
	private UserDao userDao;

	@Override
	public List<Map<String, Object>> getUserList(Integer companyId, Integer role, String orderType) {
		return dataDao.getUserList(companyId, role, orderType);
	}

	@Override
	public Map<String, Object> getPreDataView(Integer cmpId, Integer role) {
		Map<String, Object> resultMap = new HashMap<>();
		List<DataFile> list = dataDao.getUserMonthDataList(cmpId, role);
		List<DataFile> res = EntryUtil.toInsert(list);
		Connection conn = ConnectManager.getConnection();
		Object dataNum = dataDao.getBigUserDataNum(conn, cmpId, role);
		Object dataSize = dataDao.getBigUserDataSize(conn, cmpId, role);
		ConnectManager.close(conn);
		resultMap.put("dataNum", dataNum);
		resultMap.put("dataSize", dataSize);
		resultMap.put("dataList", list);
		long size = Long.parseLong(dataSize.toString());
		if ((size >> 30) > 0) {
			resultMap.put("size", size / (1024 * 1024 * 1024));
			resultMap.put("unit", "GB");
		} else if ((size >> 20) > 0) {
			resultMap.put("size", size / (1024 * 1024));
			resultMap.put("unit", "MB");
		} else if ((size >> 20) > 0) {
			resultMap.put("size", size / (1024));
			resultMap.put("unit", "KB");
		}
		return resultMap;
	}

	@Override
	public List<DataFile> getUserMonthDataList(Integer cmpId, Integer role) {
		List<DataFile> list = dataDao.getUserMonthDataList(cmpId, role);
		return list;
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

	@Override
	public Map<String, Object> getAllBigUserMonthData() {
		Connection conn = ConnectManager.getConnection();
		List<DataFile> list = null;
		list = dataDao.getAllBigUserMonthData(conn);
		List<User> userList = userDao.getBigUserList(conn);
		ConnectManager.close(conn);
		List<String> times = new ArrayList<>(48);
		Map<String, Object> map = new HashMap<>();
		Set<String> compnayNames = new HashSet<>(10);
		for (User u : userList) {
			compnayNames.add(u.getCompany_name());
		}
		map.put("companyNames", compnayNames);
		for (DataFile item : list) {
			if (!times.contains(item.getYearMonth())) {
				times.add(item.getYearMonth());
			}
		}
		map.put("xAxis", times);
		for (String time : times) {
			for (String cmpName : compnayNames) {
				getDataFileMap(map, cmpName).add(getDataFileList(time, cmpName, list));
			}
		}
		return map;
	}

	public List<DataFile> getDataFileMap(Map<String, Object> map, String cmpName) {
		Object object = map.get(cmpName);
		if (object == null) {
			object = new ArrayList<DataFile>();
			map.put(cmpName, object);
		}
		return (List<DataFile>) object;
	}

	public DataFile getDataFileList(String time, String cmpName, List<DataFile> list) {
		DataFile df = null;
		for (DataFile f : list) {
			if (f.getYearMonth().equals(time) && f.getCompany_name().equals(cmpName)) {
				df = f;
				break;
			}
		}

		if (df == null) {
			df = new DataFile();
			df.setYearMonth(time);
			df.setCompany_name(cmpName);
			df.setFileNum(0);
		}
		return df;
	}

}
