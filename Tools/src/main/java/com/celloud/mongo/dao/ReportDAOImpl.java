package com.celloud.mongo.dao;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;
import com.celloud.mongo.sdo.GddGeneticMethod;
import com.celloud.mongo.sdo.MIB;
import com.celloud.mongo.sdo.Split;
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
    public CmpReport getCmpReport(String dataKey, Integer userId) {
        return ds.createQuery(CmpReport.class).filter("dataKey", dataKey)
                .filter("userId", userId).get();
    }

    @Override
    public void deleteCmpReport(String dataKey, Integer userId) {
        ds.delete(ds.find(CmpReport.class).filter("dataKey", dataKey)
                .filter("userId", userId));
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer userId) {
        return ds
                .createQuery(CmpReport.class)
                .retrievedFields(false, "createDate", "company", "user",
                        "geneDetectionDetail", "cmpFilling").get();
    }

    @Override
    public void saveGddDiseaseDict(GddDiseaseDict gddDisease) {
        ds.save(gddDisease);
    }

    @Override
    public GddDiseaseDict getGddDiseaseDict(String name) {
        return ds.createQuery(GddDiseaseDict.class).filter("engName", name)
                .get();
    }

    @Override
    public void saveGddGeneticMethod(GddGeneticMethod geneticMethod) {
        ds.save(geneticMethod);
    }

    @Override
    public GddGeneticMethod getGddGeneticMethod(String gene) {
        return ds.createQuery(GddGeneticMethod.class).filter("gene", gene)
                .get();
    }

    @Override
    public void saveSplit(Split split) {
        ds.save(split);
    }

    @Override
    public void saveMIB(MIB mib) {
        ds.save(mib);
    }

}
