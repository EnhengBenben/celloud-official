package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.CompanyConstants;
import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.DeptConstants;
import com.celloud.constants.ReportType;
import com.celloud.model.mongo.ABINJ;
import com.celloud.model.mongo.BRAF;
import com.celloud.model.mongo.CmpFilling;
import com.celloud.model.mongo.CmpGeneDetectionDetail;
import com.celloud.model.mongo.CmpGeneSnpResult;
import com.celloud.model.mongo.CmpReport;
import com.celloud.model.mongo.DPD;
import com.celloud.model.mongo.DrugResistanceSite;
import com.celloud.model.mongo.EGFR;
import com.celloud.model.mongo.HBV;
import com.celloud.model.mongo.HCV;
import com.celloud.model.mongo.KRAS;
import com.celloud.model.mongo.MIB;
import com.celloud.model.mongo.Oncogene;
import com.celloud.model.mongo.Pgs;
import com.celloud.model.mongo.RecommendDrug;
import com.celloud.model.mongo.Split;
import com.celloud.model.mongo.TBINH;
import com.celloud.model.mongo.TBRifampicin;
import com.celloud.model.mongo.UGT;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Dept;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.DeptService;
import com.celloud.service.ExperimentService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CustomStringUtils;
import com.celloud.utils.HttpURLUtils;
import com.celloud.utils.PropertiesUtil;

