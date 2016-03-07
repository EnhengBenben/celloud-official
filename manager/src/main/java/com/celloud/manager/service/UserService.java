package com.celloud.manager.service;

import com.celloud.manager.model.User;

/**
 * 用户服务接口
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午12:53:35
 */
public interface UserService {
    /**
     * 用户登录校验
     * 
     * @param user
     * @return
     */
    public User login(User user);

}
