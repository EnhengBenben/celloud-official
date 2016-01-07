package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.Constants;
import com.celloud.mapper.UserMapper;
import com.celloud.model.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.UserService;
import com.celloud.utils.MD5Util;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.checkLogin(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void insertFindPwdInfo(Integer userId, String randomCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, Constants.FIND_PASSWORD_EXPIRE_TIME);
        userMapper.insertFindPwdInfo(userId, calendar.getTime(), randomCode);
    }

    @Override
    public void cleanFindPwd(int userId, Date expireDate) {
        userMapper.cleanFindPwdByUserId(userId, expireDate);
    }

    @Override
    public void cleanFindPwd(String username, Date expireDate) {
        userMapper.cleanFindPwdByUsername(username, expireDate);
    }

    @Override
    public User getUserByFindPwd(String username, String randomCode) {
        cleanFindPwd(null, new Date());
        return userMapper.getUserByFindPwd(username, randomCode);
    }

    @Override
    public void updatePassword(int userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(MD5Util.getMD5(password));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageList<User> findUsers(Page page) {
        List<User> lists = userMapper.findUsers(page);
        return new PageList<>(page, lists);
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

}
