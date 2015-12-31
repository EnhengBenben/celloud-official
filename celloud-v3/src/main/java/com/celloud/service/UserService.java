package com.celloud.service;

import java.util.Date;

import com.celloud.model.User;
import com.celloud.page.Page;

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

    /**
     * 根据用户名及邮箱获取用户对象，用来校验找回密码时，用户输入的用户名及邮箱是否匹配
     * 
     * @param username
     * @param email
     * @return
     */
    public User getUserByEmail(String email);

    /**
     * 保存随机生成的验证码，用来校验用户重置密码是否合法
     * 
     * @param userId
     * @param randomCode
     */
    public void insertFindPwdInfo(Integer userId, String randomCode);

    /**
     * 清理重置密码链接(指定用户的，或者已过期的)
     * 
     * @param userId
     * @param expireDate
     */
    public void cleanFindPwd(int userId, Date expireDate);

    /**
     * 清理重置密码链接(指定用户的，或者已过期的)
     * 
     * @param username
     * @param expireDate
     */
    public void cleanFindPwd(String username, Date expireDate);

    /**
     * 获取用户找回密码链接对应的user对象
     * 
     * @param username
     * @param randomCode
     * @return
     */
    public User getUserByFindPwd(String username, String randomCode);

    /**
     * 根据用户名修改密码，用来用户找回密码时的重置密码
     * 
     * @param username
     * @param password
     */
    public void updatePassword(int userId, String password);

    public Page<User> findUsers(Page<User> page);

}
