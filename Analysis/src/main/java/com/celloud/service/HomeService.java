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

	public Map<String, Object> companyPreVIew(Integer cmpId, Integer role);
}
