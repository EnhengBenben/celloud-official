package com.mongo.service;

import java.util.List;

import com.mongo.core.SystemContext;
import com.mongo.dao.ReportDAO;
import com.mongo.sdo.CmpFilling;
import com.mongo.sdo.CmpGeneSnpResult;
import com.mongo.sdo.CmpReport;
import com.mongo.sdo.GddDiseaseDict;
import com.mongo.sdo.MIB;
import com.mongo.sdo.NIPT;
import com.mongo.sdo.PGSFilling;
import com.mongo.sdo.Pgs;
import com.mongo.sdo.Split;

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
    public CmpReport getCmpReport(String dataKey, Integer userId, Integer appId) {
        return reportDao.getCmpReport(dataKey, userId, appId);
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer userId, Integer appId) {
        return reportDao.getSimpleCmp(dataKey, userId, appId);
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

    @Override
    public List<CmpGeneSnpResult> getGddResult(String dataKey, Integer proId,
            Integer appId) {
        return reportDao.getGddResult(dataKey, proId, appId);
    }

    @Override
    public NIPT getNIPTReport(String dataKey, Integer proId, Integer appId) {
        return reportDao.getNIPTReport(dataKey, proId, appId);
    }

    @Override
    public List<GddDiseaseDict> getGddDiseaseDictNormal(List<String> normalGene) {
        return reportDao.getGddDiseaseDictNormal(normalGene);
    }

    @Override
    public Split getSplit(String dataKey, Integer proId, Integer appId) {
        return reportDao.getSplit(dataKey, proId, appId);
    }

    @Override
    public void editSplit(Split split) {
        reportDao.editSplit(split);
    }

    @Override
    public MIB getMIB(String dataKey, Integer proId, Integer appId) {
        return reportDao.getMIB(dataKey, proId, appId);
    }

}
