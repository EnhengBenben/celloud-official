package com.celloud.dao;

import java.sql.Connection;

import com.celloud.dao.impl.ReportDaoImpl;
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

}
