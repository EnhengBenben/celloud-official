package com.celloud.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.UserDaoImpl;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
import com.google.inject.ImplementedBy;

@ImplementedBy(UserDaoImpl.class)
public interface UserDao {

	/**
	 * 指定时间内每个用户登录次数，倒序排序
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List<LoginLog>
	 */
	public List<LoginLog> logCountEveryUser(Date beginDate, Date endDate);
	
	/**
	 * 指定时间内每天的登录次数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<LoginLog> logCountEveryDay(Date beginDate, Date endDate);
	
	/**
	 * 指定时间内所有浏览器的登录次数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<LoginLog> logCountEveryBrowser(Date beginDate, Date endDate);

	/**
	 * 总用户数量统计
	 * 
	 * @return Integer
	 */
	public Integer userCount();

	/**
	 * 指定时间内新增的用户
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Integer userAddBetweenDate(Date beginDate, Date endDate);

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);

	/**
	 * 获取大客户的总数据量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUsersUserNum(Integer companyId);
	
	/**
	 * 获取大客户的所有客户
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getBigUsersUser(Integer companyId);

	/**
	 * 获取大客户的用户信息列表
	 * 
	 * @param companyId
	 * @return
	 */
	public List<User> getUserListByBigCom(Integer companyId);

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId);
}
