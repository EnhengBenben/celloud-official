package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.sdo.DataFile;
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
	public List<Map<String, Object>> getUserList(Integer companyId, Integer role, String orderType);

	/**
	 * 获取大客户的所有用户每月上传的数据量
	 * 
	 * @return
	 */
	public Map<String, Object> getPreDataView(Integer companyId, Integer role);

	public List<DataFile> getUserMonthDataList(Integer companyId, Integer role);

	/**
	 * 获某大客户的各月的数据与文件大小
	 * 
	 * @return
	 */
	public List<DataFile> getBigUserDataFile(Integer companyId);

	/**
	 * 某个用户每个月的数据量
	 * 
	 * @return
	 */
	public List<DataFile> getUserMonthData(Integer userId, Integer companyId);

	/**
	 * 某个用户每个月的数据量
	 * 
	 * @return
	 */
	public List<DataFile> getUserMonthDataJson(Integer userId, Integer companyId);

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
	 * 只查询大客户的数据
	 * 
	 * @return
	 */
	public List<DataFile> getBigUserData();

	/**
	 * 查询各大客户各月的数据量
	 * 
	 * @return
	 */
	public Map<String, Object> getAllBigUserMonthData();
}
