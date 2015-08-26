package com.celloud.mongo.service;

import java.util.List;

import com.celloud.mongo.core.SystemContext;
import com.celloud.mongo.dao.ReportDAO;
import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.PGSFilling;
import com.celloud.mongo.sdo.Pgs;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:40:46
 * @version Revision: 1.0
 */
public class ReportServiceImpl implements ReportService {
    ReportDAO reportDao = SystemContext.getReportDAO();

    @Override
    public void saveCmpReport(CmpReport cmpReport) {
	reportDao.saveCmpReport(cmpReport);
    }

    @Override
    public void editCmpFilling(Object id, CmpFilling cmpFill) {
	reportDao.editCmpFilling(id, cmpFill);
    }

    @Override
    public CmpReport getCmpReport(String dataKey, Integer userId) {
	return reportDao.getCmpReport(dataKey, userId);
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer userId) {
	return reportDao.getSimpleCmp(dataKey, userId);
    }

    @Override
    public Pgs getPgsReport(String dataKey, Integer proId, Integer appId) {
	return reportDao.getPgsReport(dataKey, proId, appId);
    }

    @Override
    public List<Pgs> getPgsList(Integer userId) {
	return this.reportDao.getPgsList(userId);
    }

    @Override
    public void editPGSFilling(int userId, int appId, String dataKey,
	    PGSFilling pgs) {
	reportDao.editPGSFilling(userId, appId, dataKey, pgs);
    }

}
