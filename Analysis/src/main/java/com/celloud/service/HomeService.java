package com.celloud.service;

import java.util.Map;

import com.celloud.service.impl.HomeServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(HomeServiceImpl.class)
public interface HomeService {
	/**
	 * 首页
	 * 
	 * @param cmpId
	 * @param role
	 * @return
	 */
	public Map<String, Object> toHome(Integer cmpId, Integer role);

	public Map<String, Object> toCompanyReport(Integer cmpId, Integer role);

	public Map<String, Object> companyPreView(Integer cmpId, Integer role);

	/**
	 * 查询各大客户各月的新增的医院数量
	 * 
	 * @return
	 */
	public Map<String, Object> getPreDataViewBigUesrNewCmp();
}