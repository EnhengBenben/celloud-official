package com.celloud.mongo.dao;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

/**
 * 操作Report实现类
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-10上午10:41:34
 * @version Revision: 1.0
 */
public class ReportDAOImpl extends BasicDAO<CmpReport, String> implements
		ReportDAO {
	public ReportDAOImpl(Mongo mongo, Morphia morphia, String dbName) {
		super(mongo, morphia, dbName);
	}

	@Override
	public void saveCmpReport(CmpReport cmpReport) {
		ds.save(cmpReport);
	}

	@Override
	public void editCmpFilling(Object id, CmpFilling cmpFill) {
		ds.update(
				ds.createQuery(CmpReport.class).filter("_id", id),
				ds.createUpdateOperations(CmpReport.class).set("cmpFilling",
						cmpFill));
	}

	@Override
	public CmpReport getCmpReport(String dataKey, String userId) {
		return ds.createQuery(CmpReport.class).filter("dataKey", dataKey)
				.filter("userId", userId).get();
	}

	@Override
	public void deleteCmpReport(String dataKey, String userId) {
		ds.delete(ds.find(CmpReport.class).filter("dataKey", dataKey)
				.filter("userId", userId));
	}

	@Override
	public CmpReport getSimpleCmp(String dataKey, String userId) {
		return null;
	}

}
