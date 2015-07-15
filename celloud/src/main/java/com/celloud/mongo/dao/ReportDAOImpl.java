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
	public void saveCmpFilling(CmpFilling cmpFill) {
		ds.save(cmpFill);
	}

	@Override
	public CmpReport getOneCmpReport(Object id) {
		return ds.createQuery(CmpReport.class).filter("_id", id).get();
	}

	@Override
	public CmpFilling getOneCmpFilling(Object id) {
		return ds.createQuery(CmpFilling.class).filter("_id", id).get();
	}

}
