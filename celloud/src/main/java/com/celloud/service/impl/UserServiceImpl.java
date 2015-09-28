package com.celloud.service.impl;

import java.util.Map;

import com.celloud.dao.UserDao;
import com.celloud.service.UserService;
import com.google.inject.Inject;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28下午1:38:10
 * @version Revision: 1.0
 */
public class UserServiceImpl implements UserService {
    @Inject
    UserDao userDao;

    @Override
    public Map<String, Object> getUserAllInfo(Integer userId) {
	return userDao.getUserAllInfo(userId);
    }

}
