/**  */
package com.celloud.mongo.service;

import java.util.List;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpGeneSnpResult;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;
import com.celloud.mongo.sdo.NIPT;
import com.celloud.mongo.sdo.PGSFilling;
import com.celloud.mongo.sdo.Pgs;
import com.google.inject.ImplementedBy;

/**
 * 报告服务接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:33:19
 * @version Revision: 1.0
 */
@ImplementedBy(ReportServiceImpl.class)
public interface ReportService {
    /**
     * 新增CMP运行报告内容
     * 
     * @param cmpReport
     */
    public void saveCmpReport(CmpReport cmpReport);

    /**
     * 修改cmp用户填写部分内容
     * 
     * @param cmpFill
     */
    public void editCmpFilling(Object id, CmpFilling cmpFill);

    /**
     * 获取CMP报告内容
     * 
     * @param dataKey
     * @param userId
     * @return
     */
    public CmpReport getCmpReport(String dataKey, Integer userId, Integer appId);

    /**
     * 获取CMP简要信息内容
     * 
     * @param dataKey
     * @param userId
     * @return
     */
    public CmpReport getSimpleCmp(String dataKey, Integer userId, Integer appId);

    /**
     * 获取GDD总表检测突变数统计
     * 
     * @param dataKey
     * @param proId
     * @param appId
     * @return
     */
    public List<CmpGeneSnpResult> getGddResult(String dataKey, Integer proId,
	    Integer appId);

    /**
     * 获取pgs报告内容
     * 
     * @param dataKey
     * @param proId
     * @return
     */
    public Pgs getPgsReport(String dataKey, Integer proId, Integer appId);

    /**
     * 获取pgs统计报告内容
     * 
     * @param userId
     * @return
     */
    public abstract List<Pgs> getPgsList(Integer userId);

    /**
     * 
     * 修改PGS用户填写部分内容
     * 
     * @param userId
     * @param appId
     * @param dataKey
     * @param pgs
     */
    public void editPGSFilling(int userId, int appId, String dataKey,
	    PGSFilling pgs);

    /**
     * 获取NIPT数据报告
     * 
     * @param dataKey
     * @param proId
     * @param appId
     * @return
     */
    public NIPT getNIPTReport(String dataKey, Integer proId, Integer appId);

    /**
     * 获取GDD未检测到的疾病
     * 
     * @param normalGene
     * @return
     */
    public List<GddDiseaseDict> getGddDiseaseDictNormal(List<String> normalGene);
}
