package com.celloud.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.celloud.service.impl.AppServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(AppServiceImpl.class)
public interface AppService {
	/**
	 * 获取大客户的APP总量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserAPPNum(Integer companyId,int role);

	/**
	 * 获取大客户的APP列表
	 * 
	 * @param company
	 * @return
	 */
	public List<App> getAppListByBigUser(Integer companyId,Integer role);

	/**
	 * 根据软件id获取软件信息
	 * 
	 * @param appId
	 * @return
	 */
	public App getAppById(Integer appId);
	
	/**
	 * 统计时间段内各周各App的运行次数
	 * @param userId  用户Id 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public List<App> getAppRunTimeInWeek(Integer cmpId,Integer userId,Date start,Date end,Integer role,String softwareId);
	/**
	 * 统计时间段内各月各App的运行次数
	 * @param userId  用户Id 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public List<App> getAppRunTimeInMonth(Integer cmpId,Integer userId,Date start,Date end,Integer role,String softwareId);
	
	/**
	 * 查询app
	 * @param cmpId
	 * @param role
	 * @return
	 */
	public List<App> getAppByCompanyId(Integer cmpId,Integer role);
	
	/**
	 * 查询app运行排行榜
	 * @param type 分组单位 月、周
	 * @param topN
	 * @param start
	 * @param end
	 * @return
	 */
	public List<App> getAppRunTop(String type,int topN,Date start,Date end);
	
	/**
	 * 统计用户运行APP次数排序。
	 * @param topN
	 * @return
	 */
	public List<App> getTotalUserRunNum(int topN);
	/**
	 * 统计APP运行次数
	 * @param topN
	 * @return
	 */
	public List<App> getTotalAppRunNum(int topN);

	/**
	 * 总的用户登陆排序
	 * @param topN
	 * @return
	 */
	public List<LoginLog> getTotalUserLogin(int topN);
	/**
	 * 统计各浏览器
	 * @return
	 */
	public List<LoginLog> getBrowerCount();
	/**
	 *  统计周
	 * @param start
	 * @return
	 */
	public List<App> getAppRunNumCount(Date start);

	/**
	 *  统计周里每个用户运行APP次数
	 * @param start
	 * @return
	 */
	public List<App> getAppUserCount(Date start);
	
	/**
	 * 取所有大客户的APP数量
	 * @return
	 */
	public List<App> getBigUserAppList();
	/**
	 * 根据APP查询每月运行次数
	 * @param app_id
	 * @return
	 */
	public List<App> getAppRun(int app_id);
	
	/**
	 * 根据大客户ID取APP列表
	 * @param cmpId
	 * @return
	 */
	public List<App> getAppListByBigUserId(int cmpId);
	
	/**
	 * 查询时间里APP运行次数
	 * @param conn
	 * @param role
	 * @param cmpId
	 * @param start
	 * @param end
	 * @param topN
	 * @return
	 */
	public List<App> getAppList(Integer role, Integer cmpId, Date start, Date end, Integer topN);
}
