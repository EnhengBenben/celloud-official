/**  */
package com.mongo.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.bson.types.ObjectId;

import com.google.inject.Inject;
import com.mongo.sdo.CmpFilling;
import com.mongo.sdo.CmpGeneDetectionDetail;
import com.mongo.sdo.CmpGeneSnpResult;
import com.mongo.sdo.CmpReport;
import com.mongo.sdo.Data;
import com.mongo.sdo.DrugResistanceSite;
import com.mongo.sdo.GddDiseaseDict;
import com.mongo.sdo.GeneDetectionResult;
import com.mongo.sdo.RecommendDrug;
import com.mongo.service.ReportService;
import com.nova.action.BaseAction;
import com.nova.utils.ExcelUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PropertiesUtil;

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
        @Result(name = "toPrintGddReport", location = "../../pages/print/printGDD.jsp"),
        @Result(name = "toCmpCount", location = "../../pages/count/cmpReport.jsp") })
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
    private List<GddDiseaseDict> gddDiseaseList;
    private List<CmpGeneSnpResult> allGsr;
    private List<CmpReport> cmpList;
    private String outPath = PropertiesUtil.toolsOutPath + "upload";

    public void updateFill() {
        if (infos != null) {
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
        }
        if (cmpFill != null) {
            log.info("新增用户填写报告部分");
            cmpReport.setId(new ObjectId(cmpId));
            reportService.editCmpFilling(cmpReport.getId(), cmpFill);
        }
    }

    public String toCmpReport() {
        cmpReport = reportService.getSimpleCmp(cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "查看CMP报告");
        if (cmpReport.getAppId() == 112) {
            return "toGddReport";
        }
        return "toCmpReport";
    }

    public String toPrintDetailCmp() {
        cmpReport = reportService.getCmpReport(cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());
        log.info("celloud-用户" + super.session.get("userId") + "准备打印CMP详细报告");
        return "toPrintDetailCmp";
    }

    public String toPrintSimpleCmp() {
        log.info("celloud-用户" + super.session.get("userId") + "查看CMP临床报告");
        cmpReport = reportService.getSimpleCmp(cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());
        return "toPrintSimpleCmp";
    }

    public void saveGddFile() {
        if (cmpFill != null) {
            log.info("新增用户填写报告部分");
            cmpReport.setId(new ObjectId(cmpId));
            reportService.editCmpFilling(cmpReport.getId(), cmpFill);
        }
    }

    public String toPrintGdd() {
        log.info("celloud-用户" + super.session.get("userId") + "准备打印GDD总表报告");
        cmpReport = reportService.getCmpReport(cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());
        Map<String, CmpGeneDetectionDetail> geneMap = cmpReport
                .getGeneDetectionDetail();
        Map<String, CmpGeneDetectionDetail> treeMap = new TreeMap<>();
        List<String> unnormalGene = new ArrayList<>();
        if (geneMap != null) {
            // 需要排除的疾病
            List<String> noDiseaseName = new ArrayList<>();
            noDiseaseName.add("");
            noDiseaseName.add("改变一碳代谢");
            noDiseaseName.add("活力减少");
            noDiseaseName.add("降低表达");
            noDiseaseName.add("表型改变相关");
            noDiseaseName.add("改变高半胱氨酸水平");
            for (String dataKey : geneMap.keySet()) {
                if (geneMap.get(dataKey).getResult().get(0).getGene()
                        .contains("没有发现突变位点")
                        || dataKey.equals("all")) {
                } else {
                    CmpGeneDetectionDetail gdd = geneMap.get(dataKey);
                    List<CmpGeneSnpResult> gsrli = gdd.getResult();
                    List<CmpGeneSnpResult> gsrli_ = new ArrayList<>();
                    for (CmpGeneSnpResult gsr : gsrli) {
                        if (noDiseaseName.contains(gsr.getDiseaseName().trim())) {

                        } else {
                            CmpGeneSnpResult gsr_ = gsr;
                            // 只允许字母和数字
                            String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
                            Pattern p = Pattern.compile(regEx);
                            gsr_.setDiseaseEngName(p
                                    .matcher(gsr.getDiseaseEngName())
                                    .replaceAll("").trim());
                            gsrli_.add(gsr_);
                        }
                    }
                    if (gsrli_.size() > 0) {
                        gdd.setResult(gsrli_);
                        treeMap.put(dataKey, gdd);
                    }
                    unnormalGene.add(dataKey);
                }
            }
            allGsr = geneMap.get("all").getResult();
            cmpReport.setGeneDetectionDetail(treeMap);
        } else {
            unnormalGene.add("");
        }
        gddDiseaseList = reportService.getGddDiseaseDictNormal(unnormalGene);
        gsrList = reportService.getGddResult(cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());
        return "toPrintGddReport";
    }

    public String toCmpCount() {
        Integer userId = (Integer) super.session.get("userId");
        cmpList = reportService.getCmpList(userId);
        Map<String, CmpReport> map = new HashMap<>();
        for (int i = 0; i < cmpList.size(); i++) {
            CmpReport cmp = cmpList.get(i);
            String dataKey = cmp.getDataKey();
            if (map.containsKey(dataKey)) {
                CmpReport before = map.get(dataKey);
                if (before.getCreateDate().getTime() < cmp.getCreateDate()
                        .getTime()) {
                    map.put(dataKey, cmp);
                }
            } else {
                map.put(dataKey, cmp);
            }
        }
        Map<Long, CmpReport> sort = new HashMap<>();
        Long time[] = new Long[map.size()];
        int count = 0;
        for (Entry<String, CmpReport> cmp : map.entrySet()) {
            long e = cmp.getValue().getCreateDate().getTime()
                    + Long.parseLong((Math.random() * 1000 + "").split("\\.")[0]);
            time[count] = e;
            count++;
            sort.put(e, cmp.getValue());
        }
        Arrays.sort(time);
        cmpList = new ArrayList<>();
        for (int i = time.length - 1; i > -1; i--) {
            cmpList.add(sort.get(time[i]));
        }
        // TODO 写死的路径，考虑前台下载时 js导出excel
        long l = new Date().getTime();
        String txt = String.valueOf(l + ".txt");
        infos = String.valueOf(l + ".xls");
        String path = "/share/data/output/" + txt;
        String excelpath = "/share/data/output/" + infos;
        FileTools.createFile(path);
        FileTools.appendWrite(path,
                "数据编号\t原始文件名1\t原始文件名2\t共获得有效片段\t可用片段\t平均测序深度\t基因检测结果\n");
        for (CmpReport cmp : cmpList) {
            StringBuffer line = new StringBuffer(cmp.getDataKey()).append("\t");
            List<Data> dataList = cmp.getData();
            for (Data d : dataList) {
                line.append(d.getFileName()).append("(").append(d.getDataKey())
                        .append(")").append("\t");
            }
            line.append(cmp.getAllFragment()).append("\t");
            line.append(cmp.getUsableFragment()).append("\t");
            line.append(cmp.getAvgCoverage()).append("\t");
            if (cmp.getCmpGeneResult() != null) {
                for (GeneDetectionResult gene : cmp.getCmpGeneResult()) {
                    line.append(gene.getGeneName()).append(":")
                            .append(gene.getSequencingDepth()).append(";");
                }
            }
            line.append("\n");
            FileTools.appendWrite(path, line.toString());
        }
        ExcelUtil.simpleTxtToExcel(path, excelpath, "count");
        return "toCmpCount";
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

    public List<GddDiseaseDict> getGddDiseaseList() {
        return gddDiseaseList;
    }

    public void setGddDiseaseList(List<GddDiseaseDict> gddDiseaseList) {
        this.gddDiseaseList = gddDiseaseList;
    }

    public List<CmpGeneSnpResult> getAllGsr() {
        return allGsr;
    }

    public void setAllGsr(List<CmpGeneSnpResult> allGsr) {
        this.allGsr = allGsr;
    }

    public List<CmpReport> getCmpList() {
        return cmpList;
    }

    public void setCmpList(List<CmpReport> cmpList) {
        this.cmpList = cmpList;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

}
