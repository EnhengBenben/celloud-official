package com.mongo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongo.sdo.CmpFilling;
import com.mongo.sdo.CmpGeneDetectionDetail;
import com.mongo.sdo.CmpGeneSnpResult;
import com.mongo.sdo.CmpReport;
import com.mongo.sdo.GddDiseaseDict;
import com.mongo.sdo.HBV;
import com.mongo.sdo.MIB;
import com.mongo.sdo.PGSFilling;
import com.mongo.sdo.Pgs;
import com.mongo.sdo.Split;
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
    public CmpReport getCmpReport(String dataKey, Integer proId, Integer appId) {
        return ds.createQuery(CmpReport.class).filter("dataKey", dataKey)
                .filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer proId, Integer appId) {
        return ds
                .createQuery(CmpReport.class)
                .retrievedFields(false, "createDate", "geneDetectionDetail",
                        "cmpFilling").filter("dataKey", dataKey)
                .filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public List<CmpGeneSnpResult> getGddResult(String dataKey, Integer proId,
            Integer appId) {
        List<CmpGeneSnpResult> resultList = new ArrayList<CmpGeneSnpResult>();
        CmpReport cr = ds.createQuery(CmpReport.class)
                .retrievedFields(true, "geneDetectionDetail")
                .filter("dataKey", dataKey).filter("projectId", proId)
                .filter("appId", appId).get();
        Map<String, CmpGeneDetectionDetail> map_gene = cr
                .getGeneDetectionDetail();
        if (map_gene != null) {
            CmpGeneDetectionDetail gdd = map_gene.get("all");
            if (gdd != null) {
                List<CmpGeneSnpResult> list = gdd.getResult();
                List<CmpGeneSnpResult> list_tmp = new ArrayList<CmpGeneSnpResult>();
                // 只允许字母和数字
                String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
                Pattern p = Pattern.compile(regEx);
                for (CmpGeneSnpResult gsr : list) {
                    CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
                    gsr_tmp.setDiseaseName(gsr.getDiseaseName());
                    gsr_tmp.setDiseaseEngName(p
                            .matcher(gsr.getDiseaseEngName()).replaceAll("")
                            .trim());
                    gsr_tmp.setGene(gsr.getGene());
                    list_tmp.add(gsr_tmp);
                }
                for (int i = 0; i < list_tmp.size(); i++) {
                    int num = 1;
                    for (int j = list_tmp.size() - 1; j > i; j--) {
                        if (list_tmp.get(j).getDiseaseEngName()
                                .equals(list_tmp.get(i).getDiseaseEngName())
                                && list_tmp.get(j).getGene()
                                        .equals(list_tmp.get(i).getGene())) {
                            list_tmp.remove(j);
                            num++;
                        }
                    }
                    CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
                    gsr_tmp.setDiseaseName(list_tmp.get(i).getDiseaseName());
                    gsr_tmp.setDiseaseEngName(list_tmp.get(i)
                            .getDiseaseEngName());
                    gsr_tmp.setGene(list_tmp.get(i).getGene());
                    gsr_tmp.setMutNum(num);
                    resultList.add(gsr_tmp);
                }
                // 将结果根据疾病类型排序
                Collections.sort(resultList,
                        new Comparator<CmpGeneSnpResult>() {
                            @Override
                            public int compare(CmpGeneSnpResult gsr1,
                                    CmpGeneSnpResult gsr2) {
                                return gsr1.getDiseaseName().compareTo(
                                        gsr2.getDiseaseName());
                            }
                        });
            }
        }
        return resultList;
    }

    @Override
    public List<Pgs> getPgsList(Integer userId) {
        return this.ds.createQuery(Pgs.class).filter("userId", userId)
                .order("-uploadDate").asList();
    }

    @Override
    public void editPGSFilling(int userId, int appId, String dataKey,
            PGSFilling pgs) {
        ds.update(
                ds.createQuery(Pgs.class).filter("userId", userId)
                        .filter("appId", appId).filter("dataKey", dataKey), ds
                        .createUpdateOperations(Pgs.class).set("fill", pgs));
    }

    @Override
    public List<GddDiseaseDict> getGddDiseaseDictNormal(List<String> normalGene) {
        return ds.createQuery(GddDiseaseDict.class)
                .filter("gene nin", normalGene).order("gene").asList();
    }

    @Override
    public Split getSplit(String dataKey, Integer proId, Integer appId) {
        return ds.createQuery(Split.class).filter("dataKey", dataKey)
                .filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public MIB getMIB(String dataKey, Integer proId, Integer appId) {
        return ds.createQuery(MIB.class).filter("dataKey", dataKey)
                .filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public void editSplit(Split split) {
        ds.update(
                ds.createQuery(Split.class).filter("_id", split.getId()),
                ds.createUpdateOperations(Split.class)
                        .set("upload", split.getUpload())
                        .set("splitDataIds", split.getSplitDataIds()));
    }

    @Override
    public List<HBV> getHBVList(Integer userId) {
        return this.ds.createQuery(HBV.class).filter("userId", userId)
                .order("-uploadDate").asList();
    }

    @Override
    public <T> T getDataReport(Class<T> T, String dataKey, Integer proId,
            Integer appId) {
        return ds.createQuery(T).filter("dataKey", dataKey)
                .filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public List<CmpReport> getCmpList(Integer userId) {
        return ds.createQuery(CmpReport.class).filter("userId", userId)
                .order("-createDate").asList();
    }

}
