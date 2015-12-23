package com.celloud.service.impl;

import java.util.Date;
import java.util.List;
import com.celloud.dao.LoginLogDao;
import com.celloud.sdo.LoginLog;
import com.celloud.service.LoginLogService;
import com.google.inject.Inject;

public class LoginLogServiceImpl implements LoginLogService {
	@Inject
	private LoginLogDao loginDao;

	@Override
	public List<LoginLog> getUserLoginInWeek(Date start) {
		return loginDao.getUserLoginInWeek(start);
	}

	@Override
	public List<LoginLog> getUserLoginNum(Date start) {

		return loginDao.getUserLoginNum(start);
	}

	@Override
	public List<LoginLog> getBrowserInWeek(Date start) {
		return loginDao.getBrowserInWeek(start);
	}

}
