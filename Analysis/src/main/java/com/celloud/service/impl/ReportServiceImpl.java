package com.celloud.service.impl;

import com.celloud.dao.ReportDao;
import com.celloud.service.ReportService;
import com.google.inject.Inject;

public class ReportServiceImpl implements ReportService {
	@Inject
	private ReportDao reportDao;

	@Override
	public Object getBigUserReportNum(Integer companyId,int role) {
		return reportDao.getBigUserReportNum(companyId,role);
	}

	@Override
	public Object getBigUserRunNum(Integer companyId) {
		return reportDao.getBigUserRunNum(companyId);
	}
	
}
