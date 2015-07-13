package com.celloud.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.DataDaoImpl;
import com.celloud.sdo.Data;
import com.google.inject.ImplementedBy;

@ImplementedBy(DataDaoImpl.class)
public interface DataDao {
	/**
	 * 总数据量
	 * 
	 * @return
	 */
	public int dataCount(Date beginDate, Date endDate);

	/**
	 * 在指定时间内每个用户上传的数据量
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Data> dataCountEveryUser(Date beginDate, Date endDate);

	/**
	 * 在指定时间内每个用户上传的数据列表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Data> clientDataCountEveryUser(Date beginDate, Date endDate);

	/**
	 * 获取大客户的所有用户及总上传数据列表
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getUserList(Integer companyId);

	/**
	 * 获取大客户的所有用户每月上传的数据列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getUserMonthDataList(Integer companyId);

	/**
	 * 某个用户每个月的数据量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getUserMonthData(Integer userId,
			Integer companyId);

	/**
	 * 获取某个用户某个月的数据列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonthDataList(Integer userId,
			String month, Integer companyId);

	/**
	 * 获取某月每个用户上传了
	 * 
	 * @param company
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getAllUserDataInMonth(Integer companyId,
			String month);

	/**
	 * 获取大客户的总数据量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserDataNum(Integer companyId);

	/**
	 * 获取大客户的数据总大小
	 * 
	 * @param companyId
	 * @return 数据大小 单位：b
	 */
	public Object getBigUserDataSize(Integer companyId);
	
	/**
	 * 获取用户在一个时间段内上传的文件及运行状态
	 * 
	 * @param userIds：一个或多个用户ID，用，间隔
	 * @param start：开始时间
	 * @param end：结束时间
	 * @return
	 */
	public List<Map<String, Object>> getUserFileRunState(String userIds, String start, String end);
}
