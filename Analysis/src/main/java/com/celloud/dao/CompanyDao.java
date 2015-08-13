package com.celloud.dao;

import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.CompanyDaoImpl;
import com.celloud.sdo.Company;
import com.google.inject.ImplementedBy;

@ImplementedBy(CompanyDaoImpl.class)
public interface CompanyDao {
	/**
	 * 获取大客户的总医院量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserCompanyNum(Integer companyId);

	/**
	 * 根据医院地址获取大客户各省的医院数量
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getBigUserCompanyNumByAddr(
			Integer companyId);

	/**
	 * 根据医院地址获取医院信息
	 * 
	 * @param addr
	 * @return
	 */
	public List<Company> getCompanyByAddr(String addr);

	/**
	 * 获取大客户每月的医院数量
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId);

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
}
