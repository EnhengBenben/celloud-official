package com.celloud.mongo.service;

import com.celloud.mongo.core.SystemContext;
import com.celloud.mongo.dao.ReportDAO;
import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;
import com.celloud.mongo.sdo.GddGeneticMethod;
import com.celloud.mongo.sdo.MIB;
import com.celloud.mongo.sdo.Split;

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
    public void deleteCmpReport(String dataKey, Integer userId) {
        reportDao.deleteCmpReport(dataKey, userId);
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer userId) {
        return reportDao.getCmpReport(dataKey, userId);
    }

    @Override
    public void saveGddDiseaseDict(GddDiseaseDict gddDisease) {
        reportDao.saveGddDiseaseDict(gddDisease);
    }

    @Override
    public GddDiseaseDict getGddDiseaseDict(String name) {
        return reportDao.getGddDiseaseDict(name);
    }

    @Override
    public void saveGddGeneticMethod(GddGeneticMethod geneticMethod) {
        reportDao.saveGddGeneticMethod(geneticMethod);
    }

    @Override
    public GddGeneticMethod getGddGeneticMethod(String gene) {
        return reportDao.getGddGeneticMethod(gene);
    }

    @Override
    public void saveSplit(Split split) {
        reportDao.saveSplit(split);
    }

    @Override
    public void saveMIB(MIB mib) {
        reportDao.saveMIB(mib);
    }

}
