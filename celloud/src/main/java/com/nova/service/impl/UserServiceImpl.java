package com.nova.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.nova.dao.IUserDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.User;
import com.nova.service.IUserService;

/**
 * 用户服务接口
 * 
 * @author xia
 * 
 */
public class UserServiceImpl implements IUserService {

	@Inject
	private IUserDao userDao;
	Logger log = Logger.getLogger(UserServiceImpl.class);

	/**
	 * 登录
	 */
	@Override
	public User login(User user) {
		User usr = null;
		if (user.getLoginUuid() == null) {
			usr = userDao.login(user.getUsername(), user.getPassword());
		} else {
			usr = userDao.login(user.getLoginUuid());
		}
		return usr;
	}

	@Override
	public boolean checkUsername(String username) {
		return userDao.checkUsername(username);
	}

	@Override
	public int register(User user) {
		int i = userDao.register(user);
		if (i != 0) {
			// 数据初始化暂时取消
			// userDao.initData(i);
		}
		return i;
	}

	@Override
	public boolean updateTheme(String theme, int userId) {
		return userDao.updateTheme(theme, userId);
	}

	@Override
	public String changePassword(int userId, String newPwd) {
		boolean result = userDao.changePassword(userId, newPwd);
		if (result) {
			return "success";
		} else {
			return "failure";
		}
	}

	@Override
	public boolean checkPwd(int userId, String pwd) {
		return userDao.checkPwd(userId, pwd);
	}

	@Override
	public List<User> getUserList(int userId) {
		return userDao.getUserList(userId);
	}

	@Override
	public String getEmailByUserId(int userId) {
		return userDao.getEmailByUserId(userId);
	}

	@Override
	public List<User> getUserListNotSharedPro(int userId, int projectId) {
		return userDao.getUserListNotSharedPro(userId, projectId);
	}

	@Override
	public List<User> getUserListSharedPro(int userId, int projectId) {
		return userDao.getUserListSharedPro(userId, projectId);
	}

	@Override
	public int getRoleByUserId(int userId) {
		return userDao.getRoleByUserId(userId);
	}

	@Override
	public int getUserIdByName(String userName) {
		return userDao.getUserIdByName(userName);
	}

	@Override
	public List<User> getAllUserList() {
		return userDao.getAllUserList();
	}

	@Override
	public PageList<User> getAllUserPageList(Page page, String userName) {
		if (null == page) {
			page = new Page(10, 1);
		}
		if (userName == null) {
			userName = "";
		}
		page.make(userDao.countUser(userName));
		return userDao.getAllUserPageList(page, userName);
	}

	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public boolean resetPwd(int userId) {
		return userDao.resetPwd(userId);
	}

	@Override
	public String getEmailByUserName(String userName) {
		return userDao.getEmailByUserName(userName);
	}

	@Override
	public String getUserNameByEmail(String email) {
		return userDao.getUserNameByEmail(email);
	}

	@Override
	public boolean insertFindPwdInfo(int userId, String md5) {
		return userDao.insertFindPwdInfo(userId, md5);
	}

	@Override
	public boolean insertFindPwdInfo(String email, String md5) {
		return userDao.insertFindPwdInfo(email, md5);
	}

	@Override
	public boolean validateUserByNameCode(String userName, String code) {
		return userDao.validateUserByNameCode(userName, code);
	}

	@Override
	public Date getExpireDateByUserIdCode(int userId, String code) {
		return userDao.getExpireDateByUserIdCode(userId, code);
	}

	@Override
	public int updateUserByUserName(User user) {
		return userDao.updateUserByUserName(user);
	}

	@Override
	public boolean checkUserEmail(String email) {
		return userDao.checkUserEmail(email);
	}

	@Override
	public String getEmailBySessionUserId(int userId) {
		return userDao.getEmailBySessionUserId(userId);
	}

	@Override
	public int updateExpireDateByUserId(int userId) {
		return userDao.updateExpireDateByUserId(userId);
	}

	@Override
	public boolean checkUserEmailByUserId(int userId, String email) {
		return userDao.checkUserEmailByUserId(userId, email);
	}

	@Override
	public boolean checkUsernameByUserId(int userId, String username) {
		return userDao.checkUsernameByUserId(userId, username);
	}

	@Override
	public int deleteUserByUserId(int userId) {
		return userDao.deleteUserByUserId(userId);
	}

	@Override
	public boolean updateNotify(int userId, int notify) {
		return userDao.updateNotify(userId, notify);
	}

	@Override
	public boolean getValidate(String email, String md5) {
		return userDao.getValidate(email, md5);
	}

	@Override
	public void deleteValidate(String email) {
		userDao.deleteValidate(email);
	}
}
