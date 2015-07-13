package com.nova.dao;

import java.util.Date;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.UserDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.User;

/**
 * @ClassName: IUserDao
 * @Description: (用户持久层)
 * @author summer
 * @date 2012-6-28 下午07:29:42
 * 
 */
@ImplementedBy(UserDaoImpl.class)
public interface IUserDao {

	/**
	 * 用户登陆
	 * 
	 * @param username
	 * @param password
	 *            加密后
	 * @return
	 */
	User login(String username, String password);

	/**
	 * 用户登陆:客户端直接登陆浏览器使用
	 * 
	 * @param uuid
	 * @return
	 */
	User login(String uuid);

	/**
	 * 验证密码有效性
	 * 
	 * @param userId
	 * @return
	 */
	public boolean checkPwd(int userId, String pwd);

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	int register(User user);

	/**
	 * 检测用户名
	 * 
	 * @param username
	 * @return
	 */
	boolean checkUsername(String username);
	
	/**
	 * 检测用户名
	 * 
	 * @param username
	 * @return
	 */
	boolean checkUsernameByUserId(int userId,String username);

	/**
	 * 检测用户邮箱
	 * 
	 * @param username
	 * @return
	 */
	boolean checkUserEmail(String email);
	
	/**
	 * 检测用户邮箱
	 * 
	 * @param username
	 * @return
	 */
	boolean checkUserEmailByUserId(int userId,String email);

	/**
	 * 更新主题
	 * 
	 * @param theme
	 * @param userId
	 */
	boolean updateTheme(String theme, int userId);

	/**
	 * 初始化数据
	 * 
	 * @param userId
	 */
	void initData(int userId);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	boolean changePassword(int userId, String newPwd);

	/**
	 * 数据、项目分享时获取非本人用户列表
	 * 
	 * @return
	 */
	public List<User> getUserList(int userId);

	/**
	 * 根据用户编号获取用户邮箱
	 * 
	 * @param userId
	 * @return
	 */
	public String getEmailByUserId(int userId);

	/**
	 * 根据用户名获取用户邮箱
	 * 
	 * @param userId
	 * @return
	 */
	public String getEmailByUserName(String userName);

	/**
	 * 项目分享获取没有被分享该项目的用户列表
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	List<User> getUserListNotSharedPro(int userId, int projectId);

	/**
	 * 项目分享中获取已经分享了该项目的用户列表
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	List<User> getUserListSharedPro(int userId, int projectId);

	/**
	 * 根据用户编号获取用户角色
	 * 
	 * @param userId
	 * @return
	 */
	int getRoleByUserId(int userId);

	/**
	 * 根据用户姓名获取用户编号
	 * 
	 * @param userName
	 * @return
	 */
	int getUserIdByName(String userName);

	/**
	 * 后台添加数据时获取全部用户
	 * 
	 * @return
	 */
	List<User> getAllUserList();

	/**
	 * 获取所有用户信息分页列表
	 * 
	 * @return
	 */
	PageList<User> getAllUserPageList(Page page, String userName);

	/**
	 * 获取用户的数量
	 * 
	 * @return
	 */
	int countUser(String userName);

	/**
	 * 添加用户信息
	 * 
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	int updateUser(User user);

	/**
	 * 根据用户编号获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(int userId);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	boolean resetPwd(int userId);

	/**
	 * 判断用户输入邮箱是否存在
	 * 
	 * @param email
	 * @return 返回用户名
	 */
	String getUserNameByEmail(String email);

	/**
	 * 根据用户名插入随机验证码
	 * 
	 * @param userName
	 * @return
	 */
	public boolean insertFindPwdInfo(int userId, String md5);

	/**
	 * 根据邮箱插入随机验证码
	 * 
	 * @param email
	 * @param md5
	 * @return
	 */
	public boolean insertFindPwdInfo(String email, String md5);

	/**
	 * 根据邮箱和验证码来判断请求是否有效（用于注册）
	 * 
	 * @param email
	 * @param md5
	 * @return
	 */
	public boolean getValidate(String email, String md5);

	/**
	 * 删除验证码
	 * 
	 * @param code
	 */
	public void deleteValidate(String email);

	/**
	 * 验证用户验证码是否有效，用于找回密码
	 * 
	 * @param userName
	 * @param code
	 * @return
	 */
	public boolean validateUserByNameCode(String userName, String code);

	/**
	 * 根据用户名、验证码获取点击链接找回密码的开始时间
	 * 
	 * @param userName
	 * @param code
	 * @return
	 */
	public Date getExpireDateByUserIdCode(int userId, String code);

	/**
	 * 修改用户信息- 用户信息维护
	 * 
	 * @param user
	 * @return
	 */
	int updateUserByUserName(User user);
	
	/**
	 * 根据用户编号获取用户邮箱
	 * @param userId
	 * @return
	 */
	public String getEmailBySessionUserId(int userId);
	
	/**
	 * 根据用户编号修改密码找回链接的失效时间
	 * @param userId
	 * @return
	 */
	public int updateExpireDateByUserId(int userId);
	
	/**
	 * 根据用户编号删除用户
	 * @param userId
	 * @return
	 */
	public int deleteUserByUserId(int userId);
	
	public boolean updateNotify(int userId,int notify);
}
