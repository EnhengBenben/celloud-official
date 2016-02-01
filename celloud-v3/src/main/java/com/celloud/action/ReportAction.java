package com.celloud.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.ReportType;
import com.celloud.model.App;
import com.celloud.model.CmpGeneDetectionDetail;
import com.celloud.model.CmpGeneSnpResult;
import com.celloud.model.CmpReport;
import com.celloud.model.Company;
import com.celloud.model.DataFile;
import com.celloud.model.Dept;
import com.celloud.model.HBV;
import com.celloud.model.MIB;
import com.celloud.model.Oncogene;
import com.celloud.model.Pgs;
import com.celloud.model.Project;
import com.celloud.model.Report;
import com.celloud.model.Split;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.DeptService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.utils.HttpURLUtils;
import com.celloud.utils.PropertiesUtil;

import net.sf.json.JSONArray;

@RequestMapping(value = "/report")
@Controller
public class ReportAction {
    Logger log = LoggerFactory.getLogger(ReportAction.class);
    @Resource
    private ReportService reportService;
    @Resource
    private DataService dataService;
    @Resource
    private CompanyService companyService;
    @Resource
    private DeptService deptService;
    @Resource
    private AppService appService;
    @Resource
    private ProjectService projectService;

    /**
     * 获取报告模块列表
     * 
     * @param page
     * @param size
     * @param condition
     * @param start
     * @param end
     * @param appId
     * @return
     * @date 2016-1-10 下午10:36:57
     */
    @RequestMapping("getReportPageList")
    public ModelAndView reportPages(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE
                    + "") Integer size,
            String condition, String start, String end, Integer appId) {
        Integer userId = ConstantsData.getLoginUserId();
        ModelAndView mv = new ModelAndView("report/report_list");
        Page pager = new Page(page, size);
        PageList<Map<String, Object>> pageList = reportService
                .getReportPageList(userId, pager, condition, start, end, appId);
        return mv.addObject("pageList", pageList);
    }

    /**
     * 从 Tools 端获取数据报告
     * 
     * @param dataKey
     * @param url
     * @return
     * @date 2016-1-10 下午11:37:40
     */
    @ResponseBody
    @RequestMapping(value = "getReportFromTools", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String getReportFromTools(String dataKey, String url,
            Integer projectId) {
        url = PropertiesUtil.toolsPath + url;
        DataFile data = dataService.getDataByKey(dataKey);
        String anotherName = data.getAnotherName();
        if (StringUtils.isNotEmpty(anotherName)) {
            url += "&anotherName=" + anotherName;
        }
        Project project = projectService.selectByPrimaryKey(projectId);
        url += "&projectName=" + project.getProjectName();
        return HttpURLUtils.getHTTPResult(url);
    }

    /**
     * 用于 ModelAndView 加载共有参数
     * 
     * @param path
     * @return
     * @author lin
     * @date 2016-1-11 上午10:39:13
     */
    private ModelAndView getModelAndView(String path, Integer projectId) {
        ModelAndView mv = new ModelAndView(path);
        mv.addObject("toolsPath", PropertiesUtil.toolsOutPath);
        mv.addObject("uploadPath", PropertiesUtil.toolsOutPath + "upload/");
        Project project = projectService.selectByPrimaryKey(projectId);
        mv.addObject("project", project);
        return mv;
    }

    /**
     * 用于 ModelAndView 加载CMP信息
     * 
     * @param path
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月18日 上午11:13:31
     */
    private ModelAndView getCMPModelAndView(String path, String dataKey,
            Integer projectId, Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId,
                appId);
        ModelAndView mv = getModelAndView(path, projectId);
        return mv.addObject("cmpReport", cmpReport);
    }

    /**
     * 获取 CMP 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:57:24
     */
    @RequestMapping("getCMPReport")
    public ModelAndView getCMPReport(String dataKey, Integer projectId,
            Integer appId) {
        return getCMPModelAndView("report/report_data_cmp", dataKey, projectId,
                appId);
    }

    /**
     * 打印 CMP简要报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月18日 上午11:20:57
     */
    @RequestMapping("printLessCMPReport")
    public ModelAndView printLessCMPReport(String dataKey, Integer projectId,
            Integer appId) {
        return getCMPModelAndView("print/print_cmp_less", dataKey, projectId,
                appId);
    }

    /**
     * 打印 CMP详细报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月18日 上午11:20:57
     */
    @RequestMapping("printMoreCMPReport")
    public ModelAndView printMoreCMPReport(String dataKey, Integer projectId,
            Integer appId) {
        return getCMPModelAndView("print/print_cmp_more", dataKey, projectId,
                appId);
    }

    /**
     * 获取 GDD 报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:57:13
     */
    @RequestMapping("getGDDReport")
    public ModelAndView getGDDReport(String dataKey, Integer projectId,
            Integer appId) {
        return getCMPModelAndView("report/report_data_gdd", dataKey, projectId,
                appId);
    }

    @RequestMapping("printGDDReport")
    public ModelAndView printGDDReport(String dataKey, Integer projectId,
            Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId,
                appId);
        ModelAndView mv = getModelAndView("print/print_gdd", projectId);
        if (cmpReport == null)
            return mv;
        Map<String, CmpGeneDetectionDetail> geneMap = cmpReport
                .getGeneDetectionDetail();
        if (geneMap == null)
            return mv;
        // 需要排除的疾病
        List<String> noDiseaseName = new ArrayList<>();
        noDiseaseName.add("");
        noDiseaseName.add("改变一碳代谢");
        noDiseaseName.add("活力减少");
        noDiseaseName.add("降低表达");
        noDiseaseName.add("表型改变相关");
        noDiseaseName.add("改变高半胱氨酸水平");
        // 过滤疾病英文名称只允许字母和数字
        String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);

        Map<String, CmpGeneDetectionDetail> treeMap = new TreeMap<>();
        List<CmpGeneSnpResult> gsrList = new ArrayList<>();
        List<String> unnormalGene = new ArrayList<>();
        for (String key : geneMap.keySet()) {
            if (geneMap.get(key) == null)
                continue;
            List<CmpGeneSnpResult> gddResultList = geneMap.get(key).getResult();
            if (gddResultList == null || gddResultList.get(0) == null
                    || gddResultList.get(0).getGene() == null
                    || gddResultList.get(0).getGene().contains("没有发现突变位点"))
                continue;

            if ("all".equals(key)) {
                for (int i = 0; i < gddResultList.size(); i++) {
                    int num = 1;
                    CmpGeneSnpResult gsr_i = gddResultList.get(i);
                    if (gsr_i == null || gsr_i.getDiseaseName() == null
                            || gsr_i.getDiseaseEngName() == null
                            || gsr_i.getGene() == null || noDiseaseName
                                    .contains(gsr_i.getDiseaseName().trim())) {
                        gddResultList.remove(i);
                        continue;
                    }
                    String engName_i = p.matcher(gsr_i.getDiseaseEngName())
                            .replaceAll("").trim();
                    String gene_i = gsr_i.getGene();
                    for (int j = gddResultList.size() - 1; j > i; j--) {
                        CmpGeneSnpResult gsr_j = gddResultList.get(j);
                        if (gsr_j == null || gsr_j.getDiseaseName() == null
                                || gsr_j.getDiseaseEngName() == null
                                || gsr_j.getGene() == null
                                || noDiseaseName.contains(
                                        gsr_j.getDiseaseName().trim())) {
                            gddResultList.remove(j);
                            continue;
                        }
                        String engName_j = p.matcher(gsr_j.getDiseaseEngName())
                                .replaceAll("").trim();
                        String gene_j = gsr_j.getGene();
                        if (engName_i.equals(engName_j)
                                && gene_j.equals(gene_j)) {
                            gddResultList.remove(j);
                            num++;
                        }
                    }
                    CmpGeneSnpResult gsr_tmp = new CmpGeneSnpResult();
                    gsr_tmp.setDiseaseName(gsr_i.getDiseaseName());
                    gsr_tmp.setDiseaseEngName(engName_i);
                    gsr_tmp.setGene(gene_i);
                    gsr_tmp.setMutNum(num);
                    gsrList.add(gsr_tmp);
                }
                // 将结果根据疾病类型排序
                Collections.sort(gsrList, new Comparator<CmpGeneSnpResult>() {
                    @Override
                    public int compare(CmpGeneSnpResult gsr1,
                            CmpGeneSnpResult gsr2) {
                        return gsr1.getDiseaseName()
                                .compareTo(gsr2.getDiseaseName());
                    }
                });
            } else {
                CmpGeneDetectionDetail gdd = geneMap.get(key);
                List<CmpGeneSnpResult> gsrli_temp = new ArrayList<>();
                for (CmpGeneSnpResult gsr : gddResultList) {
                    if (gsr == null || gsr.getDiseaseName() == null
                            || gsr.getDiseaseEngName() == null || noDiseaseName
                                    .contains(gsr.getDiseaseName().trim()))
                        continue;
                    gsr.setDiseaseEngName(p.matcher(gsr.getDiseaseEngName())
                            .replaceAll("").trim());
                    gsrli_temp.add(gsr);
                }
                if (gsrli_temp != null) {
                    gdd.setResult(gsrli_temp);
                    treeMap.put(key, gdd);
                }
                unnormalGene.add(key);
            }
        }
        cmpReport.setGeneDetectionDetail(treeMap);
        unnormalGene.addAll(unnormalGene);
        if (unnormalGene == null || unnormalGene.size() == 0)
            unnormalGene.add("");
        mv.addObject("gsrList", gsrList);
        mv.addObject("cmpReport", cmpReport);
        mv.addObject("allGsr", geneMap.get("all").getResult());
        mv.addObject("gddDiseaseList",
                reportService.getGddDiseaseDictNormal(unnormalGene));
        return mv;
    }

    /**
     * 用于 ModelAndView 加载split信息
     * 
     * @param path
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:10:57
     */
    private ModelAndView getSplitModelAndView(String path, String dataKey,
            Integer projectId, Integer appId) {
        Split split = reportService.getSplitReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView(path, projectId);
        return mv.addObject("split", split);
    }

    /**
     * 获取 Split 数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:44:45
     */
    @RequestMapping("getSplitReport")
    public ModelAndView getSplitReport(String dataKey, Integer projectId,
            Integer appId) {
        return getSplitModelAndView("report/report_data_split", dataKey,
                projectId, appId);
    }

    /**
     * 打印 Split 数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:44:45
     */
    @RequestMapping("printSplitReport")
    public ModelAndView printSplitReport(String dataKey, Integer projectId,
            Integer appId) {
        return getSplitModelAndView("print/print_split", dataKey, projectId,
                appId);
    }

    /**
     * 用于 ModelAndView 加载MIB信息
     * 
     * @param path
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:10:57
     */
    private ModelAndView getMIBModelAndView(String path, String dataKey,
            Integer projectId, Integer appId) {
        MIB mib = reportService.getMIBReport(dataKey, projectId, appId);
        Map<String, JSONArray> mibCharList = new HashMap<>();
        mibCharList.put("readsDistributionInfo",
                JSONArray.fromObject(mib.getReadsDistributionInfo()));
        mibCharList.put("familyDistributionInfo",
                JSONArray.fromObject(mib.getFamilyDistributionInfo()));
        mibCharList.put("genusDistributionInfo",
                JSONArray.fromObject(mib.getGenusDistributionInfo()));
        ModelAndView mv = getModelAndView(path, projectId);
        mv.addObject("mibCharList", mibCharList);
        return mv.addObject("mib", mib);
    }

    /**
     * 获取 MIB 的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:40:40
     */
    @RequestMapping("getMIBReport")
    public ModelAndView getMIBReport(String dataKey, Integer projectId,
            Integer appId) {
        return getMIBModelAndView("report/report_data_mib", dataKey, projectId,
                appId);
    }

    /**
     * 打印MIB报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:02:09
     */
    @RequestMapping("printMIBReport")
    public ModelAndView printMIBReport(String dataKey, Integer projectId,
            Integer appId) {
        return getMIBModelAndView("print/print_mib", dataKey, projectId, appId);
    }

    /**
     * 获取HBV的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-8 下午4:49:31
     */
    @RequestMapping("getHBVReport")
    public ModelAndView getHBVReport(String dataKey, Integer projectId,
            Integer appId) {
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_hbv", projectId);
        return mv.addObject("hbv", hbv);
    }

    /**
     * 获取PGS的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-9 上午2:57:38
     */
    @RequestMapping("getPgsReport")
    public ModelAndView getPgsReport(String dataKey, Integer projectId,
            Integer appId) {
        Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_pgs", projectId);
        return mv.addObject("pgs", pgs);
    }

    /**
     * 获取Oncogene报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年1月28日下午7:18:01
     */
    @RequestMapping("getOncogeneReport")
    public ModelAndView getOncogeneReport(String dataKey, Integer projectId,
            Integer appId) {
        Oncogene oncogene = reportService.getOncogeneReport(dataKey, projectId,
                appId);
        if (oncogene != null) {
            // jstl 处理 \n 很困难，就在 java 端处理
            oncogene.setReport(oncogene.getReport().replace("\n", "<br/>"));
            oncogene.setWz1(oncogene.getWz1().replace("\n", "<br/>"));
            oncogene.setWz2(oncogene.getWz2().replace("\n", "<br/>"));
            // 排序
            List<String> km = oncogene.getKnowMutation();
            if (km != null) {
                Collections.sort(km);
                oncogene.setKnowMutation(km);
            }
            List<String> out = oncogene.getOut();
            if (out != null) {
                Collections.sort(out);
                oncogene.setOut(out);
            }
        }
        ModelAndView mv = getModelAndView("report/report_data_oncogene",
                projectId);
        return mv.addObject("oncogene", oncogene);
    }

    /**
     * 点击数据报告列表查看上一页数据报告
     * 
     * @author lin
     * @date 2016年1月17日上午12:53:22
     */
    @RequestMapping("prevDataReport")
    @ResponseStatus(value = HttpStatus.OK)
    public void prevDataReport() {
        log.info("点击数据报告列表查看上一页数据报告");
    }

    /**
     * 点击数据报告列表查看下一页数据报告
     * 
     * @author lin
     * @date 2016年1月17日上午12:53:33
     */
    @RequestMapping("nextDataReport")
    @ResponseStatus(value = HttpStatus.OK)
    public void nextDataReport() {
        log.info("点击数据报告列表查看下一页数据报告");
    }

    /**
     * 点击数据报告列表查看报告
     * 
     * @author lin
     * @date 2016年1月17日上午1:03:30
     */
    @RequestMapping("clickItemDataReport")
    @ResponseStatus(value = HttpStatus.OK)
    public void clickItemDataReport() {
        log.info("点击数据报告列表查看报告");
    }

    /**
     * 打印PGS报告
     * 
     * @param appId
     * @param dataKey
     * @return
     * @author lin
     * @date 2016年1月17日下午4:47:37
     */
    @RequestMapping("printPGS")
    public ModelAndView printPGS(Integer appId, Integer projectId,
            String dataKey, String miniPng, String txt, String splitPng) {
        ModelAndView mv = getModelAndView("print/print_pgs", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        DataFile data = dataService.getDataByKey(dataKey);
        Integer fileId = data.getFileId();
        Report report = reportService.getReport(userId, appId, projectId,
                fileId, ReportType.DATA);
        if (StringUtils.isEmpty(report.getPrintContext())) {
            Dept dept = deptService.selectByPrimaryKey(
                    ConstantsData.getLoginUser().getDeptId());
            Company company = companyService
                    .selectByPrimaryKey(dept.getCompanyId());
            mv.addObject("userId", userId).addObject("appId", appId)
                    .addObject("data", data);
            mv.addObject("miniPng", miniPng).addObject("txt", txt)
                    .addObject("splitPng", splitPng);
            mv.addObject("company", company).addObject("dept", dept)
                    .addObject("report", report);
        } else {
            mv.addObject("printContext", report.getPrintContext());
        }
        return mv;
    }

    /**
     * 打印HBV等
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @param imgHtml
     * @param sensitive
     * @param context
     * @param peakFigure
     * @param allPic
     * @param result
     * @param table
     * @param flag
     * @return
     * @author lin
     * @date 2016年1月20日上午11:05:24
     */
    @RequestMapping("printHBV")
    public ModelAndView printHBV(Integer appId, String dataKey,
            Integer projectId, String imgHtml, String sensitive, String context,
            String peakFigure, String allPic, String result, String table,
            Integer flag) {
        ModelAndView mv = getModelAndView("print/print_hbv", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId,
                fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext())
                    .addObject("report", report);
        }
        Integer deptId = ConstantsData.getLoginUser().getDeptId();
        Dept dept = deptService.selectByPrimaryKey(deptId);
        Company company = companyService
                .selectByPrimaryKey(dept.getCompanyId());
        if (StringUtils.isNotEmpty(imgHtml)) {
            String[] imgArr = imgHtml.split(",");
            ArrayList<String> imgList = new ArrayList<String>();
            for (String s : imgArr) {
                imgList.add(s);
            }
            mv.addObject("imgList", imgList);
        }
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        App app = appService.selectByPrimaryKey(appId);
        // TODO 传递HBV之后，很多参数已经不需要传递了，修改时需要改动页面接收参数的方法
        // 为了兼容下面的方法，需要等每个流程一个打印页面之后再修改
        mv.addObject("app", app).addObject("hbv", hbv);
        mv.addObject("userId", userId).addObject("appId", appId)
                .addObject("sensitive", sensitive);
        mv.addObject("context", context).addObject("peakFigure", peakFigure)
                .addObject("allPic", allPic);
        mv.addObject("result", result).addObject("table", table)
                .addObject("flag", flag);
        mv.addObject("company", company).addObject("dept", dept)
                .addObject("report", report);
        return mv;
    }

    // TODO 达安流程打印，需要拆分页面，拆分方法
    @RequestMapping("printDAAN")
    public ModelAndView printDAAN(Integer appId, String dataKey,
            Integer projectId, String context, String imgHtml, String seq,
            String result, String allPic, String table) {
        ModelAndView mv = getModelAndView("print/print_hbv", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId,
                fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext())
                    .addObject("report", report);
        }
        Integer deptId = ConstantsData.getLoginUser().getDeptId();
        Dept dept = deptService.selectByPrimaryKey(deptId);
        Company company = companyService
                .selectByPrimaryKey(dept.getCompanyId());
        if (StringUtils.isNotEmpty(imgHtml)) {
            String[] imgArr = imgHtml.split(",");
            ArrayList<String> imgList = new ArrayList<String>();
            for (String s : imgArr) {
                imgList.add(s);
            }
            mv.addObject("imgList", imgList);
        }
        App app = appService.selectByPrimaryKey(appId);
        mv.addObject("app", app);
        mv.addObject("userId", userId).addObject("appId", appId);
        mv.addObject("context", context).addObject("allPic", allPic);
        mv.addObject("result", result).addObject("table", table)
                .addObject("seq", seq);
        mv.addObject("company", company).addObject("dept", dept)
                .addObject("report", report);
        return mv;
    }

    @RequestMapping("updateContext")
    @ResponseBody
    public Integer updateContext(Report report) {
        return reportService.updateReport(report);
    }

    @RequestMapping("downPdf")
    @ResponseBody
    public String downPdf(Integer appId, Integer projectId) {
        StringBuffer sb = new StringBuffer();
        Integer userId = ConstantsData.getLoginUserId();
        sb.append(userId).append(";").append(appId).append(";")
                .append(projectId).append(";");
        List<DataFile> list = dataService.getDatasInProject(projectId);
        for (DataFile data : list) {
            sb.append(data.getDataKey()).append(",");
        }
        String requestUrl = PropertiesUtil.toolsPath
                + "Procedure!downPDF?dataKeyList=" + sb.toString();
        log.info("requestUrl:" + requestUrl);
        return HttpURLUtils.getHTTPResult(requestUrl);
    }
}
