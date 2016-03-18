package com.celloud.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.User;
import com.celloud.manager.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.checkLogin(user);
    }

}