import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.Predicate;
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
    @Resource
    private ExperimentService expService;

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
    @ActionLog(value = "获取报告模块项目报告列表", button = "报告模块")
    @RequestMapping("getReportPageList")
    public ModelAndView reportPages(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size, String condition, String start,
            String end, Integer appId, Integer belongs) {
        Integer userId = ConstantsData.getLoginUserId();
        ModelAndView mv = new ModelAndView("report/report_list");
        Page pager = new Page(page, size);
        PageList<Map<String, Object>> pageList = reportService.getReportPageList(userId, pager, condition, start, end,
                appId, belongs);
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
    @ActionLog(value = "从 Tools 端获取数据报告", button = "数据报告")
    @ResponseBody
    @RequestMapping(value = "getReportFromTools", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String getReportFromTools(String dataKey, String url, Integer projectId) {
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
    private ModelAndView getCMPModelAndView(String path, String dataKey, Integer projectId, Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId, appId);
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
    @ActionLog(value = "查看CMP数据报告", button = "数据报告")
    @RequestMapping("getCMPReport")
    public ModelAndView getCMPReport(String dataKey, Integer projectId, Integer appId) {
        return getCMPModelAndView("report/report_data_cmp", dataKey, projectId, appId);
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
    @ActionLog(value = "打印 CMP简要报告", button = "打印报告")
    @RequestMapping("printLessCMPReport")
    public ModelAndView printLessCMPReport(String dataKey, Integer projectId, Integer appId) {
        return getCMPModelAndView("print/print_cmp_less", dataKey, projectId, appId);
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
    @ActionLog(value = "打印 CMP详细报告", button = "打印报告")
    @RequestMapping("printMoreCMPReport")
    public ModelAndView printMoreCMPReport(String dataKey, Integer projectId, Integer appId) {
        return getCMPModelAndView("print/print_cmp_more", dataKey, projectId, appId);
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
    @ActionLog(value = "查看GDD数据报告", button = "数据报告")
    @RequestMapping("getGDDReport")
    public ModelAndView getGDDReport(String dataKey, Integer projectId, Integer appId) {
        return getCMPModelAndView("report/report_data_gdd", dataKey, projectId, appId);
    }

    @ActionLog(value = "打印GDD数据报告", button = "打印报告")
    @RequestMapping("printGDDReport")
    public ModelAndView printGDDReport(String dataKey, Integer projectId, Integer appId) {
        CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("print/print_gdd", projectId);
        if (cmpReport == null)
            return mv;
        Map<String, CmpGeneDetectionDetail> geneMap = cmpReport.getGeneDetectionDetail();
        // 需要排除的疾病
        final List<String> noDiseaseName = Arrays.asList("", "改变一碳代谢", "活力减少", "降低表达", "表型改变相关", "改变高半胱氨酸水平");
        Map<String, List<String>> conditionMap = new HashMap<>();
        conditionMap.put("name", noDiseaseName);
        if (geneMap == null) {
            String[] fields = { "name" };
            mv.addObject("cmpReport", cmpReport);
            mv.addObject("gddDiseaseList", reportService.getGddDiseaseDictNormal(fields, conditionMap, "gene"));
            return mv;
        }
        // 过滤疾病英文名称只允许字母和数字
        String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);

        Map<String, CmpGeneDetectionDetail> treeMap = new TreeMap<>();
        List<CmpGeneSnpResult> gsrList = new ArrayList<>();
        List<CmpGeneSnpResult> allGsrList = new ArrayList<>();
        List<String> unnormalGene = new ArrayList<>();
        for (String key : geneMap.keySet()) {
            if (geneMap.get(key) == null)
                continue;
            List<CmpGeneSnpResult> gddResultList = geneMap.get(key).getResult();
            if (gddResultList == null || gddResultList.get(0) == null || gddResultList.get(0).getGene() == null
                    || gddResultList.get(0).getGene().contains("没有发现突变位点"))
                continue;

            if ("all".equals(key)) {
                allGsrList = gddResultList;
                for (int i = 0; i < gddResultList.size(); i++) {
                    int num = 1;
                    CmpGeneSnpResult gsr_i = gddResultList.get(i);
                    if (gsr_i == null || gsr_i.getDiseaseName() == null || gsr_i.getDiseaseEngName() == null
                            || gsr_i.getGene() == null || noDiseaseName.contains(gsr_i.getDiseaseName().trim())) {
                        gddResultList.remove(i);
                        continue;
                    }
                    String engName_i = p.matcher(gsr_i.getDiseaseEngName()).replaceAll("").trim();
                    String gene_i = gsr_i.getGene();
                    for (int j = gddResultList.size() - 1; j > i; j--) {
                        CmpGeneSnpResult gsr_j = gddResultList.get(j);
                        if (gsr_j == null || gsr_j.getDiseaseName() == null || gsr_j.getDiseaseEngName() == null
                                || gsr_j.getGene() == null || noDiseaseName.contains(gsr_j.getDiseaseName().trim())) {
                            gddResultList.remove(j);
                            continue;
                        }
                        String engName_j = p.matcher(gsr_j.getDiseaseEngName()).replaceAll("").trim();
                        String gene_j = gsr_j.getGene();
                        if (engName_i.equals(engName_j) && gene_i.equals(gene_j)) {
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
                    public int compare(CmpGeneSnpResult gsr1, CmpGeneSnpResult gsr2) {
                        return gsr1.getDiseaseName().compareTo(gsr2.getDiseaseName());
                    }
                });
            } else {
                CmpGeneDetectionDetail gdd = geneMap.get(key);
                List<CmpGeneSnpResult> gsrli_temp = new ArrayList<>();
                for (CmpGeneSnpResult gsr : gddResultList) {
                    if (gsr == null || gsr.getDiseaseName() == null || gsr.getDiseaseEngName() == null
                            || noDiseaseName.contains(gsr.getDiseaseName().trim()))
                        continue;
                    gsr.setDiseaseEngName(p.matcher(gsr.getDiseaseEngName()).replaceAll("").trim());
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
        if (unnormalGene == null || unnormalGene.size() == 0)
            unnormalGene.add("");
        mv.addObject("gsrList", gsrList);
        mv.addObject("cmpReport", cmpReport);
        List<CmpGeneSnpResult> allGsrListNew = new ArrayList<>();
        for (CmpGeneSnpResult gsr_tmp : allGsrList) {
            String name_tmp = gsr_tmp.getDiseaseName();
            if (name_tmp != null && noDiseaseName.contains(name_tmp)) {
            } else {
                allGsrListNew.add(gsr_tmp);
            }
        }
        CollectionUtils.filter(allGsrList, new Predicate() {
            @Override
            public boolean evaluate(Object obj) {
                CmpGeneSnpResult gsr_tmp = (CmpGeneSnpResult) obj;
                String name_tmp = gsr_tmp.getDiseaseName();
                if (name_tmp != null && noDiseaseName.contains(name_tmp)) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        mv.addObject("allGsr", allGsrListNew);
        String[] fields = { "gene", "name" };
        conditionMap.put("gene", unnormalGene);
        mv.addObject("gddDiseaseList", reportService.getGddDiseaseDictNormal(fields, conditionMap, "gene"));
        return mv;
    }

    /**
     * 修改燕达流程用户填写信息
     * 
     * @param cmpFill
     * @param cmpId
     * @author leamo
     * @date 2016年2月1日 下午7:51:52
     */
    @ActionLog(value = "打印报告时修改燕达流程用户填写信息", button = "修改数据报告")
    @RequestMapping("updateYANDAFilling")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateYANDAFilling(CmpFilling cmpFill, String cmpId) {
        List<DrugResistanceSite> rssList = cmpFill.getResistanceSiteSum();
        List<DrugResistanceSite> pmList = cmpFill.getPersonalizedMedicine();
        List<RecommendDrug> rdList = cmpFill.getRecommendDrug();
        if (rssList != null) {
            filterFillDrugResistanceSite(rssList);
            cmpFill.setResistanceSiteSum(rssList);
        }
        if (pmList != null) {
            filterFillDrugResistanceSite(pmList);
            cmpFill.setPersonalizedMedicine(pmList);
        }
        if (rdList != null) {
            CollectionUtils.filter(rdList, new Predicate() {
                @Override
                public boolean evaluate(Object obj) {
                    RecommendDrug rd = (RecommendDrug) obj;
                    if ((rd.getDrugName() == null || rd.getDrugName().trim().equals(""))
                            && (rd.getDrugDescrip() == null || rd.getDrugDescrip().trim().equals(""))) {
                        return false;
                    } else {
                        return true;
                    }
                }
            });
            cmpFill.setRecommendDrug(rdList);
        }
        reportService.updateCmpFilling(new ObjectId(cmpId), cmpFill);
    }

    /**
     * 过滤CMP用户填写药物信息为空的信息
     * 
     * @param list
     * @author leamo
     * @date 2016年2月3日 下午3:40:59
     */
    private void filterFillDrugResistanceSite(List<DrugResistanceSite> list) {
        CollectionUtils.filter(list, new Predicate() {
            @Override
            public boolean evaluate(Object obj) {
                DrugResistanceSite rs = (DrugResistanceSite) obj;
                if ((rs.getDrug() == null || rs.getDrug().trim().equals(""))
                        && (rs.getGeneName() == null || rs.getGeneName().trim().equals(""))
                        && (rs.getMutationSite() == null || rs.getMutationSite().trim().equals(""))) {
                    return false;
                } else {
                    return true;
                }
            }
        });
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
    private ModelAndView getSplitModelAndView(String path, String dataKey, Integer projectId, Integer appId) {
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
    @ActionLog(value = "查看split数据报告", button = "数据报告")
    @RequestMapping("getSplitReport")
    public ModelAndView getSplitReport(String dataKey, Integer projectId, Integer appId) {
        return getSplitModelAndView("report/report_data_split", dataKey, projectId, appId);
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
    @ActionLog(value = "打印split数据报告", button = "打印数据报告")
    @RequestMapping("printSplitReport")
    public ModelAndView printSplitReport(String dataKey, Integer projectId, Integer appId) {
        return getSplitModelAndView("print/print_split", dataKey, projectId, appId);
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
    private ModelAndView getMIBModelAndView(String path, String dataKey, Integer projectId, Integer appId) {
        MIB mib = reportService.getMIBReport(dataKey, projectId, appId);
        Map<String, JSONArray> mibCharList = new HashMap<>();
        ModelAndView mv = getModelAndView(path, projectId);
        if (mib == null)
            return mv;
        mibCharList.put("readsDistributionInfo", JSONArray.fromObject(mib.getReadsDistributionInfo()));
        mibCharList.put("familyDistributionInfo", JSONArray.fromObject(mib.getFamilyDistributionInfo()));
        mibCharList.put("genusDistributionInfo", JSONArray.fromObject(mib.getGenusDistributionInfo()));
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
    @ActionLog(value = "查看MIB数据报告", button = "数据报告")
    @RequestMapping("getMIBReport")
    public ModelAndView getMIBReport(String dataKey, Integer projectId, Integer appId) {
        return getMIBModelAndView("report/report_data_mib", dataKey, projectId, appId);
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
    @ActionLog(value = "打印MIB数据报告", button = "打印数据报告")
    @RequestMapping("printMIBReport")
    public ModelAndView printMIBReport(String dataKey, Integer projectId, Integer appId) {
        return getMIBModelAndView("print/print_mib", dataKey, projectId, appId);
    }

    /**
     * 修改
     * 
     * @param cmpFill
     * @param cmpId
     * @author leamo
     * @date 2016年2月18日 上午10:54:53
     */
    @ActionLog(value = "打印MIB数据报告时修改用户填写的信息", button = "修改数据报告")
    @RequestMapping("updateMIBFilling")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMIBFilling(MIB mib) {
        reportService.updateMIBFilling(mib);
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
    @ActionLog(value = "查看HBV数据报告", button = "数据报告")
    @RequestMapping("getHBVReport")
    public ModelAndView getHBVReport(String dataKey, Integer projectId, Integer appId) {
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_hbv", projectId);
        return mv.addObject("hbv", hbv);
    }
    
    /**
     * 获取ABINJ的数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年4月13日下午3:59:57
     */
    @ActionLog(value = "查看ABINJ数据报告", button = "数据报告")
    @RequestMapping("getABINJReport")
    public ModelAndView getABINJReport(String dataKey, Integer projectId, Integer appId) {
    	ABINJ abinj = reportService.getABINJReport(dataKey, projectId, appId);
    	ModelAndView mv = getModelAndView("report/report_data_abinj", projectId);
    	return mv.addObject("abinj", abinj);
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
    @ActionLog(value = "查看PGS数据报告", button = "数据报告")
    @RequestMapping("getPgsReport")
    public ModelAndView getPgsReport(String dataKey, Integer projectId, Integer appId) {
        ModelAndView mv = getModelAndView("report/report_data_pgs", projectId);
        Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
        Integer userId = ConstantsData.getLoginUserId();
		List<Experiment> expList = expService.getReportList(userId, dataKey, appId);
        if (expList != null && expList.size() > 0) {
            mv.addObject("experiment", expList.get(0));
        }
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
    @ActionLog(value = "查看Oncogene数据报告", button = "数据报告")
    @RequestMapping("getOncogeneReport")
    public ModelAndView getOncogeneReport(String dataKey, Integer projectId, Integer appId) {
        Oncogene oncogene = reportService.getOncogeneReport(dataKey, projectId, appId);
        if (oncogene != null) {
            // jstl 处理 \n 很困难，就在 java 端处理
            oncogene.setReport(CustomStringUtils.htmlbr(oncogene.getReport()));
            oncogene.setWz1(CustomStringUtils.htmlbr(oncogene.getWz1()));
            oncogene.setWz2(CustomStringUtils.htmlbr(oncogene.getWz2()));
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
        ModelAndView mv = getModelAndView("report/report_data_oncogene", projectId);
        return mv.addObject("oncogene", oncogene);
    }

    /**
     * 获取HCV数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月7日下午5:07:31
     */
    @ActionLog(value = "查看HCV数据报告", button = "数据报告")
    @RequestMapping("getHCVReport")
    public ModelAndView getHCVReport(String dataKey, Integer projectId, Integer appId) {
        HCV hcv = reportService.getHCVReport(dataKey, projectId, appId);
        ModelAndView mv = getModelAndView("report/report_data_hcv", projectId);
        return mv.addObject("hcv", hcv);
    }

    /**
     * 获取EGFR数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月10日下午2:47:57
     */
    @ActionLog(value = "查看EGFR数据报告", button = "数据报告")
    @RequestMapping("getEGFRReport")
    public ModelAndView getEGFRReport(String dataKey, Integer projectId, Integer appId) {
        EGFR egfr = reportService.getEGFRReport(dataKey, projectId, appId);
        String mp = egfr.getMutationPosition();
        if (StringUtils.isNotEmpty(mp)) {
            egfr.setMutationPosition(CustomStringUtils.htmlbr(mp));
        }
        ModelAndView mv = getModelAndView("report/report_data_egfr", projectId);
        return mv.addObject("egfr", egfr);
    }

    /**
     * 获取KRAS数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月22日下午4:37:27
     */
    @ActionLog(value = "查看KRAS数据报告", button = "数据报告")
    @RequestMapping("getKRASReport")
    public ModelAndView getKRASReport(String dataKey, Integer projectId, Integer appId) {
        KRAS kras = reportService.getKRASReport(dataKey, projectId, appId);
        String mp = kras.getMutationPosition();
        if (StringUtils.isNotBlank(mp)) {
            kras.setMutationPosition(CustomStringUtils.htmlbr(mp));
        }
		String pos = kras.getPosition();
		if (StringUtils.isNotBlank(pos)) {
			kras.setPosition(CustomStringUtils.htmlbr(pos));
		}
        ModelAndView mv = getModelAndView("report/report_data_kras", projectId);
        return mv.addObject("kras", kras);
    }

    /**
     * 获取DPD数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月25日下午4:00:38
     */
    @ActionLog(value = "查看DPD数据报告", button = "数据报告")
    @RequestMapping("getDPDReport")
    public ModelAndView getDPDReport(String dataKey, Integer projectId, Integer appId) {
        DPD dpd = reportService.getDPDReport(dataKey, projectId, appId);
        String mp = dpd.getMutationPosition();
        dpd.setMutationPosition(CustomStringUtils.toTable(mp));
        String postion = dpd.getPosition();
        if (StringUtils.isNotBlank(postion)) {
            dpd.setPosition(CustomStringUtils.htmlbr(postion));
        }
        ModelAndView mv = getModelAndView("report/report_data_dpd", projectId);
        return mv.addObject("dpd", dpd);
    }

    /**
     * 获取UGT数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author lin
     * @date 2016年3月25日下午4:00:51
     */
    @ActionLog(value = "查看UGT数据报告", button = "数据报告")
    @RequestMapping("getUGTReport")
    public ModelAndView getUGTReport(String dataKey, Integer projectId, Integer appId) {
        UGT ugt = reportService.getUGTReport(dataKey, projectId, appId);
        String position = ugt.getPosition();
        if (StringUtils.isNotBlank(position)) {
            ugt.setPosition(CustomStringUtils.htmlbr(position));
        }
        String mutationPosition = ugt.getMutationPosition();
        if (StringUtils.isNotBlank(mutationPosition)) {
            ugt.setMutationPosition(CustomStringUtils.htmlbr(mutationPosition));
        }
        ModelAndView mv = getModelAndView("report/report_data_ugt", projectId);
        return mv.addObject("ugt", ugt);
    }

    /**
     * 点击数据报告列表查看上一页数据报告
     * 
     * @author lin
     * @date 2016年1月17日上午12:53:22
     */
    @ActionLog(value = "点击数据报告列表查看上一页数据报告", button = "数据报告")
    @RequestMapping("prevDataReport")
    @ResponseStatus(value = HttpStatus.OK)
    public void prevDataReport() {
        log.info("点击数据报告列表查看上一页数据报告");
    }

    /**
     * 获取TBINH数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author MQ
     */
    @ActionLog(value = "查看TBINH数据报告", button = "数据报告")
    @RequestMapping("getTBINHReport")
    public ModelAndView getTBINHReport(String dataKey, Integer projectId, Integer appId) {
        TBINH tbinh = reportService.getTBINHReport(dataKey, projectId, appId);
        String position = tbinh.getPosition();
        tbinh.setPosition(CustomStringUtils.toTable(position));
        String mutationPosition = tbinh.getMutationPosition();
        tbinh.setMutationPosition(CustomStringUtils.toTable(mutationPosition));
        // 获取userId下野生型,非野生型,两者都不是的数量
        Integer userId = tbinh.getUserId();
        String simpleGeneName = tbinh.getSimpleGeneName();
        // 两者都不是
        Integer neither = reportService.getTBINHisWildByGeneNameAndUserId(userId, simpleGeneName, 0);
        // 野生型
        Integer wild = reportService.getTBINHisWildByGeneNameAndUserId(userId, simpleGeneName, 1);
        // 非野生型
        Integer mutant = reportService.getTBINHisWildByGeneNameAndUserId(userId, simpleGeneName, 2);
        ModelAndView mv = getModelAndView("report/report_data_tbinh", projectId);
        return mv.addObject("tbinh", tbinh).addObject("wild", wild).addObject("mutant", mutant).addObject("neither",
                neither);
    }

    /**
     * 获取TBRifampicin数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author MQ
     */
    @ActionLog(value = "查看TBRifampicin数据报告", button = "数据报告")
    @RequestMapping("getTBRifampicinReport")
    public ModelAndView getTBRifampicinReport(String dataKey, Integer projectId, Integer appId) {
        TBRifampicin tbrifampicin = reportService.getTBRifampicinReport(dataKey, projectId, appId);
        String report = tbrifampicin.getReport();
        tbrifampicin.setReport(CustomStringUtils.toTable(report));
        ModelAndView mv = getModelAndView("report/report_data_tbrifampicin", projectId);
        return mv.addObject("tbrifampicin", tbrifampicin);
    }

    /**
     * 获取BRAF数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author MQ
     */
    @ActionLog(value = "查看BRAF数据报告", button = "数据报告")
    @RequestMapping("getBRAFReport")
    public ModelAndView getBRAFReport(String dataKey, Integer projectId, Integer appId) {
        BRAF braf = reportService.getBRAFReport(dataKey, projectId, appId);
        String mp = braf.getMutationPosition();
        braf.setMutationPosition(CustomStringUtils.toTable(mp));
        ModelAndView mv = getModelAndView("report/report_data_braf", projectId);
        return mv.addObject("braf", braf);
    }

    /**
     * 点击数据报告列表查看下一页数据报告
     * 
     * @author lin
     * @date 2016年1月17日上午12:53:33
     */
    @ActionLog(value = "点击数据报告列表查看下一页数据报告", button = "数据报告")
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
    @ActionLog(value = "点击数据报告列表查看报告", button = "数据报告")
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
	@ActionLog(value = "打印PGS数据报告", button = "打印数据报告")
	@RequestMapping("printPGS")
	public ModelAndView printPGS(Integer appId, Integer projectId, String dataKey, Integer flag) {
		ModelAndView mv = getModelAndView("print/print_pgs", projectId);
		Integer userId = ConstantsData.getLoginUserId();
		DataFile data = dataService.getDataByKey(dataKey);
		Integer fileId = data.getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		if (StringUtils.isEmpty(report.getPrintContext())) {
			Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
			mv.addObject("pgs", pgs).addObject("report", report).addObject("flag", flag);
		} else {
			mv.addObject("printContext", report.getPrintContext());
		}
		return mv;
	}

    /**
     * 打印TBRifampicin
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @param flag
     * @return
     * @author mq
     */
    @ActionLog(value = "打印TBRifampicin数据报告", button = "打印数据报告")
    @RequestMapping("printTBRifampicin")
    public ModelAndView printTBRifampicin(Integer appId, String dataKey, Integer projectId, Integer flag) {
        ModelAndView mv = getModelAndView("print/print_tbrifampicin", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext());
        }
        TBRifampicin tbrifampicin = reportService.getTBRifampicinReport(dataKey, projectId, appId);
        if (StringUtils.isNotBlank(tbrifampicin.getReport())) {
            tbrifampicin.setReport(CustomStringUtils.htmlbr(tbrifampicin.getReport()));
        }

        mv.addObject("tbrifampicin", tbrifampicin).addObject("flag", flag).addObject("report", report);
        return mv;
    }

    /**
     * 打印HBV
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @param flag
     * @return
     * @author lin
     * @date 2016年3月21日下午2:51:25
     */
    @ActionLog(value = "打印HBV数据报告", button = "打印数据报告")
    @RequestMapping("printHBV")
    public ModelAndView printHBV(Integer appId, String dataKey, Integer projectId, Integer flag) {
        ModelAndView mv = getModelAndView("print/print_hbv", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext());
        }
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        mv.addObject("hbv", hbv).addObject("flag", flag).addObject("report", report);
        return mv;
    }

    /**
     * 打印HCV
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @return
     * @author lin
     * @date 2016年3月21日下午2:51:25
     */
    @ActionLog(value = "打印HCV数据报告", button = "打印数据报告")
    @RequestMapping("printHCV")
    public ModelAndView printHCV(Integer appId, String dataKey, Integer projectId) {
        ModelAndView mv = getModelAndView("print/print_hcv", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext());
        }
        HCV hcv = reportService.getHCVReport(dataKey, projectId, appId);
        mv.addObject("hcv", hcv).addObject("report", report);
        return mv;
    }

    /**
     * 打印EGFR
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @return
     * @author lin
     * @date 2016年3月21日下午2:51:25
     */
    @ActionLog(value = "打印EGFR数据报告", button = "打印数据报告")
    @RequestMapping("printEGFR")
    public ModelAndView printEGFR(Integer appId, String dataKey, Integer projectId) {
        ModelAndView mv = getModelAndView("print/print_egfr", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext());
        }
        EGFR egfr = reportService.getEGFRReport(dataKey, projectId, appId);
        mv.addObject("egfr", egfr).addObject("report", report);
        return mv;
    }

    /**
     * 打印KRAS
     * 
     * @param appId
     * @param dataKey
     * @param projectId
     * @return
     * @author lin
     * @date 2016年3月22日下午5:04:22
     */
    @ActionLog(value = "打印KRAS数据报告", button = "打印数据报告")
    @RequestMapping("printKRAS")
    public ModelAndView printKRAS(Integer appId, String dataKey, Integer projectId) {
        ModelAndView mv = getModelAndView("print/print_kras", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext());
        }
        KRAS kras = reportService.getKRASReport(dataKey, projectId, appId);
        mv.addObject("kras", kras).addObject("report", report);
        return mv;
    }

    // TODO 达安流程打印，需要拆分页面，拆分方法
    @ActionLog(value = "打印DAAN数据报告", button = "打印数据报告")
    @RequestMapping("printDAAN")
    public ModelAndView printDAAN(Integer appId, String dataKey, Integer projectId, String context, String imgHtml,
            String seq, String result, String allPic, String table) {
        ModelAndView mv = getModelAndView("print/print_daan", projectId);
        Integer userId = ConstantsData.getLoginUserId();
        Integer fileId = dataService.getDataByKey(dataKey).getFileId();
        Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
        // 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
        if (StringUtils.isNotEmpty(report.getPrintContext())) {
            return mv.addObject("printContext", report.getPrintContext()).addObject("report", report);
        }
        Integer deptId = ConstantsData.getLoginUser().getDeptId();
        Dept dept = deptService.selectByPrimaryKey(deptId);
        Company company = companyService.selectByPrimaryKey(dept.getCompanyId());
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
        mv.addObject("result", result).addObject("table", table).addObject("seq", seq);
        mv.addObject("company", company).addObject("dept", dept).addObject("report", report);
        return mv;
    }

    @ActionLog(value = "打印数据报告时点击保存按钮修改数据报告", button = "修改数据报告")
    @RequestMapping("updateContext")
    @ResponseBody
    public Integer updateContext(Report report) {
        return reportService.updateReport(report);
    }

    @ActionLog(value = "下载PDF报告", button = "下载PDF报告")
    @RequestMapping("downPdf")
    @ResponseBody
    public String downPdf(Integer appId, Integer projectId) {
        StringBuffer sb = new StringBuffer();
        Integer userId = ConstantsData.getLoginUserId();
        sb.append(userId).append(";").append(appId).append(";").append(projectId).append(";");
        List<DataFile> list = dataService.getDatasInProject(projectId);
        for (DataFile data : list) {
            sb.append(data.getDataKey()).append(",");
        }
        String requestUrl = PropertiesUtil.toolsPath + "Procedure!downPDF?dataKeyList=" + sb.toString();
        log.info("requestUrl:" + requestUrl);
        return HttpURLUtils.getHTTPResult(requestUrl);
    }

    /**
     * 获取已保存的医院logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @ActionLog(value = "获取用户所属医院logo", button = "打印数据报告")
    @RequestMapping(value = "company/icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> companyIcon(String file) throws IOException {
        String path = CompanyConstants.getCompanyIconPath() + File.separator + file;
        File targetFile = new File(path);
        // log.info("医院logo绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

    /**
     * 获取已保存的部门logo
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @ActionLog(value = "获取用户所属部门logo", button = "打印数据报告")
    @RequestMapping(value = "dept/icon", method = RequestMethod.GET)
    public ResponseEntity<byte[]> deptIcon(String file) throws IOException {
        String path = DeptConstants.getDeptIconPath() + File.separator + file;
        File targetFile = new File(path);
        // log.info("部门logo目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
}
