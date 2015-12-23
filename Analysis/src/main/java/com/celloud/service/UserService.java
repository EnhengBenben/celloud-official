package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.celloud.sdo.Data;
import com.celloud.sdo.Entry;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.celloud.sdo.TotalCount;
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
	public Object getBigUsersUserNum(Integer companyId,int role);
	
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
	public List<User> getUserListByBigCom(Integer companyId,Integer role);

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId);
	
	/**
	 * 根据用户Id查询用户日志
	 * @param userId
	 * @return
	 */
	public List<LoginLog> getLogById(String userId);
	/**
	 * 根据用户id查询用户运行各app次数
	 * @param userId
	 * @return
	 */
	public List<Software> getAppRunTimesByUId(Integer userId);
	
	/**
	 * 根据用户ID查询用户每月运行App的次数
	 * @param userId
	 * @return
	 */
	public List<Entry> getAppRunEachMonthByUId(Integer userId);
	
	/**
	 * 根据用户ID查询用户每周运行APP的次数
	 * @param userId
	 * @return
	 */
	public List<Entry> getAppRunEachWeekByUId(Integer userId);
	/**
	 * 按月统计用户上传文件
	 * @param uesrId
	 * @return
	 */
	public List<Data> getUploadFileMonth(Integer uesrId);
	/**
	 * 按周统计用户上传文件
	 * @param uesrId
	 * @return
	 */
	public List<Data> getUploadFileWeek(Integer userId);
	
	/**
	 * 获取月时间段登陆用户次数
	 * @param UserId
	 * @return
	 */
	public List<LoginLog>getLoginUserSortWeek(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	/**
	 * 获取周时间段登陆用户次数
	 * @param UserId
	 * @return
	 */
	public List<LoginLog>getLoginUserSortMonth(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	
	/**
	 * 获取时间内文件大小、数量
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Data>getFileMonthInDate(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	/**
	 * 获取时间内文件大小、数量
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Data>getFileInWeekDate(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	/**
	 * 按周分组 取用户在单位时间内运行app次数
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Software> getAppRunInWeek(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	/**
	 * 按月分组 取用户在单位时间内运行app次数
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Software> getAppRunInMonth(Integer cmpId,Integer role ,List<Integer> uids,Date start,Date end);
	/**
	 * 按权限取用户
	 * @param companyId
	 * @param role
	 * @return
	 */
	public List<User> getUserByCompany(Integer companyId,Integer role);
	/**
	 * 查询用户登陆前N的用户
	 * @param topN
	 * @return
	 */
	public List<LoginLog> getLoginTop(String type,int topN,Date start,Date end);
	/**
	 * 查询用户数据时排行
	 * @param type
	 * @param topN
	 * @param start
	 * @param end
	 * @return
	 */
			
	public List<Data> getUserDataTop(String type,int topN,Date start,Date end);
	
	/***
	 * 用户历史统计，与比较
	 * @return
	 */
	public List<TotalCount> getCountInHistory();
	
	/**
	 * 新用户的活跃度
	 * @param isWeek
	 * @return
	 */
	public List<LoginLog> getLoginLog(String isWeek);
}
