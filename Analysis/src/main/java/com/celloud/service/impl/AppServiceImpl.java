package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import com.celloud.dao.AppDao;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.service.AppService;
import com.google.inject.Inject;

public class AppServiceImpl implements AppService {
	@Inject
	private AppDao appDao;
	@Override
	public Object getBigUserAPPNum(Integer companyId,int role) {
		return appDao.getBigUserAPPNum(companyId,role);
	}

	@Override
	public List<App> getAppListByBigUser(Integer companyId,Integer role) {
		return appDao.getAppListByBigUser(companyId,role);
	}

	@Override
	public App getAppById(Integer appId) {
		return appDao.getAppById(appId);
	}

	@Override
	public List<App> getAppRunTimeInWeek(Integer cmpId,Integer userId, Date start, Date end,Integer role,String softwareId) {
		return appDao.getAppRunTimeInWeek(cmpId,userId, start, end,role,softwareId);
	}

	@Override
	public List<App> getAppRunTimeInMonth(Integer cmpId,Integer userId, Date start, Date end,Integer role,String softwareId) {
		return appDao.getAppRunTimeInMonth(cmpId,userId, start, end,role,softwareId);
	}

	@Override
	public List<App> getAppByCompanyId(Integer cmpId, Integer role) {
		return appDao.getAppByCompanyId(cmpId, role);
	}
	@Override
	public List<App> getAppRunTop(String type, int topN, Date start, Date end) {
		return appDao.getAppRunTop(type, topN, start, end);
	}

	@Override
	public List<App> getTotalUserRunNum(int topN) {
		return appDao.getTotalUserRunNum(topN);
	}

	@Override
	public List<LoginLog> getTotalUserLogin(int topN) {
		return appDao.getTotalUserLogin(topN);
	}

	@Override
	public List<LoginLog> getBrowerCount() {
		return appDao.getBrowerCount();
	}

	@Override
	public List<App> getAppRunNumCount(Date start) {
		
		return appDao.getAppRunNumCount(start);
	}


	@Override
	public List<App> getAppUserCount(Date start) {
		return appDao.getAppUserCount(start);
	}

	@Override
	public List<App> getTotalAppRunNum(int topN) {
		return appDao.getTotalAppRunNum(topN);
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
}
