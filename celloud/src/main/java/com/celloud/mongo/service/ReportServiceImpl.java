package com.celloud.mongo.service;

import java.util.HashMap;
import java.util.Map;

import com.celloud.mongo.core.SystemContext;
import com.celloud.mongo.dao.ReportDAO;
import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;

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
	public void saveCmpFilling(CmpFilling cmpFill) {
		reportDao.saveCmpFilling(cmpFill);
	}

	@Override
	public Map<String, Object> getOneWholeCmpReport(String dataKey,
			String userId) {
		CmpReport report = reportDao.getOneCmpReport(dataKey, userId);
		CmpFilling fill = reportDao.getOneCmpFilling(dataKey, userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CmpReport", report);
		map.put("CmpFilling", fill);
		return map;
	}

	@Override
	public void deleteCmpFilling(Object id) {
		reportDao.deleteCmpFilling(id);
	}

}
