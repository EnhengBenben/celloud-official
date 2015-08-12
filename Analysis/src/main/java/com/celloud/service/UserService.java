package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.sdo.LoginLog;
import com.celloud.sdo.User;
import com.celloud.service.impl.UserServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(UserServiceImpl.class)
public interface UserService {
	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(User user);

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
	 * 获取大客户的总数据量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUsersUserNum(Integer companyId);
	
	/**
	 * 获取大大客户的所有客户
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
}
