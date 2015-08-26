/**  */
package com.celloud.mongo.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.celloud.mongo.sdo.Pgs;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.utils.PropertiesUtil;

/**
 * 操作PGS mongodb 工具
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-8-18下午4:48:34
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("pgsReport")
@Results({
		@Result(name = "toPgsReport", location = "../../pages/report/PGS.jsp"),
		@Result(name = "toPgsCount", location = "../../pages/count/pgsReport.jsp") })
public class PgsReportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CmpReportAction.class);
	@Inject
	private ReportService reportService;
	private Pgs pgs;
	private String path;
	private List<Pgs> pgsList;

	/**
	 * 查看PGS报告
	 * 
	 * @return
	 */
	public String toPgsReport() {
		path = PropertiesUtil.toolsOutPath + "upload";
		pgs = reportService.getPgsReport(pgs.getDataKey(), pgs.getProjectId(),
				pgs.getAppId());
		return "toPgsReport";
	}

	public String toPgsCount() {
		log.info("查看用户" + pgs.getUsername() + "的数据报告统计");
		pgsList = reportService.getPgsList(pgs.getUserId());
		return "toPgsCount";
	}

	public Pgs getPgs() {
		return pgs;
	}

	public void setPgs(Pgs pgs) {
		this.pgs = pgs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Pgs> getPgsList() {
		return pgsList;
	}

	public void setPgsList(List<Pgs> pgsList) {
		this.pgsList = pgsList;
	}

}
