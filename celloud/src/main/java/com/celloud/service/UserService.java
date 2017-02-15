package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.celloud.model.mysql.SecRole;
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
	 * 保存随机生成的验证码，用来校验微信二维码
	 * 
	 * @param userId
	 * @param randomCode
	 * @author lin
	 * @date 2016年7月12日上午11:03:25
	 */
	public void insertWechatCode(Integer userId, String randomCode);

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
	 * 根据id获取user对象，但不包括其头像
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年7月15日下午3:05:34
	 */
	public User selectUserByIdNotIcon(int userId);

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
     * 校验手机号码是否存在
     * 
     * @param mobile
     * @return
     * @author leamo
     * @date 2017年2月13日 下午3:46:15
     */
    public boolean isCellphoneInUse(String cellphone);

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
	 * 查询用户所有的角色
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年12月20日下午5:03:11
	 */
	public List<SecRole> getRolesByUserId(Integer userId);

	/**
	 * 查询authFrom授予userId的所有权限
	 * 
	 * @param userId
	 * @param authFrom
	 * @return
	 * @author lin
	 * @date 2017年1月9日上午10:59:39
	 */
	public List<SecRole> getRoles(Integer userId, Integer authFrom);

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

	public List<User> selectUserByIds(String userIds);

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

    /**
     * 根据微信openid获取用户信息
     * 
     * @param openId
     * @return
     * @author leamo
     * @date 2016年7月4日 下午4:36:16
     */
    public User getUserByOpenId(String openId);

	/**
	 * 校验用户或者微信是否已经绑定
	 * 
	 * @param openId
	 * @param userId
	 * @return 0 未绑定
	 * @author lin
	 * @date 2016年7月8日上午11:28:04
	 */
	public int checkWechatBind(String openId, Integer userId);

	/**
	 * 微信解除绑定时的校验
	 * 
	 * @param openId
	 * @param pwd
	 * @return
	 * @author lin
	 * @date 2016年7月11日下午1:24:17
	 */
	public int checkWechatUnBind(String openId, String pwd);

	/**
	 * 微信解除绑定
	 * 
	 * @param openId
	 * @param pwd
	 * @return
	 * @author lin
	 * @date 2016年7月11日下午1:35:15
	 */
	public int wechatUnBind(String openId, String pwd);

    /**
     * 
     * @description 发送用户注册邮件
     * @author miaoqi
     * @date 2016年10月28日下午1:45:21
     *
     * @param email
     *            注册邮箱
     * @param loginUserId
     *            登录用户id
     */
	public Boolean sendRegisterEmail(String email, Integer[] appIds, Integer[] roles);

    /**
     * 发送注册短信
     * 
     * @return
     * @author leamo
     * @date 2017年2月13日 下午4:46:29
     */
    public Boolean sendRegisterSms(String cellphone, String truename, Integer[] appIds, Integer[] roles);

    /**
     * 新增C端用户
     * 
     * @param cellphone
     * @return
     * @author leamo
     * @date 2016年10月31日 下午7:43:07
     */
    public Integer addClientUser(String cellphone);

    /**
     * 判断是否新增C端用户
     * 
     * @param cellphone
     * @return
     * @author leamo
     * @date 2016年10月31日 下午8:24:03
     */
    public Integer checkAddClientUser(String cellphone);
    
    /**
     * 
     * @description 根据主键修改不为空的字段
     * @author miaoqi
     * @date 2016年10月27日下午4:06:25
     *
     * @param updateUser
     * @return
     */
    Boolean updateBySelective(User updateUser);

    /**
     * 
     * @description 从mongo中查询用户对象
     * @author miaoqi
     * @date 2016年11月29日下午1:12:57
     *
     * @return
     */
    User queryFromMongo(Integer userId);

    /**
     * 
     * @description 向mongo中插入用户对象
     * @author miaoqi
     * @date 2016年11月29日下午1:20:31
     *
     * @param user
     * @return
     */
    Boolean saveToMongo(User user);

    /**
     * 
     * @description 更新mongo中的用户对象
     * @author miaoqi
     * @date 2016年11月29日下午1:30:14
     *
     * @param user
     * @return
     */
    Boolean updateToMongo(User user);
}
