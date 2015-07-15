/**  */
package com.celloud.mongo.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;

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
public class CmpReportAction {
	@Inject
	private ReportService reportService;
	private CmpFilling cmpFill;
	private CmpReport cmpReport;
	private Map<String, Object> map;

	public void addFill() {
		reportService.saveCmpFilling(cmpFill);
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

}
