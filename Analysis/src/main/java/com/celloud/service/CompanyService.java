package com.celloud.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.service.impl.CompanyServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {

	/**
	 * 获取大客户每月的医院数量
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId, Integer role);

	/**
	 * 获取医院详细信息
	 * 
	 * @param companyId
	 *            大客户id
	 * @return
	 */
	public List<Company> getCompanyDetailById(Integer companyId, Integer role, String orderBy);

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
	public List<Map<String, Object>> getProvince(Integer companyId, int role);

	/**
	 * 获取大客户下医院运行App的次数
	 * 
	 * @param companyId
	 * @return
	 */
	public List<App> getCompanyRunAppNumByCId(Integer companyId);

	/**
	 * 获取大客户下医院运行App的次数按月分组
	 * 
	 * @param companyId
	 * @return
	 */
	public List<App> getCompanyRunAppNumGroupByMonth(Integer companyId);

	/**
	 * 获取大客户下医院运行App的次数按周分组
	 * 
	 * @param companyId
	 * @return
	 */
	public List<App> getCompanyRunAppNumGroupByWeek(Integer companyId);

	/**
	 * 取大客户下所有医院上传文件个数按月统计
	 * 
	 * @param companyId
	 * @return
	 */
	public List<DataFile> getCompanyUpLoadGroupMonthByCId(Integer companyId);

	/**
	 * 取大客户下所有医院上传文件个数按周统计
	 * 
	 * @param companyId
	 * @return
	 */
	public List<DataFile> getCompanyUpLoadGroupWeekByCId(Integer companyId);

	/**
	 * 管好权限查询客户权限
	 * 
	 * @param cmpId
	 * @param role
	 * @return
	 */
	public List<Company> getCompanyClient(Integer cmpId, Integer role);

	/**
	 * 大客户详细信息列表列表
	 * 
	 * @return
	 */
	public List<Company> BigUserList();

	/**
	 * 医院活跃度统计
	 * 
	 * @param role
	 * @param cmpId
	 * @param start
	 * @param end
	 * @param topN
	 * @return
	 */
	public Map<String, Object> getCompanyFile(int role, int cmpId, Date start, Date end, int topN);

	public Map<String, List> getList(int role, int cmpId, Date start, Date end, int topN);

	/**
	 * 按省查询相关医院
	 * 
	 * @param role
	 * @param cmpId
	 * @param province
	 * @return
	 */
	public List<Map<String, Object>> getCompanys(Integer role, Integer cmpId, String province);

}
