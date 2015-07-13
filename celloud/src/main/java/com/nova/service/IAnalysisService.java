package com.nova.service;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.nova.service.impl.AnalysisServiceImpl;

@ImplementedBy(AnalysisServiceImpl.class)
public interface IAnalysisService {
	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	List<Map<String, String>> getUserList(Integer companyId);

	/**
	 * 所有用户每个月上传的数据总个数
	 * 
	 * @return
	 */
	List<Map<String, String>> getUserDataList(Integer companyId);

	/**
	 * 某个用户每个月的数据个数
	 * 
	 * @return
	 */
	List<Map<String, String>> getUserData(Integer userId,Integer companyId);

	/**
	 * 获取某个用户某个月的数据列表
	 * 
	 * @return
	 */
	List<Map<String, String>> getDataList(Integer userId, String mounth,Integer companyId);
}