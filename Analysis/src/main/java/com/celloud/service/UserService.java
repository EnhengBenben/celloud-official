package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.sdo.App;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.Entry;
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
	public List<User> getUserListByBigCom(Integer companyId, Integer role, String orderType);

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId);

	/**
	 * 根据用户Id查询用户日志
	 * 
	 * @param userId
	 * @return
	 */
	public List<LoginLog> getLogById(String userId);

	/**
	 * 根据用户id查询用户运行各app次数
	 * 
	 * @param userId
	 * @return
	 */
	public List<App> getAppRunTimesByUId(Integer userId);

	/**
	 * 根据用户ID查询用户每月运行App的次数
	 * 
	 * @param userId
	 * @return
	 */
	public List<Entry> getAppRunEachMonthByUId(Integer userId);

	/**
	 * 根据用户ID查询用户每周运行APP的次数
	 * 
	 * @param userId
	 * @return
	 */
	public List<Entry> getAppRunEachWeekByUId(Integer userId);

	/**
	 * 按月统计用户上传文件
	 * 
	 * @param uesrId
	 * @return
	 */
	public List<DataFile> getUploadFileMonth(Integer uesrId);

	/**
	 * 按周统计用户上传文件
	 * 
	 * @param uesrId
	 * @return
	 */
	public List<DataFile> getUploadFileWeek(Integer userId);

	/**
	 * 获取月时间段登陆用户次数
	 * 
	 * @param UserId
	 * @return
	 */
	public List<LoginLog> getLoginUserSortWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 获取周时间段登陆用户次数
	 * 
	 * @param UserId
	 * @return
	 */
	public List<LoginLog> getLoginUserSortMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 获取时间内文件大小、数量
	 * 
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DataFile> getFileMonthInDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 获取时间内文件大小、数量
	 * 
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DataFile> getFileInWeekDate(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 按周分组 取用户在单位时间内运行app次数
	 * 
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<App> getAppRunInWeek(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 按月分组 取用户在单位时间内运行app次数
	 * 
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<App> getAppRunInMonth(Integer cmpId, Integer role, List<Integer> uids, Date start, Date end);

	/**
	 * 按权限取用户
	 * 
	 * @param companyId
	 * @param role
	 * @return
	 */
	public List<User> getUserByCompany(Integer companyId, Integer role);

	/**
	 * 用户活跃度统计
	 * 
	 * @param role
	 * @param cmpId
	 * @param start
	 * @param end
	 * @param topN
	 * @return
	 */
	public Map<String, Object> getUserActivity(int role, int cmpId, Date start, Date end, int topN);
}
