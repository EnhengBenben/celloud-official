/**  */
package com.celloud.mongo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpGeneDetectionDetail;
import com.celloud.mongo.sdo.CmpGeneSnpResult;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.DrugResistanceSite;
import com.celloud.mongo.sdo.GeneDetectionResult;
import com.celloud.mongo.sdo.RecommendDrug;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;

/**
 * 操作CMP报告接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:47:25
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("cmpReport")
@Results({ @Result(name = "success", location = "../../pages/print/printDetailCMP.jsp") })
public class CmpReportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CmpReportAction.class);
	@Inject
	private ReportService reportService;
	private CmpFilling cmpFill;
	private CmpReport cmpReport;
	private Map<String, Object> map;
	private String infos;

	public void addFill() {
		if (cmpFill.getId() != null) {
			log.info("删除用户填写信息的CMP报告，id"+cmpFill.getId());
			reportService.deleteCmpFilling(cmpFill.getId());
		}
		if (infos != null) {
			log.info("新增用户填写CMP报告部分" + cmpFill.getId());
			String[] r = infos.split("----");
			String[] s1 = StringUtils.splitByWholeSeparatorPreserveAllTokens(
					r[0], ";");
			List<DrugResistanceSite> rssli = new ArrayList<DrugResistanceSite>();
			for (int i = 0; i < s1.length - 1; i++) {
				String[] drsStr = StringUtils
						.splitByWholeSeparatorPreserveAllTokens(s1[i], ",");
				DrugResistanceSite drs = new DrugResistanceSite();
				drs.setGeneName(drsStr[0]);
				drs.setMutationSite(drsStr[1]);
				drs.setDrug(drsStr[2]);
				rssli.add(drs);
			}
			cmpFill.setResistanceSiteSum(rssli);
			String[] s2 = StringUtils.splitByWholeSeparatorPreserveAllTokens(
					r[1], ";");
			List<DrugResistanceSite> pmli = new ArrayList<DrugResistanceSite>();
			for (int i = 0; i < s2.length - 1; i++) {
				String[] drsStr = StringUtils
						.splitByWholeSeparatorPreserveAllTokens(s2[i], ",");
				DrugResistanceSite drs = new DrugResistanceSite();
				drs.setGeneName(drsStr[0]);
				drs.setMutationSite(drsStr[1]);
				drs.setDrug(drsStr[2]);
				pmli.add(drs);
			}
			cmpFill.setPersonalizedMedicine(pmli);
			String[] s3 = StringUtils.splitByWholeSeparatorPreserveAllTokens(
					r[2], ";");
			List<RecommendDrug> rdli = new ArrayList<RecommendDrug>();
			for (int i = 0; i < s3.length - 1; i++) {
				String[] drsStr = StringUtils
						.splitByWholeSeparatorPreserveAllTokens(s3[i], ",");
				RecommendDrug rd = new RecommendDrug();
				rd.setDrugName(drsStr[0]);
				rd.setDrugDescrip(drsStr[1]);
				rdli.add(rd);
			}
			cmpFill.setRecommendDrug(rdli);
			reportService.saveCmpFilling(cmpFill);
		}
	}

	public void addReport() {
		cmpReport = new CmpReport();
		cmpReport.setDataKey((String) map.get("dataKey"));
		cmpReport.setUserId((String) map.get("userId"));
		List<GeneDetectionResult> cmpGeneResult = new ArrayList<GeneDetectionResult>();
		List<Map<String, Object>> grmap = (List<Map<String, Object>>) map
				.get("cmpGeneResult");
		if (grmap != null && grmap.size() > 0) {
			for (Map<String, Object> m : grmap) {
				GeneDetectionResult gdr = new GeneDetectionResult();
				gdr.setGeneName((String) m.get("geneName"));
				gdr.setKnownMSNum((String) m.get("knownMSNum"));
				gdr.setSequencingDepth((Integer) m.get("SequencingDepth"));
				cmpGeneResult.add(gdr);
			}
		}
		cmpReport.setCmpGeneResult(cmpGeneResult);
		cmpReport.setRunDate((String) map.get("runDate"));
		cmpReport.setAllFragment((String) map.get("allFragment"));
		cmpReport.setAvgQuality((String) map.get("avgQuality"));
		cmpReport.setAvgGCContent((String) map.get("avgGCContent"));
		cmpReport.setUsableFragment((String) map.get("usableFragment"));
		cmpReport.setNoDetectedGene((String) map.get("noDetectedGene"));
		cmpReport.setDetectedGene((String) map.get("detectedGene"));
		cmpReport.setAvgCoverage((String) map.get("avgCoverage"));

		List<CmpGeneDetectionDetail> geneDetectionDetail = new ArrayList<CmpGeneDetectionDetail>();
		List<Map<String, Object>> gddmap = (List<Map<String, Object>>) map
				.get("geneDetectionDetail");
		if (gddmap != null && gddmap.size() > 0) {
			for (Map<String, Object> m : gddmap) {
				CmpGeneDetectionDetail gdd = new CmpGeneDetectionDetail();
				gdd.setGeneName((String) m.get("geneName"));
				gdd.setAvgCoverage((String) m.get("avgCoverage"));
				List<Map<String, String>> r = (List<Map<String, String>>) m
						.get("result");
				List<CmpGeneSnpResult> result = new ArrayList<CmpGeneSnpResult>();
				for (Map<String, String> m_ : r) {
					CmpGeneSnpResult gsr = new CmpGeneSnpResult();
					gsr.setAaMutSyntax(m_.get("aaMutSyntax"));
					gsr.setCdsMutSyntax(m_.get("cdsMutSyntax"));
					gsr.setDepth(m_.get("depth"));
					gsr.setGene(m_.get("gene"));
					gsr.setMutationType(m_.get("mutationType"));
					gsr.setMutBase(m_.get("mutBase"));
					gsr.setRefBase(m_.get("refBase"));
					result.add(gsr);
				}
				gdd.setResult(result);
				geneDetectionDetail.add(gdd);
			}
		}
		cmpReport.setGeneDetectionDetail(geneDetectionDetail);
		cmpReport.setBasicStatistics1((Map<String, String>) map
				.get("basicStatistics1"));
		cmpReport.setQualityPath1((String) map.get("qualityPath1"));
		cmpReport.setSeqContentPath1((String) map.get("seqContentPath1"));
		cmpReport.setBasicStatistics2((Map<String, String>) map
				.get("basicStatistics2"));
		cmpReport.setQualityPath2((String) map.get("qualityPath2"));
		cmpReport.setSeqContentPath2((String) map.get("seqContentPath2"));

		reportService.saveCmpReport(cmpReport);
	}

	public String getWhole() {
		map = reportService.getOneWholeCmpReport(cmpReport.getDataKey(),
				cmpReport.getUserId());
		return "success";
	}

	public CmpFilling getCmpFill() {
		return cmpFill;
	}

	public void setCmpFill(CmpFilling cmpFill) {
		this.cmpFill = cmpFill;
	}

	public CmpReport getCmpReport() {
		return cmpReport;
	}

	public void setCmpReport(CmpReport cmpReport) {
		this.cmpReport = cmpReport;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

}
