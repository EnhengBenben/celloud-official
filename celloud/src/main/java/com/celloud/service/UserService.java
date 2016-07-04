package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.celloud.model.mysql.User;

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
     * @return
     */
    public int updatePassword(int userId, String password);

    /**
     * 根据id获取user对象
     * 
     * @param userId
     * @return
     */
    public User selectUserById(int userId);

    /**
     * 修改用户基本信息
     * 
     * @param user
     * @return
     */
    public Integer updateUserInfo(User user);
    
    /**
     * 修改用户邮箱
     * 
     * @param user
     * @return
     * @author lin
     * @date 2016年5月17日下午3:25:03
     */
    public Integer updateUserEmail(User user);

    /**
     * 校验邮箱是否存在(排除userId本身的email，如userId不存在，则默认为0，表示校验email在全局范围内是否已存在)
     * 
     * @param email
     * @param userId
     * @return
     */
    public boolean isEmailInUse(String email, Integer userId);

    /**
     * 根据用户名称获取用户
     * 
     * @param username
     * @return
     * @date 2016-1-7 下午5:10:00
     */
    public User getUserByName(String username);

    // TODO 返回值将来也许需要改成List<Integer>
    /**
     * 获取用户所属的大客户
     * 
     * @param userId
     * @return
     * @date 2016-1-9 下午10:35:29
     */
    public Integer getCompanyIdByUserId(Integer userId);

    /**
     * 根据用户名查询用户
     * 
     * @param username
     * @return
     */
    public User findByUsernameOrEmail(String username);

    /**
     * 查询用户所有的角色
     * 
     * @param userId
     * @return
     */
    public Set<String> findRoles(Integer userId);

    /**
     * 查询用户所有的权限
     * 
     * @param userId
     * @return
     */
    public Set<String> findPermissions(Integer userId);

    /**
     * 根据id获取user对象列表
     * 
     * @param userId
     * @return
     */
    public List<String> selectUserUserById(String userIds);

    /**
     * 新增用户与微信服务号绑定关系
     * 
     * @param userId
     * @param openId
     * @param unionId
     * @return
     * @author leamo
     * @date 2016年6月30日 上午10:28:24
     */
    public Integer insertUserWechatInfo(Integer userId, String openId,
            String unionId);

    /**
     * 获取用户绑定的微信openid
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016年7月4日 上午2:33:05
     */
    public String getOpenIdByUser(Integer userId);
}
