package com.celloud.mongo.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.mongo.sdo.NIPT;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.utils.PropertiesUtil;

@ParentPackage("celloud-default")
@Action("niptReport")
@Results({ @Result(name = "toNIPTReport", location = "../../pages/report/NIPT.jsp") })
public class NIPTReportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(NIPTReportAction.class);
	@Inject
	private ReportService reportService;
	private NIPT nipt;
	private String path;

	/**
	 * 查看NIPT报告
	 * 
	 * @return
	 */
	public String toNIPTReport() {
		path = PropertiesUtil.toolsOutPath + "upload";
		nipt = reportService.getNIPTReport(nipt.getDataKey(),
				nipt.getProjectId(), nipt.getAppId());
		return "toNIPTReport";
	}

	public NIPT getNipt() {
		return nipt;
	}

	public void setNipt(NIPT nipt) {
		this.nipt = nipt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
