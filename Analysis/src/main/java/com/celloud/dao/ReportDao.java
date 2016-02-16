package com.celloud.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.ReportDaoImpl;
import com.celloud.sdo.Company;
import com.google.inject.ImplementedBy;

@ImplementedBy(ReportDaoImpl.class)
public interface ReportDao {
	/**
	 * 获取大客户的报告总量
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserReportNum(Connection conn, Integer companyId, int role);

	/**
	 * 获取大客户的运行次数
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserRunNum(Integer companyId);

	/**
	 * 查询医院的各APP运行次数
	 * 
	 * @param role
	 * @param cmpId
	 * @return
	 */
	public List<Map<String, Object>> getCompanyRunEachApp(Connection conn, Integer role, Integer cmpId);

	public List<Company> getCompanyRunEachApp_Company(Connection conn, Integer role, Integer cmpId);

}
