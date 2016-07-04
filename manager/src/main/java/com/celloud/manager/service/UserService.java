package com.celloud.manager.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.manager.model.User;
import com.celloud.manager.model.UserSelect;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

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
     * 校验邮箱是否存在(排除userId本身的email，如userId不存在，则默认为0，表示校验email在全局范围内是否已存在)
     * 
     * @param email
     * @param userId
     * @return
     */
    public boolean isEmailInUse(String email, Integer userId);

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @param userId
     * @return
     * @author han
     * @date 2016年1月28日 下午2:38:54
     */
    public boolean isUsernameInUse(String username, Integer userId);

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
     * 分页获取用户列表
     *
     * @param page
     * @return
     * @author han
     * @date 2016年1月27日 下午4:13:47
     */
    public PageList<Map<String, String>> getUserByPage(Integer companyId, Page page, String searchField,
            String keyword);

    /**
     * 根据邮箱和验证码来判断请求是否有效（用于注册）
     * 
     * @param email
     * @param md5
     * @return
     */
    public boolean getValidate(String email, String md5);

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     * @author han
     * @date 2016年1月28日 下午2:50:31
     */
    public boolean addUser(User user, String md5code, Integer appCompanyId);

    /**
     * 发送添加用户邮件
     *
     * @param emailArray
     * @param deptId
     *            所属部门Id
     * @param companyId
     *            所属公司Id
     * @param appCompanyId
     *            大客户Id
     * @param appIdArray
     * @author han
     * @date 2016年1月29日 上午11:12:39
     */
    public void sendRegisterEmail(String[] emailArray, Integer deptId, Integer companyId, Integer appCompanyId,
            Integer[] appIdArray, Integer[] roleIdArray, Integer role);

    /**
     * 获取所有用户列表
     *
     * @return
     * @author han
     * @date 2016年2月18日 下午5:17:39
     */
    public List<User> getAllUserList();

    /**
     * 获取所有select2用户列表
     * 
     * @author mq
     * @return
     */
    public List<UserSelect> getAllUserSelectList();

    /**
     * 根据用户id获取已授权的app
     */
    public List<Map<String, String>> getAppListByUserId(Integer userId);

    /**
     * 授权普通用户App权限
     */
    public void grantUserApp(Integer userId, List<Map<String, String>> appAddList);

    /**
     * 授权普通用户模块角色
     */
    public void grantUserRole(Integer userId, String[] roleIds);

}
