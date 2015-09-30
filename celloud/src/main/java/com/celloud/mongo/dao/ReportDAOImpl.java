package com.celloud.mongo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpGeneDetectionDetail;
import com.celloud.mongo.sdo.CmpGeneSnpResult;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;
import com.celloud.mongo.sdo.NIPT;
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
		for (int i = 0; i < list_tmp.size() - 1; i++) {
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
    public void editPGSFilling(int userId, int appId, String dataKey,
	    PGSFilling pgs) {
	ds.update(
		ds.createQuery(Pgs.class).filter("userId", userId)
			.filter("appId", appId).filter("dataKey", dataKey), ds
			.createUpdateOperations(Pgs.class).set("fill", pgs));
    }

    @Override
    public NIPT getNIPTReport(String dataKey, Integer proId, Integer appId) {
	return ds.createQuery(NIPT.class).filter("dataKey", dataKey)
		.filter("projectId", proId).filter("appId", appId).get();
    }

    @Override
    public List<GddDiseaseDict> getGddDiseaseDictNormal(List<String> normalGene) {
	return ds.createQuery(GddDiseaseDict.class)
		.filter("gene nin", normalGene).order("gene").asList();
    }

}
