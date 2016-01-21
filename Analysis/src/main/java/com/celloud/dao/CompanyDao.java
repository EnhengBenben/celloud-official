package com.celloud.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.CompanyDaoImpl;
import com.celloud.sdo.App;
import com.celloud.sdo.Company;
import com.celloud.sdo.DataFile;
import com.google.inject.ImplementedBy;

@ImplementedBy(CompanyDaoImpl.class)
public interface CompanyDao {
	/**
	 * 获取大客户的总医院量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserCompanyNum(Connection conn, Integer companyId, int role);

	/**
	 * 根据医院地址获取大客户各省的医院数量
	 * 
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> getBigUserCompanyNumByAddr(Integer companyId);

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
	 * 取大客户下所有医院上传文件个数
	 * 
	 * @param companyId
	 * @return
	 */
	public List<DataFile> getCompanyUpLoadDataByCId(Integer companyId, String groupBytag);

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
	public List<Company> BigUserList(Connection conn);

	/**
	 * 查询医院时间内的排序，取前N条记录
	 * 
	 * @param role
	 * @param cmpId
	 * @param start
	 * @param end
	 * @param topN
	 * @return
	 */
	public List<DataFile> getCompanyFileNum(Connection conn, int role, int cmpId, Date start, Date end, int topN);

	/**
	 * 查询医院时间内的排序，取前N条记录
	 * 
	 * @param role
	 * @param cmpId
	 * @param start
	 * @param end
	 * @param topN
	 * @return
	 */
	public List<DataFile> getCompanyFileSize(Connection conn, int role, int cmpId, Date start, Date end, int topN);

	/**
	 * 按省查询相关医院
	 * 
	 * @param role
	 * @param cmpId
	 * @param province
	 * @return
	 */
	public List<Map<String, Object>> getCompanys(Integer role, Integer cmpId, String province);

	/**
	 * 查询大客户每月新增的医院数量
	 * 
	 * @return
	 */
	public List<Company> getBigUserCmpNum(Connection conn);
}
