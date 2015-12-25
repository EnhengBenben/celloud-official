package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.UserMapper;
import com.celloud.model.User;
import com.celloud.service.UserService;
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        return userMapper.checkLogin(user);
    }
    
}
