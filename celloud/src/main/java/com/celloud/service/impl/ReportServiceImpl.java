package com.celloud.service.impl;


import com.celloud.dao.ReportDao;
import com.celloud.sdo.Report;
import com.celloud.service.ReportService;
import com.google.inject.Inject;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-24下午3:06:27
 * @version Revision: 1.0
 */
public class ReportServiceImpl implements ReportService {
    @Inject
    ReportDao reportDao;
    @Override
    public Long insertProReport(Report report) {
	return reportDao.insertProReport(report);
    }
}
