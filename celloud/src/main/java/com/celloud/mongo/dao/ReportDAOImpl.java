package com.celloud.mongo.dao;

import java.util.List;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.PGSFilling;
import com.celloud.mongo.sdo.Pgs;
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
	public CmpReport getCmpReport(String dataKey, Integer proId) {
		return ds.createQuery(CmpReport.class).filter("dataKey", dataKey)
				.filter("projectId", proId).get();
	}

	@Override
	public CmpReport getSimpleCmp(String dataKey, Integer proId) {
		return ds
				.createQuery(CmpReport.class)
				.retrievedFields(false, "createDate",
						"geneDetectionDetail", "cmpFilling")
				.filter("dataKey", dataKey).filter("projectId", proId).get();
	}

	@Override
	public Pgs getPgsReport(String dataKey, Integer proId, Integer appId) {
		return ds.createQuery(Pgs.class).filter("dataKey", dataKey)
				.filter("projectId", proId).filter("appId", appId).get();
	}

	@Override
	public List<Pgs> getPgsList(Integer userId) {
		return this.ds.createQuery(Pgs.class).filter("userId", userId)
				.order("-uploadDate").asList();
	}

	@Override
	public void editPGSFilling(int userId, int appId, int dataKey,
			PGSFilling pgs) {
		ds.update(
				ds.createQuery(Pgs.class).filter("userId", userId).filter("appId", appId).filter("dataKey", dataKey),
				ds.createUpdateOperations(Pgs.class).set("fill", pgs));
	}
}
