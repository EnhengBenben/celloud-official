package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import com.celloud.dao.SoftwareDao;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;

public class SoftwareServiceImpl implements SoftwareService {
	@Inject
	private SoftwareDao softwareDao;
	@Override
	public Object getBigUserAPPNum(Integer companyId,int role) {
		return softwareDao.getBigUserAPPNum(companyId,role);
	}

	@Override
	public List<Software> getAppListByBigUser(Integer companyId,Integer role) {
		return softwareDao.getAppListByBigUser(companyId,role);
	}

	@Override
	public Software getAppById(Integer appId) {
		return softwareDao.getAppById(appId);
	}

	@Override
	public List<Software> getAppRunTimeInWeek(Integer cmpId,Integer userId, Date start, Date end,Integer role,String softwareId) {
		return softwareDao.getAppRunTimeInWeek(cmpId,userId, start, end,role,softwareId);
	}

	@Override
	public List<Software> getAppRunTimeInMonth(Integer cmpId,Integer userId, Date start, Date end,Integer role,String softwareId) {
		return softwareDao.getAppRunTimeInMonth(cmpId,userId, start, end,role,softwareId);
	}

	@Override
	public List<Software> getAppByCompanyId(Integer cmpId, Integer role) {
		return softwareDao.getAppByCompanyId(cmpId, role);
	}
	@Override
	public List<Software> getAppRunTop(String type, int topN, Date start, Date end) {
		return softwareDao.getAppRunTop(type, topN, start, end);
	}

	@Override
	public List<Software> getTotalUserRunNum(int topN) {
		return softwareDao.getTotalUserRunNum(topN);
	}

	@Override
	public List<LoginLog> getTotalUserLogin(int topN) {
		return softwareDao.getTotalUserLogin(topN);
	}

	@Override
	public List<LoginLog> getBrowerCount() {
		return softwareDao.getBrowerCount();
	}

	@Override
	public List<Software> getAppRunNumCount(Date start) {
		
		return softwareDao.getAppRunNumCount(start);
	}


	@Override
	public List<Software> getAppUserCount(Date start) {
		return softwareDao.getAppUserCount(start);
	}

	@Override
	public List<Software> getTotalAppRunNum(int topN) {
		return softwareDao.getTotalAppRunNum(topN);
	}
}
