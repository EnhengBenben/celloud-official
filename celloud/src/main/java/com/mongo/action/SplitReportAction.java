package com.mongo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONObject;
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
        @Result(name = "splitCount", type = "json", params = { "root",
                "countMapList" }),
        @Result(name = "toSplit", location = "../../pages/report/split.jsp"),
        @Result(name = "toPrintSplit", location = "../../pages/print/split.jsp"),
        @Result(name = "toMibReport", location = "../../pages/report/MIB.jsp"),
        @Result(name = "toPrintMib", location = "../../pages/print/MIB.jsp") })
public class SplitReportAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(SplitReportAction.class);
    @Inject
    private ReportService reportService;
    private Split split;
    private MIB mib;
    private String path;
    private String outPath = PropertiesUtil.toolsOutPath + "upload";
    /** 数据参数同比数据 */
    private Map<String, List<List<Float>>> countMapList;
    /** MIB报告图形数据 */
    private Map<String, String> mibCharList;
    private String param;

    public String getSplitCount() {
        log.info("celloud-用户" + super.session.get("userId") + "获取split数据参数同比报告");
        countMapList = reportService.getSplitCount(new ObjectId(param));
        return "splitCount";
    }

    public String toSplitReport() {
        path = PropertiesUtil.toolsOutPath + "upload";
        split = reportService.getSplit(split.getDataKey(),
                split.getProjectId(), split.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "查看split报告");
        return "toSplit";
    }

    public String toPrintSplit() {
        split = reportService.getSplit(split.getDataKey(),
                split.getProjectId(), split.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "准备打印mib报告");
        return "toPrintSplit";
    }

    public String getMibReport() {
        mib = reportService.getMIB(mib.getDataKey(), mib.getProjectId(),
                mib.getAppId());
        mibCharList = new HashMap<>();
        mibCharList.put("readsDistributionInfo",
                JSONObject.toJSONString(mib.getReadsDistributionInfo()));
        mibCharList.put("familyDistributionInfo",
                JSONObject.toJSONString(mib.getFamilyDistributionInfo()));
        mibCharList.put("genusDistributionInfo",
                JSONObject.toJSONString(mib.getGenusDistributionInfo()));
        log.info("celloud-用户" + super.session.get("userId") + "查看mib报告");
        return "toMibReport";
    }

    public String toPrintMib() {
        mib = reportService.getMIB(mib.getDataKey(), mib.getProjectId(),
                mib.getAppId());
        mibCharList = new HashMap<>();
        mibCharList.put("readsDistributionInfo",
                JSONObject.toJSONString(mib.getReadsDistributionInfo()));
        mibCharList.put("familyDistributionInfo",
                JSONObject.toJSONString(mib.getFamilyDistributionInfo()));
        mibCharList.put("genusDistributionInfo",
                JSONObject.toJSONString(mib.getGenusDistributionInfo()));
        log.info("celloud-用户" + super.session.get("userId") + "准备打印mib报告");
        return "toPrintMib";
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

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public Map<String, List<List<Float>>> getCountMapList() {
        return countMapList;
    }

    public void setCountMapList(Map<String, List<List<Float>>> countMapList) {
        this.countMapList = countMapList;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, String> getMibCharList() {
        return mibCharList;
    }

    public void setMibCharList(Map<String, String> mibCharList) {
        this.mibCharList = mibCharList;
    }

}
