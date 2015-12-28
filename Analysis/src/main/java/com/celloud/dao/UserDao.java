package com.celloud.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.UserDaoImpl;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.Entry;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.sdo.TotalCount;
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
	public Object getBigUsersUserNum(Integer companyId,int role);

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
	public List<User> getUserListByBigCom(Integer companyId,Integer role,String orderType);

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
	 * @param userId
	 * @return
	 */
	public List<App> getAppRunTimesByUId(Integer userId);

	/**
	 * 按月统计用户上App运行次数
	 * 
	 * @param uesrId
	 * @return
	 */
	public List<Entry> getAppRunEachMonthByUId(Integer userId);

	/**
	 * 按月统计用户上App运行次数
	 * 
	 * @param uesrId
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
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DataFile>getFileMonthInDate(Integer cmpId, Integer role,List<Integer> uids, Date start,Date end);
	/**
	 * 获取时间内文件大小、数量
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<DataFile>getFileInWeekDate(Integer cmpId, Integer role,List<Integer> uids, Date start,Date end);
	/**
	 * 按周分组 取用户在单位时间内运行app次数
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<App> getAppRunInWeek(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
	/**
	 * 按月分组 取用户在单位时间内运行app次数
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<App> getAppRunInMonth(Integer cmpId, Integer role,List<Integer> uids,Date start,Date end);
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
	public List<DataFile> getUserDataTop(String type, int topN, Date start, Date end);
	
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
