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
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.DrugResistanceSite;
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
@ParentPackage("json-default")
@Action("cmpReport")
@Results({ @Result(name = "success", type = "json", params = { "root", "map" }) })
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
		reportService.saveCmpReport(cmpReport);
	}

	public String getWhole() {
		map = reportService.getOneWholeCmpReport(cmpReport.getId(),
				cmpFill.getId());
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
