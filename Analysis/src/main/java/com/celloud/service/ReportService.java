package com.celloud.service;

import com.celloud.service.impl.ReportServiceImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(ReportServiceImpl.class)
public interface ReportService {

	/**
	 * 获取大客户的运行次数
	 * 
	 * @param companyId
	 * @return
	 */
	public Object getBigUserRunNum(Integer companyId);

}
