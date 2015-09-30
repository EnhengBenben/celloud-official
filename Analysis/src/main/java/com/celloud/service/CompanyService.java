package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.sdo.Company;
import com.celloud.service.impl.CompanyServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {
	/**
	 * 获取大客户的总医院量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserCompanyNum(Integer companyId);

	/**
	 * 获取大客户每月的医院数量
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, Object> getCompanyNumEveryMonth(Integer companyId);

	/**
	 * 获取医院详细信息
	 * 
	 * @param companyId
	 *            大客户id
	 * @return
	 */
	public List<Company> getCompanyDetailById(Integer companyId);

	/**
	 * 获取单个医院信息
	 * 
	 * @param compId
	 * @return
	 */
	public Company getCompanyById(Integer compId);

	/**
	 * 获取平台医院分布
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getProvince();
}
