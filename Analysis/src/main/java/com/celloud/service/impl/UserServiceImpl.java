package com.celloud.service.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.celloud.dao.UserDao;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.Entry;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.sdo.TotalCount;
import com.celloud.sdo.User;
import com.celloud.service.UserService;
import com.celloud.utils.ConnectManager;
import com.google.inject.Inject;

public class UserServiceImpl implements UserService {
	@Inject
	private UserDao userDao;

	@Override
	public List<LoginLog> logCountEveryUser(Date beginDate, Date endDate) {
		return userDao.logCountEveryUser(beginDate, endDate);
	}

	@Override
	public List<LoginLog> logCountEveryDay(Date beginDate, Date endDate) {
		return userDao.logCountEveryDay(beginDate, endDate);
	}

	@Override
	public List<LoginLog> logCountEveryBrowser(Date beginDate, Date endDate) {
		return userDao.logCountEveryBrowser(beginDate, endDate);
	}

	@Override
	public Integer userCount() {
		return userDao.userCount();
	}

	@Override
	public Integer userAddBetweenDate(Date beginDate, Date endDate) {
		return userDao.userAddBetweenDate(beginDate, endDate);
	}

	@Override
	public User login(User user) {
		return userDao.login(user.getUsername(), user.getPassword());
	}

	@Override
	public Object getBigUsersUserNum(Integer companyId, int role) {
		return userDao.getBigUsersUserNum(companyId, role);
	}

	@Override
	public List<Map<String, Object>> getBigUsersUser(Integer companyId) {
		return userDao.getBigUsersUser(companyId);
	}

	@Override
	public List<User> getUserListByBigCom(Integer companyId, Integer role, String orderType) {
		return userDao.getUserListByBigCom(companyId, role, orderType);
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public List<LoginLog> getLogById(String userId) {
		return userDao.getLogById(userId);
	}

	@Override
	public List<App> getAppRunTimesByUId(Integer userId) {
		return userDao.getAppRunTimesByUId(userId);
	}

	@Override
	public List<Entry> getAppRunEachMonthByUId(Integer userId) {
		return userDao.getAppRunEachMonthByUId(userId);
	}

	@Override
	public List<Entry> getAppRunEachWeekByUId(Integer userId) {
		return userDao.getAppRunEachWeekByUId(userId);
	}

	@Override
	public List<DataFile> getUploadFileMonth(Integer userId) {
		return userDao.getUploadFileMonth(userId);
	}

	@Override
	public List<DataFile> getUploadFileWeek(Integer userId) {
		return userDao.getUploadFileWeek(userId);
	}

	@Override
	public List<LoginLog> getLoginUserSortWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getLoginUserSortWeek(cmpId, role, uids, start, end);
	}

	@Override
	public List<LoginLog> getLoginUserSortMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getLoginUserSortMonth(cmpId, role, uids, start, end);
	}

	@Override
	public List<DataFile> getFileMonthInDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getFileMonthInDate(cmpId, role, uids, start, end);
	}

	@Override
	public List<DataFile> getFileInWeekDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getFileInWeekDate(cmpId, role, uids, start, end);
	}

	@Override
	public List<App> getAppRunInWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getAppRunInWeek(cmpId, role, uids, start, end);
	}

	@Override
	public List<App> getAppRunInMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end) {
		return userDao.getAppRunInMonth(cmpId, role, uids, start, end);
	}

	@Override
	public List<User> getUserByCompany(Integer companyId, Integer role) {
		return userDao.getUserByCompany(companyId, role);
	}

	@Override
	public List<TotalCount> getCountInHistory() {
		return userDao.getCountInHistory();
	}


	@Override
	public Map<String, Object> getUserActivity(int role, int cmpId, Date start, Date end, int topN) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = ConnectManager.getConnection();
		List<DataFile> fileNum = userDao.getUserFileNum(conn, role, cmpId, start, end, topN);
		List<DataFile> fileSize = userDao.getUserFileSize(conn, role, cmpId, start, end, topN);
		List<App> appRun = userDao.getUserRunApp(conn, role, cmpId, start, end, topN);
		ConnectManager.close(conn);
		map.put("fileNum", fileNum);
		map.put("size", fileSize);
		map.put("appRun", appRun);
		return map;
	}
}
