package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.celloud.sdo.*;
import com.celloud.service.impl.DataServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(DataServiceImpl.class)
public interface DataService {

	/**
	 * 获取大客户的所有用户及总上传数据量
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getUserList(Integer companyId, Integer role);

	/**
	 * 获取大客户的所有用户每月上传的数据量
	 * 
	 * @return
	 */
	public List<Data> getUserMonthDataList(Integer companyId, Integer role);

	/**
	 * 某个用户每个月的数据量
	 * 
	 * @return
	 */
	public List<Data> getUserMonthData(Integer userId, Integer companyId);

	/**
	 * 某个用户每个月的数据量
	 * 
	 * @return
	 */
	public List<Data> getUserMonthDataJson(Integer userId, Integer companyId);

	/**
	 * 获取某个用户某个月的数据列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonthDataList(Integer userId, String month, Integer companyId);

	/**
	 * 获取某月每个用户上传了
	 * 
	 * @param company
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId, String month);

	/**
	 * 获取大客户的总数据量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserDataNum(Integer companyId,int role);

	/**
	 * 获取大客户的数据总大小
	 * 
	 * @param companyId
	 * @return 数据大小 单位：b
	 */
	public Double getBigUserDataSize(Integer companyId,int role);

	/**
	 * 获取用户在一个时间段内上传的文件及运行状态
	 * 
	 * @param userIds
	 *            ：一个或多个用户ID，用，间隔
	 * @param start
	 *            ：开始时间
	 * @param end
	 *            ：结束时间
	 * @return
	 */
	public List<Map<String, Object>> getUserFileRunState(String userIds, String start, String end);

	/**
	 * 统计周内各用户上传文件大小、数量
	 * @param start开始时间
	 * @return
	 */
	public List<Data> getUserWeekData(Date start);
	
	/**
	 * 统计周内每天上传的数据大小
	 * @param start开始时间
	 * @return
	 */
	public List<Data> getEachDayData(Date start);
}
