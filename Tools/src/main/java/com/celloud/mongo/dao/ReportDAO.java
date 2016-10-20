/**  */
package com.celloud.mongo.dao;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;
import com.celloud.mongo.sdo.GddGeneticMethod;
import com.celloud.mongo.sdo.MIB;
import com.celloud.mongo.sdo.Split;

/**
 * 操作Report接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-9下午4:23:35
 * @version Revision: 1.0
 */
public interface ReportDAO {
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
     * @param reportId
     *            mysql中的报告id
     * @return
     */
    public CmpReport getCmpReport(String dataKey, Integer userId);

    /**
     * 删除CMP报告内容
     * 
     * @param dataKey
     * @param userId
     */
    public void deleteCmpReport(String dataKey, Integer userId);

    /**
     * 获取CMP简要信息内容
     * 
     * @param dataKey
     * @param userId
     * @return
     */
    public CmpReport getSimpleCmp(String dataKey, Integer userId);

    /**
     * 保存GDD疾病信息字典库
     * 
     * @param gddDisease
     */
    public void saveGddDiseaseDict(GddDiseaseDict gddDisease);

    /**
     * 获取GDD疾病信息字典库
     * 
     * @param name
     * @return
     */
    public GddDiseaseDict getGddDiseaseDict(String name);

    /**
     * 保存GDD遗传方式字典库
     * 
     * @param geneticMethod
     */
    public void saveGddGeneticMethod(GddGeneticMethod geneticMethod);

    /**
     * 获取GDD遗传方式
     * 
     * @param gene
     * @return
     */
    public GddGeneticMethod getGddGeneticMethod(String gene);

    /**
     * 新增split运行报告内容
     * 
     * @param cmpReport
     */
    public void saveSplit(Split split);

    /**
     * 新增MIB运行报告内容
     * 
     * @param cmpReport
     */
    public void saveMIB(MIB mib);
}
