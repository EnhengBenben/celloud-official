package com.celloud.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.CompanyDaoImpl;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.celloud.sdo.LoginLog;
import com.celloud.sdo.App;
import com.google.inject.ImplementedBy;

@ImplementedBy(CompanyDaoImpl.class)
public interface CompanyDao {
	/**
	 * 获取大客户的总医院量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserCompanyNum(Integer companyId,int role);

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
	public List<Map<String, Object>> getCompanyNumEveryMonth(Integer companyId,Integer role);

	/**
	 * 获取医院详细信息
	 * 
	 * @param companyId
	 *            大客户id
	 * @return
	 */
	public List<Company> getCompanyDetailById(Integer companyId,Integer role);

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
	public List<Map<String, Object>> getProvince(Integer companyId,int role);
	
	/**
	 * 获取大客户下医院运行App的次数
	 * @param companyId
	 * @return
	 */
	public List<App> getCompanyRunAppNumByCId(Integer companyId,String groupBytag);
	
	/**
	 * 取大客户下所有医院上传文件个数
	 * @param companyId
	 * @return
	 */
	public List<DataFile> getCompanyUpLoadDataByCId(Integer companyId,String groupBytag);
	
	/**
	 * 查询时间段内医院在各周登陆的次数
	 * @param userId
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<LoginLog> getCompanyLoginInWeek(Integer cmpId,Date start,Date end,List<Integer> cmpIdList,Integer role) ;
	/**
	 * 查询时间段内医院在各个月内登陆的次数
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<LoginLog> getCompanyLoginInMonth(Integer cmpId,Date start,Date end,List<Integer> companyList,Integer role) ;
	/**
	 * 查询时间段医院在各个周上传文件大小、数量
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<DataFile> getCompanyFileInWeek(Integer cmpId,Date start ,Date end,List<Integer> cmpIdList,Integer role );
	/**
	 * 查询时间段医院在各个月上传文件大小、数量
	 * @param userId
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<DataFile> getCompanyFileInMonth(Integer cmpId,Date start ,Date end,List<Integer> cmpIdList,Integer role );
	/**
	 * 查询时间段内医院在各个周内运行app的次数
	 * @param userId
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<App> getCompanySoftwareInWeek(Integer cmpId,Date start,Date end,List<Integer> cmpIdList,Integer role);
	/**
	 * 查询时间段内医院在各个月内运行app的次数
	 * @param userId
	 * @param start
	 * @param end
	 * @param companyList 仅看此医院
	 * @return
	 */
	public List<App> getCompanySoftwareInMonth(Integer cmpId,Date start,Date end,List<Integer> cmpIdList,Integer role);

	/**
	 * 管好权限查询客户权限
	 * @param cmpId
	 * @param role
	 * @return
	 */
	public List<Company> getCompanyClient(Integer cmpId,Integer role);}
