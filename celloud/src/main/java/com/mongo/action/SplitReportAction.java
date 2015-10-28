package com.mongo.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.mongo.sdo.MIB;
import com.mongo.sdo.Split;
import com.mongo.service.ReportService;
import com.nova.action.BaseAction;
import com.nova.utils.PropertiesUtil;

/**
 * 操作split MongoDB报告工具
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-10-14下午2:21:09
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("splitReport")
@Results({
        @Result(name = "toSplit", location = "../../pages/report/split.jsp"),
        @Result(name = "toMibReport", location = "../../pages/report/MIB.jsp") })
public class SplitReportAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(SplitReportAction.class);
    @Inject
    private ReportService reportService;
    private Split split;
    private MIB mib;
    private String path;

    public String toSplitReport() {
        path = PropertiesUtil.toolsOutPath + "upload";
        split = reportService.getSplit(split.getDataKey(),
                split.getProjectId(), split.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "查看split报告");
        return "toSplit";
    }

    public String getMibReport() {
        mib = reportService.getMIB(mib.getDataKey(), mib.getProjectId(),
                mib.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "查看mib报告");
        return "toMibReport";
    }

    public Split getSplit() {
        return split;
    }

    public void setSplit(Split split) {
        this.split = split;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MIB getMib() {
        return mib;
    }

    public void setMib(MIB mib) {
        this.mib = mib;
    }

}
