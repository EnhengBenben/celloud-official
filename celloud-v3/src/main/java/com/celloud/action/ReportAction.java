package com.celloud.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.ReportType;
import com.celloud.model.CmpGeneDetectionDetail;
import com.celloud.model.CmpGeneSnpResult;
import com.celloud.model.CmpReport;
import com.celloud.model.Company;
import com.celloud.model.DataFile;
import com.celloud.model.Dept;
import com.celloud.model.GddDiseaseDict;
import com.celloud.model.HBV;
import com.celloud.model.MIB;
import com.celloud.model.Pgs;
import com.celloud.model.Report;
import com.celloud.model.Split;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.DeptService;
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
    @RequestMapping("getReportFromTools")
    public String getReportFromTools(String dataKey, String url) {
        url = PropertiesUtil.toolsPath + url;
        DataFile data = dataService.getDataByKey(dataKey);
        String anotherName = data.getAnotherName();
        if (StringUtils.isNotEmpty(anotherName)) {
            url += "&anotherName=" + anotherName;
        }
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
    private ModelAndView getModelAndView(String path) {
        ModelAndView mv = new ModelAndView(path);
        mv.addObject("toolsPath", PropertiesUtil.toolsOutPath);
        mv.addObject("uploadPath", PropertiesUtil.toolsOutPath + "upload/");
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
        ModelAndView mv = getModelAndView(path);
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
        Map<String, CmpGeneDetectionDetail> geneMap = cmpReport
                .getGeneDetectionDetail();
        Map<String, CmpGeneDetectionDetail> treeMap = new TreeMap<>();
        List<String> unnormalGene = new ArrayList<>();
        List<CmpGeneSnpResult> allGsr = new ArrayList<>();
        List<GddDiseaseDict> gddDiseaseList = new ArrayList<>();
        if (geneMap != null) {
            // 需要排除的疾病
            List<String> noDiseaseName = new ArrayList<>();
            noDiseaseName.add("");
            noDiseaseName.add("改变一碳代谢");
            noDiseaseName.add("活力减少");
            noDiseaseName.add("降低表达");
            noDiseaseName.add("表型改变相关");
            noDiseaseName.add("改变高半胱氨酸水平");
            for (String str : geneMap.keySet()) {
                if (geneMap.get(str).getResult().get(0).getGene()
                        .contains("没有发现突变位点") || str.equals("all")) {
                } else {
                    CmpGeneDetectionDetail gdd = geneMap.get(str);
                    List<CmpGeneSnpResult> gsrli = gdd.getResult();
                    List<CmpGeneSnpResult> gsrli_ = new ArrayList<>();
                    for (CmpGeneSnpResult gsr : gsrli) {
                        if (noDiseaseName
                                .contains(gsr.getDiseaseName().trim())) {

                        } else {
                            CmpGeneSnpResult gsr_ = gsr;
                            // 只允许字母和数字
                            String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
                            Pattern p = Pattern.compile(regEx);
                            gsr_.setDiseaseEngName(
                                    p.matcher(gsr.getDiseaseEngName())
                                            .replaceAll("").trim());
                            gsrli_.add(gsr_);
                        }
                    }
                    if (gsrli_.size() > 0) {
                        gdd.setResult(gsrli_);
                        treeMap.put(str, gdd);
                    }
                    unnormalGene.add(str);
                }
            }
            allGsr = geneMap.get("all").getResult();
            cmpReport.setGeneDetectionDetail(treeMap);
        } else {
            unnormalGene.add("");
        }
        gddDiseaseList = reportService.getGddDiseaseDictNormal(unnormalGene);
        List<CmpGeneSnpResult> gsrList = reportService.getGddResult(
                cmpReport.getDataKey(),
                cmpReport.getProjectId(), cmpReport.getAppId());

        ModelAndView mv = getModelAndView("print/print_gdd");
        mv.addObject("cmpReport", cmpReport);
        mv.addObject("gsrList", gsrList);
        mv.addObject("allGsr", allGsr);
        mv.addObject("gddDiseaseList", gddDiseaseList);
        return mv;
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
        Split split = reportService.getSplitReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_split");
        return mv.addObject("split", split);
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
        ModelAndView mv = getModelAndView(path);
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
        ModelAndView mv = getModelAndView("report/report_data_hbv");
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
        ModelAndView mv = getModelAndView("report/report_data_pgs");
        return mv.addObject("pgs", pgs);
    }
    
	/**
	 * 点击数据报告列表查看上一页数据报告
	 * 
	 * @author lin
	 * @date 2016年1月17日上午12:53:22
	 */
	@RequestMapping("prevDataReport")
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
	public ModelAndView printPGS(Integer appId,String dataKey,String miniPng,String txt,String splitPng) {
		ModelAndView mv = getModelAndView("print/print_pgs");
        Integer userId = ConstantsData.getLoginUserId();
        DataFile data = dataService.getDataByKey(dataKey);
        Integer fileId = data.getFileId();
        Report report = reportService.getReport(userId, appId, fileId, ReportType.DATA);
        System.out.println(userId);
        System.out.println(appId);
        System.out.println(fileId);
        System.out.println(miniPng);
        System.out.println(txt);
        System.out.println(splitPng);
        if (StringUtils.isEmpty(report.getContext())) {
            Dept dept = deptService.selectByPrimaryKey(ConstantsData.getLoginUser().getDeptId());
        	Company company = companyService.selectByPrimaryKey(dept.getCompanyId());
            mv.addObject("userId", userId).addObject("appId", appId).addObject("fileId", fileId);
            mv.addObject("miniPng", miniPng).addObject("txt", txt).addObject(splitPng, splitPng);
            mv.addObject("company", company);
        }else{
        	mv.addObject("context", report.getContext());
        }
        return mv;
    }
}
