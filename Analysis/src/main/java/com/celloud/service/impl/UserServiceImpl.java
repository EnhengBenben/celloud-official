package com.celloud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.UserDao;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
import com.celloud.service.UserService;
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
	public Object getBigUsersUserNum(Integer companyId) {
		return userDao.getBigUsersUserNum(companyId);
	}

	@Override
	public List<Map<String, Object>> getBigUsersUser(Integer companyId) {
		return userDao.getBigUsersUser(companyId);
	}

	@Override
	public List<User> getUserListByBigCom(Integer companyId) {
		return userDao.getUserListByBigCom(companyId);
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

}
