/**  */
package com.celloud.mongo.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bson.types.ObjectId;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpGeneSnpResult;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.DrugResistanceSite;
import com.celloud.mongo.sdo.RecommendDrug;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;

/**
 * 操作CMP MongoDB报告工具
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:47:25
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("cmpReport")
@Results({
	@Result(name = "toCmpReport", location = "../../pages/report/CMP.jsp"),
	@Result(name = "toPrintDetailCmp", location = "../../pages/print/printDetailCMP.jsp"),
	@Result(name = "toPrintSimpleCmp", location = "../../pages/print/printCMP.jsp"),
	@Result(name = "toGddReport", location = "../../pages/report/GDD.jsp"),
	@Result(name = "toPrintGddReport", location = "../../pages/print/printGDD.jsp"), })
public class CmpReportAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(CmpReportAction.class);
    @Inject
    private ReportService reportService;
    private CmpFilling cmpFill;
    private CmpReport cmpReport;
    private String infos;
    private String cmpId;
    private List<CmpGeneSnpResult> gsrList;

    public void updateFill() {
	if (infos != null) {
	    log.info("新增用户填写CMP报告部分");
	    String[] r = infos.split("----");
	    String[] s1 = StringUtils.splitByWholeSeparatorPreserveAllTokens(
		    r[0], ";");
	    List<DrugResistanceSite> rssli = null;
	    for (int i = 0; i < s1.length - 1; i++) {
		String[] drsStr = StringUtils
			.splitByWholeSeparatorPreserveAllTokens(s1[i], ",");
		DrugResistanceSite drs = new DrugResistanceSite();
		drs.setGeneName(drsStr[0]);
		drs.setMutationSite(drsStr[1]);
		drs.setDrug(drsStr[2]);
		rssli = new ArrayList<DrugResistanceSite>();
		rssli.add(drs);
	    }
	    if (rssli != null && rssli.size() > 0) {
		cmpFill.setResistanceSiteSum(rssli);
	    }
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
	    List<RecommendDrug> rdli = null;
	    for (int i = 0; i < s3.length - 1; i++) {
		String[] drsStr = StringUtils
			.splitByWholeSeparatorPreserveAllTokens(s3[i], ",");
		RecommendDrug rd = new RecommendDrug();
		rd.setDrugName(drsStr[0]);
		rd.setDrugDescrip(drsStr[1]);
		rdli = new ArrayList<RecommendDrug>();
		rdli.add(rd);
	    }
	    if (rdli != null && rdli.size() > 0) {
		cmpFill.setRecommendDrug(rdli);
	    }
	    cmpReport.setId(new ObjectId(cmpId));
	    reportService.editCmpFilling(cmpReport.getId(), cmpFill);
	}
    }

    public String toCmpReport() {
	cmpReport = reportService.getSimpleCmp(cmpReport.getDataKey(),
		cmpReport.getProjectId(), cmpReport.getAppId());
	log.info("celloud-用户" + super.session.get("userId") + "查看"
		+ cmpReport.getAppName() + "报告");
	if (cmpReport.getAppId() == 112) {
	    return "toGddReport";
	}
	return "toCmpReport";
    }

    public String toPrintDetailCmp() {
	cmpReport = reportService.getCmpReport(cmpReport.getDataKey(),
		cmpReport.getProjectId(), cmpReport.getAppId());
	if (cmpReport.getAppId() == 112) {
	    log.info("celloud-用户" + super.session.get("userId") + "准备打印"
		    + cmpReport.getAppName() + "总表报告");
	    gsrList = reportService.getGddResult(cmpReport.getDataKey(),
		    cmpReport.getProjectId(), cmpReport.getAppId());
	    return "toPrintGddReport";
	}
	log.info("celloud-用户" + super.session.get("userId") + "准备打印"
		+ cmpReport.getAppName() + "详细报告");
	return "toPrintDetailCmp";
    }

    public String toPrintSimpleCmp() {
	log.info("celloud-用户" + super.session.get("userId") + "查看CMP临床报告");
	cmpReport = reportService.getSimpleCmp(cmpReport.getDataKey(),
		cmpReport.getProjectId(), cmpReport.getAppId());
	return "toPrintSimpleCmp";
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

    public String getInfos() {
	return infos;
    }

    public void setInfos(String infos) {
	this.infos = infos;
    }

    public String getCmpId() {
	return cmpId;
    }

    public void setCmpId(String cmpId) {
	this.cmpId = cmpId;
    }

    public List<CmpGeneSnpResult> getGsrList() {
	return gsrList;
    }

    public void setGsrList(List<CmpGeneSnpResult> gsrList) {
	this.gsrList = gsrList;
    }

}
