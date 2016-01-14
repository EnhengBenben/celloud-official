package com.celloud.service.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.celloud.dao.AppDao;
import com.celloud.sdo.App;
import com.celloud.sdo.LoginLog;
import com.celloud.service.AppService;
import com.celloud.utils.ConnectManager;
import com.google.inject.Inject;

public class AppServiceImpl implements AppService {
	@Inject
	private AppDao appDao;

	@Override
	public List<App> getAppListByBigUser(Integer companyId, Integer role) {
		return appDao.getAppListByBigUser(companyId, role);
	}

	@Override
	public App getAppById(Integer appId) {
		return appDao.getAppById(appId);
	}

	@Override
	public List<App> getAppRunTimeInWeek(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		return appDao.getAppRunTimeInWeek(cmpId, userId, start, end, role, softwareId);
	}

	@Override
	public List<App> getAppRunTimeInMonth(Integer cmpId, Integer userId, Date start, Date end, Integer role,
			String softwareId) {
		return appDao.getAppRunTimeInMonth(cmpId, userId, start, end, role, softwareId);
	}

	@Override
	public List<LoginLog> getTotalUserLogin(int role, int cmpId) {
		return appDao.getTotalUserLogin(role, cmpId);
	}

	@Override
	public List<LoginLog> getBrowerCount() {
		return appDao.getBrowerCount();
	}

	@Override
	public List<App> getBigUserAppList() {
		return appDao.getBigUserAppList();
	}

	@Override
	public List<App> getAppRun(int app_id) {
		return appDao.getAppRun(app_id);
	}

	@Override
	public List<App> getAppListByBigUserId(int cmpId) {
		return appDao.getAppListByBigUserId(cmpId);
	}

	@Override
	public List<App> getAppList(Integer role, Integer cmpId, Date start, Date end, Integer topN) {
		Connection conn = ConnectManager.getConnection();
		List<App> list = appDao.getAppList(conn, role, cmpId, start, end, topN);
		ConnectManager.close(conn);
		return list;

	}

	@Override
	public List<App> getUserRunNum(int role, int cmpId) {
		return appDao.getUserRunNum(role, cmpId);
	}

	@Override
	public List<App> getAppRunNum(int role, int cmpId) {
		return appDao.getAppRunNum(role, cmpId);
	}
}
