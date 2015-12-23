package com.celloud.dao;

import java.util.Date;
import java.util.List;

import com.celloud.dao.impl.SoftwareDaoImpl;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.Software;
import com.google.inject.ImplementedBy;

@ImplementedBy(SoftwareDaoImpl.class)
public interface SoftwareDao {
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
	public List<Software> getAppListByBigUser(Integer companyId,Integer role);

	/**
	 * 根据软件id获取软件信息
	 * 
	 * @param appId
	 * @return
	 */
	public Software getAppById(Integer appId);
	/**
	 * 统计时间段内各周各App的运行次数
	 * @param userId  用户Id 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public List<Software> getAppRunTimeInWeek(Integer cmpId,Integer userId,Date start,Date end,Integer role,String softwareId);
	/**
	 * 统计时间段内各月各App的运行次数
	 * @param userId  用户Id 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public List<Software> getAppRunTimeInMonth(Integer cmpId,Integer userId,Date start,Date end,Integer role,String softwareId);
	
	/**
	 * 查询app
	 * @param cmpId
	 * @param role
	 * @return
	 */
	public List<Software> getAppByCompanyId(Integer cmpId,Integer role);
	/**
	 * 查询app运行排行
	 * @param type
	 * @param topN
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Software> getAppRunTop(String type,int topN,Date start,Date end);

	/**
	 * 统计用户运行APP次数排序。
	 * @param topN
	 * @return
	 */
	public List<Software> getTotalUserRunNum(int topN);
	
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
	 *  统计周里每天
	 * @param start
	 * @return
	 */
	public List<Software> getAppRunNumCount(Date start);
	/**
	 *  统计周里每个用户运行APP次数
	 * @param start
	 * @return
	 */
	public List<Software> getAppUserCount(Date start);
	
	/**
	 * 统计APP运行次数
	 * @param topN
	 * @return
	 */
	public List<Software> getTotalAppRunNum(int topN);

}
