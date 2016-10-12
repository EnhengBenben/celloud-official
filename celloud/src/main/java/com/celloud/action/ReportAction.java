package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.ReportType;
import com.celloud.constants.SparkPro;
import com.celloud.model.mongo.ABINJ;
import com.celloud.model.mongo.BRAF;
import com.celloud.model.mongo.BSI;
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
import com.celloud.model.mongo.Rocky;
import com.celloud.model.mongo.S16;
import com.celloud.model.mongo.Split;
import com.celloud.model.mongo.TBINH;
import com.celloud.model.mongo.TBRifampicin;
import com.celloud.model.mongo.Translate;
import com.celloud.model.mongo.UGT;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.Medicine;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Tag;
import com.celloud.model.mysql.Task;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.DeptService;
import com.celloud.service.ExperimentService;
import com.celloud.service.MedicineService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.TagService;
import com.celloud.service.TaskService;
import com.celloud.service.UserService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.CustomStringUtils;
import com.celloud.utils.FileTools;
import com.celloud.utils.VelocityUtil;

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
	@Resource
	private VelocityUtil velocityUtil;
	@Resource
	private TaskService taskService;
	@Resource
	private UserService userService;
    @Resource
    private MedicineService medicineService;
    @Resource
    private TagService tagService;

    @RequestMapping("checkPgsProject")
    @ResponseBody
    public Integer checkPgsProject(Integer projectId) {
        return reportService.getProjectPeriod(projectId);
    }

	@ActionLog(value = "下载", button = "下载")
	@RequestMapping("down")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer down(String path) {
		String filePath = SparkPro.TOOLSPATH + path;
		if (new File(filePath).exists()) {
			FileTools.fileDownLoad(ConstantsData.getResponse(), filePath);
			return 0;
		}
		return 1;
	}

    @ActionLog(value = "下载", button = "下载")
    @RequestMapping("downByName")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Integer downByName(String path) throws UnsupportedEncodingException {
        String filePath = SparkPro.TOOLSPATH + path;
        // 获取dataKey
        String dataKey = path.split("/")[2];
        // 获取appId
        String appId = path.split("/")[1];
        String filename = null;
        // 如果是split则用文件名作为下载名称
        if ("113".equals(appId)) {
            // 根据datakey获取文件
            DataFile dataFile = dataService.getDataByKey(dataKey);
            // 获取文件名称
            filename = dataFile.getFileName().split("R1")[0];
            // 如果截取R1之前的名称是以"_"结尾则截掉该"_"
            if (filename.endsWith("_")) {
                filename = filename.substring(0, filename.lastIndexOf("_"));
            }
        }
        if (new File(filePath).exists()) {
            FileTools.fileDownLoad(ConstantsData.getResponse(), filePath, filename);
            return 0;
        }
        return 1;
    }

    /**
     * 获取数据报告形式列表
     * 
     * @param page
     * @param size
     * @param sample
     * @param condition
     * @param sord
     * @param batch
     * @param period
     * @param beginDate
     * @param endDate
     * @return
     * @author leamo
     * @date 2016年8月29日 下午3:37:38
     */
    @RequestMapping("dataReportPages")
    @ResponseBody
    public PageList<Task> dataReportPages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String sample,
            String condition, @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(name = "tagId", required = false) Integer tagId,
            @RequestParam(name = "batch", required = false) String batch,
            @RequestParam(name = "period", required = false) Integer period, String beginDate,
            String endDate){
        log.info("查看data报告列表");
        Page pager = new Page(page,size);
        Integer userId = ConstantsData.getLoginUserId();
        PageList<Task> plist = taskService.findAllTasks(pager, userId,
                condition, tagId, batch,
                period, beginDate, endDate, sort);
        return plist;
    }

    /**
     * 数据报告列表搜索信息列表
     * 
     * @return
     * @author leamo
     * @date 2016年8月30日 上午11:47:18
     */
    @RequestMapping("reportSearchInfo")
    @ResponseBody
    public Map<String, Object> reportSearchInfo() {
        Integer userId = ConstantsData.getLoginUserId();
        List<String> batchs = dataService.getBatchList(userId);
        List<Tag> tags = tagService.findTags(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("batchs", batchs);
        map.put("tags", tags);
        return map;
    }

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
	@ResponseBody
	public PageList<Map<String, Object>> reportPages(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size, String condition, String start,
			String end, @RequestParam(defaultValue = "0") Integer appId,
			@RequestParam(defaultValue = "1") Integer belongs) {
		Integer userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Map<String, Object>> pageList = reportService.getReportPageList(userId, pager, condition, start, end,
				appId, belongs);
		return pageList;
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
		mv.addObject("uploadPath", "/upload/");
		Project project = projectService.selectByPrimaryKey(projectId);
		mv.addObject("project", project);
		return mv;
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月4日下午4:43:42
     * @description 获取数据报告共有数据
     * @param projectId
     *
     */
    private Map<String, Object> getCommonInfo(Integer projectId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Project project = projectService.selectByPrimaryKey(projectId);
        map.put("uploadPath", "/upload/");
        map.put("project", project);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月7日下午4:30:25
     * @description 获取CMP,CMP_199数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看CMP数据报告", button = "数据报告")
    @RequestMapping("getCMPInfo")
    @ResponseBody
    public Map<String, Object> getCMPInfo(String dataKey, Integer projectId, Integer appId) {
        CmpReport cmp = reportService.getCMPReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("cmp", cmp);
        return map;
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
	@ResponseBody
	public void printLessCMPReport(String dataKey, Integer projectId, Integer appId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "print_less.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print_less.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId, appId);
		context.put("cmpReport", cmpReport);
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printMoreCMPReport(String dataKey, Integer projectId, Integer appId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "print_more.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print_more.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId, appId);
		context.put("cmpReport", cmpReport);
		returnToVelocity(path, context, projectId);
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

    /**
     * 
     * @author miaoqi
     * @date 2016年9月7日下午3:27:00
     * @description 获取GDD数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看GDD数据报告", button = "数据报告")
    @RequestMapping("getGDDInfo")
    @ResponseBody
    public Map<String, Object> getGDDInfo(String dataKey, Integer projectId, Integer appId) {
        CmpReport cmp = reportService.getCMPReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("cmp", cmp);
        return map;
    }

	@ActionLog(value = "打印GDD数据报告", button = "打印报告")
	@RequestMapping("printGDDReport")
	@ResponseBody
	public void printGDDReport(String dataKey, Integer projectId, Integer appId) {
		CmpReport cmpReport = reportService.getCMPReport(dataKey, projectId, appId);
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		if (cmpReport == null) {
			returnToVelocity(path, context, projectId);
			return;
		}
		Map<String, CmpGeneDetectionDetail> geneMap = cmpReport.getGeneDetectionDetail();
		// 需要排除的疾病
		final List<String> noDiseaseName = Arrays.asList("", "改变一碳代谢", "活力减少", "降低表达", "表型改变相关", "改变高半胱氨酸水平");
		Map<String, List<String>> conditionMap = new HashMap<>();
		conditionMap.put("name", noDiseaseName);
		if (geneMap == null) {
			String[] fields = { "name" };
			context.put("cmpReport", cmpReport);
			context.put("gddDiseaseList", reportService.getGddDiseaseDictNormal(fields, conditionMap, "gene"));
			returnToVelocity(path, context, projectId);
			return;
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
		context.put("gsrList", gsrList);
		context.put("cmpReport", cmpReport);
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
		context.put("allGsr", allGsrListNew);

		String[] fields = { "gene", "name" };
		conditionMap.put("gene", unnormalGene);
		context.put("gddDiseaseList", reportService.getGddDiseaseDictNormal(fields, conditionMap, "gene"));

		returnToVelocity(path, context, projectId);
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
     * 获取 Split 数据报告
     * 
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @date 2016-1-10 下午10:44:45
     */
    @ActionLog(value = "查看split数据报告", button = "数据报告")
    @RequestMapping("getSplitInfo")
    @ResponseBody
    public Map<String, Object> getSplitInfo(String dataKey, Integer projectId, Integer appId) {
        Split split = reportService.getSplitReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("split", split);
        map.put("splitId", split.getId().toString());
        return map;
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
	@ResponseBody
	public void printSplitReport(String dataKey, Integer projectId, Integer appId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		Split split = reportService.getSplitReport(dataKey, projectId, appId);
		context.put("split", split);
		returnToVelocity(path, context, projectId);
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
        if (mib.getReadsDistributionInfo() != null
                && mib.getReadsDistributionInfo().size() > 0)
            mibCharList.put("readsDistributionInfo",
                    JSONArray.fromObject(mib.getReadsDistributionInfo()));
        if (mib.getFamilyDistributionInfo() != null
                && mib.getFamilyDistributionInfo().size() > 0)
            mibCharList.put("familyDistributionInfo",
                    JSONArray.fromObject(mib.getFamilyDistributionInfo()));
        if (mib.getGenusDistributionInfo() != null
                && mib.getGenusDistributionInfo().size() > 0)
            mibCharList.put("genusDistributionInfo",
                    JSONArray.fromObject(mib.getGenusDistributionInfo()));
		mv.addObject("mibCharList", mibCharList);
		return mv.addObject("mib", mib);
	}

    @RequestMapping("getMIBReportInfo")
    @ResponseBody
    public Map<String, Object> getMIBReportInfo(String dataKey,
            Integer projectId, Integer appId) {
        MIB mib = reportService.getMIBReport(dataKey, projectId, appId);
        Map<String, Object> map = new HashMap<>();
        if (mib.getReadsDistributionInfo() != null
                && mib.getReadsDistributionInfo().size() > 0)
            map.put("readsDistributionInfo",
                    JSONArray.fromObject(mib.getReadsDistributionInfo()));
        if (mib.getFamilyDistributionInfo() != null
                && mib.getFamilyDistributionInfo().size() > 0)
            map.put("familyDistributionInfo",
                    JSONArray.fromObject(mib.getFamilyDistributionInfo()));
        if (mib.getGenusDistributionInfo() != null
                && mib.getGenusDistributionInfo().size() > 0)
            map.put("genusDistributionInfo",
                    JSONArray.fromObject(mib.getGenusDistributionInfo()));
        map.put("mib", mib);
        map.put("uploadPath", "/upload/");
        return map;
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
	@ResponseBody
	public void printMIBReport(String dataKey, Integer projectId, Integer appId) {
		String path = ConstantsData.getLoginCompanyId() + File.separator + appId + "/print.vm";
		if (!new File("templates/report/" + path).exists()) {
			path = "default/" + appId + "/print.vm";
		}

		MIB mib = reportService.getMIBReport(dataKey, projectId, appId);
		Map<String, Object> context = new HashMap<String, Object>();
		if (mib != null) {
            if (mib.getReadsDistributionInfo() != null
                    && mib.getReadsDistributionInfo().size() > 0)
                context.put("readsDistributionInfo",
                        JSONArray.fromObject(mib.getReadsDistributionInfo()));
            if (mib.getFamilyDistributionInfo() != null
                    && mib.getFamilyDistributionInfo().size() > 0)
                context.put("familyDistributionInfo",
                        JSONArray.fromObject(mib.getFamilyDistributionInfo()));
            if (mib.getGenusDistributionInfo() != null
                    && mib.getGenusDistributionInfo().size() > 0)
                context.put("genusDistributionInfo",
                        JSONArray.fromObject(mib.getGenusDistributionInfo()));
			context.put("mib", mib);
		}
		returnToVelocity(path, context, projectId);
	}

	/**
	 * 用于 ModelAndView 加载BSI信息
	 * 
	 * @param path
	 * @param dataKey
	 * @param projectId
	 * @param appId
	 * @return
	 * @author leamo
	 * @date 2016年1月17日 下午1:10:57
	 */
	private ModelAndView getBSIModelAndView(String path, String dataKey, Integer projectId, Integer appId) {
		BSI bsi = reportService.getBSIReport(dataKey, projectId, appId);
		Map<String, JSONArray> mibCharList = new HashMap<>();
		ModelAndView mv = getModelAndView(path, projectId);
		if (bsi == null)
			return mv;
		mibCharList.put("readsDistributionInfo", JSONArray.fromObject(bsi.getReadsDistributionInfo()));
		mibCharList.put("familyDistributionInfo", JSONArray.fromObject(bsi.getFamilyDistributionInfo()));
		mibCharList.put("genusDistributionInfo", JSONArray.fromObject(bsi.getGenusDistributionInfo()));
		mv.addObject("bsiCharList", mibCharList);
		return mv.addObject("bsi", bsi);
	}

	/**
	 * 获取 BSI 的患者报告
	 * 
	 * @param dataKey
	 * @param projectId
	 * @param appId
	 * @return
	 * @date 2016-1-10 下午10:40:40
	 */
	@ActionLog(value = "查看BSI患者报告", button = "数据报告")
	@RequestMapping("getBSIPatientReport")
	public ModelAndView getBSIPatientReport(String dataKey, Integer projectId, Integer appId, Integer reportIndex,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size, String condition,
			@RequestParam(defaultValue = "0") int sort, @RequestParam(defaultValue = "desc") String sortDate,
			@RequestParam(defaultValue = "asc") String sortBatch, @RequestParam(defaultValue = "asc") String sortName,
			@RequestParam(defaultValue = "asc") String sortPeriod, String batch, String period, String beginDate,
			String endDate, String sampleName) {
		Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
		Matcher m = p.matcher(condition);
		StringBuffer con_sb = new StringBuffer();
		while (m.find()) {
			String rep = "\\\\" + m.group(0);
			m.appendReplacement(con_sb, rep);
		}
		m.appendTail(con_sb);
		Page pager = new Page((page - 1) * size + reportIndex, 1);
		PageList<Task> pageList = taskService.findTasksByUserCondition(pager, ConstantsData.getLoginUserId(), condition,
				sort, sortDate, sortBatch, sortName, sortPeriod, batch, period, beginDate, endDate, appId, sampleName);
		ModelAndView mv = getBSIModelAndView("bsi/report_data_new", dataKey, projectId, appId);
		DataFile df = dataService.getDataByKey(dataKey);
		mv.addObject("pageList", pageList);
		mv.addObject("data", df);
		return mv;
	}

	@ActionLog(value = "查看BSI患者报告", button = "数据报告")
	@RequestMapping("getPrevOrNextBSIReport")
	public ModelAndView getPrevOrNextBSIReport(@RequestParam(defaultValue = "1") int page, String condition,
			@RequestParam(defaultValue = "0") int totalPage, @RequestParam(defaultValue = "0") int sort,
			@RequestParam(defaultValue = "desc") String sortDate, @RequestParam(defaultValue = "asc") String sortBatch,
			@RequestParam(defaultValue = "asc") String sortName, @RequestParam(defaultValue = "asc") String sortPeriod,
			Boolean isPrev, String batch, String period, String beginDate, String endDate, String sampleName) {
		Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
		Matcher m = p.matcher(condition);
		StringBuffer con_sb = new StringBuffer();
		while (m.find()) {
			String rep = "\\\\" + m.group(0);
			m.appendReplacement(con_sb, rep);
		}
		m.appendTail(con_sb);
		Page pager = new Page(page, 1);
		PageList<Task> pageList = taskService.findNextOrPrevTasks(pager, ConstantsData.getLoginUserId(), condition,
				sort, sortDate, sortBatch, sortName, sortPeriod, isPrev, totalPage, batch, period, beginDate, endDate,
				118, sampleName);
		if (pageList != null) {
			List<Task> list = pageList.getDatas();
			if (list != null) {
				Task task = list.get(0);
				if (task != null) {
					String dataKey = task.getDataKey();
					ModelAndView mv = getBSIModelAndView("bsi/report_data_new", dataKey, task.getProjectId(),
							task.getAppId());
					DataFile df = dataService.getDataByKey(dataKey);
					mv.addObject("data", df);
					mv.addObject("pageList", pageList);
					return mv;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 * 获取 BSI 的患者报告
	 * 
	 * @param dataKey
	 * @param projectId
	 * @param appId
	 * @return
	 * @date 2016-1-10 下午10:40:40
	 */
	@ActionLog(value = "查看BSI分析报告", button = "数据报告")
	@RequestMapping("getBSIAnalyReport")
	public ModelAndView getBSIAnalyReport(String dataKey, Integer projectId, Integer appId) {
		return getBSIModelAndView("bsi/report_data_bsi_analy", dataKey, projectId, appId);
	}

	/**
	 * 获取 BSI 的数据报告
	 * 
	 * @param dataKey
	 * @param projectId
	 * @param appId
	 * @return
	 * @date 2016-1-10 下午10:40:40
	 */
	@ActionLog(value = "查看BSI数据报告", button = "数据报告")
	@RequestMapping("getBSIReport")
	public ModelAndView getBSIReport(String dataKey, Integer projectId, Integer appId) {
		return getBSIModelAndView("report/report_data_bsi", dataKey, projectId, appId);
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月7日下午6:51:40
     * @description 获取BSI数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看BSI数据报告", button = "数据报告")
    @RequestMapping("getBSIInfo")
    @ResponseBody
    public Map<String, Object> getBSIInfo(String dataKey, Integer projectId, Integer appId) {
        BSI bsi = reportService.getBSIReport(dataKey, projectId, appId);
        // Map<String, JSONArray> mibCharList = new HashMap<>();
        Map<String, Object> map = getCommonInfo(projectId);
        if (bsi == null)
            return map;
        // mibCharList.put("readsDistributionInfo",
        // JSONArray.fromObject(bsi.getReadsDistributionInfo()));
        // mibCharList.put("familyDistributionInfo",
        // JSONArray.fromObject(bsi.getFamilyDistributionInfo()));
        // mibCharList.put("genusDistributionInfo",
        // JSONArray.fromObject(bsi.getGenusDistributionInfo()));
        // map.put("bsiCharList", mibCharList);
        map.put("bsi", bsi);
        return map;
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
	@ActionLog(value = "打印BSI报告", button = "打印数据报告")
	@RequestMapping("printBSIReport")
	@ResponseBody
	public void printBSIReport(String dataKey, Integer projectId, Integer appId, String templateType) {
		StringBuffer path = new StringBuffer();
		path.append(ConstantsData.getLoginCompanyId()).append(File.separator).append(appId).append(File.separator)
				.append(templateType).append(".vm");
		if (!new File("templates/report/" + path).exists()) {
			path.setLength(0);
			path.append("default").append(File.separator).append(appId).append(File.separator).append(templateType)
					.append(".vm");
		}
		BSI bsi = reportService.getBSIReport(dataKey, projectId, appId);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("bsi", bsi);
		returnToVelocity(path.toString(), context, projectId);
	}

	@ActionLog(value = "打印Rocky报告", button = "打印")
	@RequestMapping("printRockyReport")
	@ResponseBody
	public void printRockyReport(String dataKey, Integer projectId, Integer appId, String templateType) {
		if (StringUtils.isBlank(templateType)) {
			templateType = "print";
		}
		StringBuffer path = new StringBuffer();
		path.append(ConstantsData.getLoginCompanyId()).append(File.separator).append(appId).append(File.separator)
				.append(templateType).append(".vm");
		if (!new File("templates/report/" + path).exists()) {
			path.setLength(0);
			path.append("default").append(File.separator).append(appId).append(File.separator).append(templateType)
					.append(".vm");
		}
		Rocky rocky = reportService.getRockyReport(dataKey, projectId, appId);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("rocky", rocky);
		returnToVelocity(path.toString(), context, projectId);
	}

	/**
	 * 修改Split的数据报告
	 */
	@RequestMapping("updateSplitReport")
	@ResponseBody
	public Integer updateSplitReport(Split split) {
		return reportService.updateSplitReport(split);
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
	@ResponseBody
	public Integer updateMIBFilling(MIB mib) {
		return reportService.updateMIBFilling(mib);
	}

	/**
	 * 修改
	 * 
	 * @param cmpFill
	 * @param cmpId
	 * @author leamo
	 * @date 2016年2月18日 上午10:54:53
	 */
	@ActionLog(value = "修改BSI数据报告用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateBSIFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateBSIFilling(BSI bsi) {
		return reportService.updateBSIFilling(bsi);
	}

	/**
	 * 修改乳腺癌数据报告
	 * 
	 * @param cmpFill
	 * @param cmpId
	 * @author leamo
	 * @date 2016年2月18日 上午10:54:53
	 */
	@ActionLog(value = "修改Rocky数据报告用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateRockyFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateRockyFilling(Rocky rocky) {
		return reportService.updateRockyFilling(rocky);
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
		hbv.setReporttxt(CustomStringUtils.htmlbr(hbv.getReporttxt()));
		ModelAndView mv = getModelAndView("report/report_data_hbv", projectId);
		return mv.addObject("hbv", hbv);
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月6日下午1:34:54
     * @description 查看HBV数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看HBV数据报告", button = "数据报告")
    @RequestMapping("getHBVInfo")
    @ResponseBody
    public Map<String, Object> getHBVInfo(String dataKey, Integer projectId, Integer appId) {
        HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
        hbv.setReporttxt(CustomStringUtils.htmlbr(hbv.getReporttxt()));
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("hbv", hbv);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日下午6:20:52
     * @description 获取ABINJ数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看ABINJ数据报告", button = "数据报告")
    @RequestMapping("getABINJInfo")
    @ResponseBody
    public Map<String, Object> getABINJInfo(String dataKey, Integer projectId, Integer appId) {
        ABINJ abinj = reportService.getABINJReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("abinj", abinj);
        return map;
    }

	/**
	 * 获取16S的数据报告
	 * 
	 * @param dataKey
	 * @param projectId
	 * @param appId
	 * @return
	 * @author lin
	 * @date 2016年4月28日下午12:15:32
	 */
	@ActionLog(value = "查看16S数据报告", button = "数据报告")
	@RequestMapping("get16SReport")
	public ModelAndView get16SReport(String dataKey, Integer projectId, Integer appId) {
		S16 s16 = reportService.get16SReport(dataKey, projectId, appId);
		ModelAndView mv = getModelAndView("report/report_data_16s", projectId);
		return mv.addObject("s16", s16);
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月6日下午6:48:34
     * @description 获取16S数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看16S数据报告", button = "数据报告")
    @RequestMapping("get16SInfo")
    @ResponseBody
    public Map<String, Object> get16SInfo(String dataKey, Integer projectId, Integer appId) {
        S16 s16 = reportService.get16SReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("s16", s16);
        return map;
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
		List<Experiment> expList = expService.getReportList(pgs.getUserId(), dataKey, appId);
		if (expList != null && expList.size() > 0) {
			mv.addObject("experiment", expList.get(0));
		}
		return mv.addObject("pgs", pgs);
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月7日上午9:55:49
     * @description 获取Pgs数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看PGS数据报告", button = "数据报告")
    @RequestMapping("getPgsInfo")
    @ResponseBody
    public Map<String, Object> getPgsInfo(String dataKey, Integer projectId, Integer appId) {
        Map<String, Object> map = getCommonInfo(projectId);
        Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
        List<Experiment> expList = null;
        if (pgs != null) {
            expList = expService.getReportList(pgs.getUserId(), dataKey, appId);
        }
        if (expList != null && expList.size() > 0) {
            map.put("experiment", expList.get(0));
        }
        map.put("pgs", pgs);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日下午4:34:16
     * @description 查看Oncogene数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看Oncogene数据报告", button = "数据报告")
    @RequestMapping("getOncogeneInfo")
    @ResponseBody
    public Map<String, Object> getOncogeneInfo(String dataKey, Integer projectId, Integer appId) {
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
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("oncogene", oncogene);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月5日上午11:05:19
     * @description 获取HCV数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @RequestMapping("getHCVInfo")
    @ResponseBody
    public Map<String, Object> getHCVInfo(String dataKey, Integer projectId, Integer appId) {
        HCV hcv = reportService.getHCVReport(dataKey, projectId, appId);
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("hcv", hcv);
        return map;
    }

	@ActionLog(value = "查看Translate数据报告", button = "数据报告")
	@RequestMapping("getTranslateReport")
	public ModelAndView getTranslateReport(String dataKey, Integer projectId, Integer appId) {
		DataFile data = dataService.getDataByKey(dataKey);
		Translate translate = reportService.getTranslateReport(dataKey, projectId, appId);
		String path = data.getPath();
		int MAX = 256 * 1024;
		if (FileTools.getFileSize(path) > MAX) {
			translate.setSource("输入序列超过256k，不再显示！");
		} else {
			String source = FileTools.readAppoint(data.getPath());
			translate.setSource(source);
		}
		String result = translate.getResult();
		if (result != null && result.length() > MAX) {
			translate.setResult("输出序列超过256k，不再显示，请下载查看！");
		} else {
			translate.setResult(CustomStringUtils.htmlbr(result));
		}
		ModelAndView mv = getModelAndView("report/report_data_translate", projectId);
		return mv.addObject("translate", translate);
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月6日下午7:33:22
     * @description 获取Translate数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看Translate数据报告", button = "数据报告")
    @RequestMapping("getTranslateInfo")
    @ResponseBody
    public Map<String, Object> getTranslateInfo(String dataKey, Integer projectId, Integer appId) {
        DataFile data = dataService.getDataByKey(dataKey);
        Translate translate = reportService.getTranslateReport(dataKey, projectId, appId);
        String path = data.getPath();
        int MAX = 256 * 1024;
        if (FileTools.getFileSize(path) > MAX) {
            translate.setSource("输入序列超过256k，不再显示！");
        } else {
            String source = FileTools.readAppoint(data.getPath());
            translate.setSource(source);
        }
        String result = translate.getResult();
        if (result != null && result.length() > MAX) {
            translate.setResult("输出序列超过256k，不再显示，请下载查看！");
        } else {
            translate.setResult(CustomStringUtils.htmlbr(result));
        }
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("translate", translate);
        return map;
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

    @RequestMapping("getEGFRInfo")
    @ResponseBody
    public Map<String, Object> getEGFRInfo(String dataKey, Integer projectId, Integer appId) {
        EGFR egfr = reportService.getEGFRReport(dataKey, projectId, appId);
        String mp = egfr.getMutationPosition();
        String position = egfr.getPosition();
        String conclusion = egfr.getConclusion();
        if (StringUtils.isNotBlank(conclusion)) {
            egfr.setConclusion(CustomStringUtils.htmlbr(conclusion));
        }
        if (StringUtils.isNotEmpty(mp)) {
            egfr.setMutationPosition(CustomStringUtils.htmlbr(mp));
        }
        if (StringUtils.isNotBlank(position)) {
            egfr.setPosition(CustomStringUtils.htmlbr(position));
        }
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("egfr", egfr);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月5日上午11:04:39
     * @description 获取kras数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @RequestMapping("getKRASInfo")
    @ResponseBody
    public Map<String, Object> getKRASInfo(String dataKey, Integer projectId, Integer appId) {
        KRAS kras = reportService.getKRASReport(dataKey, projectId, appId);
        String mp = kras.getMutationPosition();
        if (StringUtils.isNotBlank(mp)) {
            kras.setMutationPosition(CustomStringUtils.htmlbr(mp));
        }
        String pos = kras.getPosition();
        if (StringUtils.isNotBlank(pos)) {
            kras.setPosition(CustomStringUtils.htmlbr(pos));
        }
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("kras", kras);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日下午5:11:22
     * @description 获取DPD数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看DPD数据报告", button = "数据报告")
    @RequestMapping("getDpdInfo")
    @ResponseBody
    public Map<String, Object> getDpdInfo(String dataKey, Integer projectId, Integer appId) {
        DPD dpd = reportService.getDPDReport(dataKey, projectId, appId);
        String mp = dpd.getMutationPosition();
        dpd.setMutationPosition(CustomStringUtils.toTable(mp));
        String postion = dpd.getPosition();
        if (StringUtils.isNotBlank(postion)) {
            dpd.setPosition(CustomStringUtils.htmlbr(postion));
        }
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("dpd", dpd);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日下午6:34:06
     * @description 获取UGT数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看UGT数据报告", button = "数据报告")
    @RequestMapping("getUGTInfo")
    @ResponseBody
    public Map<String, Object> getUGTInfo(String dataKey, Integer projectId, Integer appId) {
        UGT ugt = reportService.getUGTReport(dataKey, projectId, appId);
        String position = ugt.getPosition();
        if (StringUtils.isNotBlank(position)) {
            ugt.setPosition(CustomStringUtils.htmlbr(position));
        }
        String mutationPosition = ugt.getMutationPosition();
        if (StringUtils.isNotBlank(mutationPosition)) {
            ugt.setMutationPosition(CustomStringUtils.htmlbr(mutationPosition));
        }
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("ugt", ugt);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日上午11:21:31
     * @description 获取TBINH数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看TBINH数据报告", button = "数据报告")
    @RequestMapping("getTBINHInfo")
    @ResponseBody
    public Map<String, Object> getTBINHInfo(String dataKey, Integer projectId, Integer appId) {
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
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("tbinh", tbinh);
        map.put("wild", wild);
        map.put("mutant", mutant);
        map.put("neither", neither);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月6日上午10:04:08
     * @description 获取tbrifampicin数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "查看TBRifampicin数据报告", button = "数据报告")
    @RequestMapping("getTBRifampicinInfo")
    @ResponseBody
    public Map<String, Object> getTBRifampicinInfo(String dataKey, Integer projectId, Integer appId) {
        TBRifampicin tbrifampicin = reportService.getTBRifampicinReport(dataKey, projectId, appId);
        String report = tbrifampicin.getReport();
        tbrifampicin.setReport(CustomStringUtils.toTable(report));
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("tbrifampicin", tbrifampicin);
        return map;
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
     * 
     * @author miaoqi
     * @date 2016年9月5日下午3:18:34
     * @description 获取BRAF数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @RequestMapping("getBRAFInfo")
    @ResponseBody
    public Map<String, Object> getBRAFInfo(String dataKey, Integer projectId, Integer appId) {
        BRAF braf = reportService.getBRAFReport(dataKey, projectId, appId);
        String mp = braf.getMutationPosition();
        braf.setMutationPosition(CustomStringUtils.toTable(mp));
        Map<String, Object> map = getCommonInfo(projectId);
        map.put("braf", braf);
        return map;
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
	@RequestMapping("printPGS_bak")
	@ResponseBody
	public void printPGS_bak(Integer appId, Integer projectId, String dataKey, Integer flag) {
		Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
		// 涉及共享，此处不能取登陆者的companyId
		String path = pgs.getCompanyId() + "/PGS/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/PGS/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		Integer userId = ConstantsData.getLoginUserId();
		DataFile data = dataService.getDataByKey(dataKey);
		Integer fileId = data.getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		if (StringUtils.isEmpty(report.getPrintContext())) {
			context.put("pgs", pgs);
			context.put("report", report);
			context.put("flag", flag);
		} else {
			context.put("printContext", report.getPrintContext());
		}
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printPGS(Integer appId, Integer projectId, String dataKey, Integer flag) {
		Pgs pgs = reportService.getPgsReport(dataKey, projectId, appId);
		// 涉及共享，此处不能取登陆者的companyId
		String path = pgs.getCompanyId() + "/PGS/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/PGS/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("pgs", pgs);
		context.put("flag", flag);
		returnToVelocity(path, context, projectId);
	}

	@RequestMapping("pgsMysqlToMongo")
	public void pgsMysqlToMongo() {
		List<Report> pgsList = reportService.getAllPgsReport();
		for (Report report : pgsList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 15) {
					Integer companyId = user.getCompanyId();
					String dataKey = reportService.getDataKey(report.getFileId());
					Pgs pgs = reportService.getPgsReport(dataKey, report.getProjectId(), report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					Elements textareaEles = document.select("textarea");
					if (companyId == 10 && pgs != null) {
						baseInfo.put("number", inputEles.get(0).val());
						baseInfo.put("name", inputEles.get(1).val());
						baseInfo.put("gender", inputEles.get(2).val());
						baseInfo.put("age", inputEles.get(3).val());
						baseInfo.put("type", inputEles.get(4).val());
						baseInfo.put("applyDate", inputEles.get(5).val());
						baseInfo.put("receiveDate", inputEles.get(6).val());
						baseInfo.put("state", inputEles.get(7).val());
						baseInfo.put("clinical", inputEles.get(8).val());
						baseInfo.put("doctor", inputEles.get(9).val());
						baseInfo.put("dept", inputEles.get(10).val());
						baseInfo.put("detection", inputEles.get(11).val());
						baseInfo.put("review", inputEles.get(12).val());
						if (inputEles.size() == 14) {
							baseInfo.put("date", inputEles.get(13).val());
						} else if (inputEles.size() == 16) {
							baseInfo.put("date", inputEles.get(13).val() + "-" + inputEles.get(14).val() + "-"
									+ inputEles.get(15).val());
						}
						if (textareaEles.size() == 1) {
							baseInfo.put("des2", textareaEles.get(0).text());
						} else {
							baseInfo.put("des", textareaEles.get(0).text());
							baseInfo.put("des2", textareaEles.get(1).text());
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 12) {
						if (inputEles.size() == 13) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("audit", inputEles.get(11).val());
							baseInfo.put("date", inputEles.get(12).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 14) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("date",
									inputEles.get(11).val() + inputEles.get(12).val() + inputEles.get(13).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else {
							System.out.println("bbbbbbbbbbbb");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 14) {
						if (inputEles.size() == 18) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("gender", inputEles.get(1).val());
							baseInfo.put("age", inputEles.get(2).val());
							baseInfo.put("week", inputEles.get(3).val());
							baseInfo.put("outpatient", inputEles.get(4).val());
							baseInfo.put("number", inputEles.get(5).val());
							baseInfo.put("clinical", inputEles.get(6).val());
							baseInfo.put("samplingDate", inputEles.get(7).val());
							baseInfo.put("receiveDate", inputEles.get(8).val());
							baseInfo.put("dept", inputEles.get(9).val());
							baseInfo.put("doctor", inputEles.get(10).val());
							baseInfo.put("material", inputEles.get(11).val());
							baseInfo.put("state", inputEles.get(12).val());

							baseInfo.put("reporter", inputEles.get(13).val());
							baseInfo.put("audit", inputEles.get(14).val());
							baseInfo.put("year", inputEles.get(15).val());
							baseInfo.put("month", inputEles.get(16).val());
							baseInfo.put("day", inputEles.get(17).val());

							baseInfo.put("des1", textareaEles.get(0).text());
							baseInfo.put("des2", textareaEles.get(1).text());
							baseInfo.put("des3", textareaEles.get(2).text());
							baseInfo.put("advice", textareaEles.get(3).text());

						} else if (inputEles.size() == 14) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("material", inputEles.get(5).val());
							baseInfo.put("samplingDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());

							baseInfo.put("reporter", inputEles.get(9).val());
							baseInfo.put("audit", inputEles.get(10).val());
							baseInfo.put("year", inputEles.get(11).val());
							baseInfo.put("month", inputEles.get(12).val());
							baseInfo.put("day", inputEles.get(13).val());

							baseInfo.put("des1", textareaEles.get(0).text());

						} else if (inputEles.size() == 15) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("outpatient", inputEles.get(1).val());
							baseInfo.put("number", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("doctor", inputEles.get(4).val());
							baseInfo.put("samplingDate", inputEles.get(5).val());
							baseInfo.put("age", inputEles.get(6).val());
							baseInfo.put("material", inputEles.get(7).val());
							baseInfo.put("receiveDate", inputEles.get(8).val());
							baseInfo.put("week", inputEles.get(9).val());
							baseInfo.put("clinical", inputEles.get(10).val());
							baseInfo.put("reporter", inputEles.get(11).val());
							baseInfo.put("audit", inputEles.get(12).val());

							if (!inputEles.get(13).val().equals("")) {
								if (inputEles.get(13).val().split("-").length == 3) {
									baseInfo.put("year", inputEles.get(13).val().split("-")[0]);
									baseInfo.put("month", inputEles.get(13).val().split("-")[1]);
									baseInfo.put("day", inputEles.get(13).val().split("-")[2]);
								} else if (inputEles.get(13).val().length() == 8) {
									baseInfo.put("year", inputEles.get(13).val().substring(0, 4));
									baseInfo.put("month", inputEles.get(13).val().substring(4, 5));
									baseInfo.put("day", inputEles.get(13).val().substring(6, 7));
								} else {
									baseInfo.put("year", inputEles.get(13).val().split("年")[0]);
									baseInfo.put("month", inputEles.get(13).val().split("年")[1].split("月")[0]);
									baseInfo.put("day",
											inputEles.get(13).val().split("年")[1].split("月")[1].split("日")[0]);
								}
							} else {
								baseInfo.put("year", "");
								baseInfo.put("month", "");
								baseInfo.put("day", "");
							}

							baseInfo.put("advice", inputEles.get(14).val());
							baseInfo.put("des1", textareaEles.get(1).text());
							baseInfo.put("des2", textareaEles.get(2).text());
							baseInfo.put("des3", textareaEles.get(3).text());
						} else {
							System.out.println("cccccccccccccc");
						}

						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 22) {
						if (inputEles.size() == 13) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("audit", inputEles.get(11).val());
							baseInfo.put("date", inputEles.get(12).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else {
							System.out.println("ddddddddddddddd");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 42) {
						if (inputEles.size() == 11) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("gender", inputEles.get(1).val());
							baseInfo.put("age", inputEles.get(2).val());
							baseInfo.put("dept", inputEles.get(3).val());
							baseInfo.put("number", inputEles.get(4).val());
							baseInfo.put("material", inputEles.get(5).val());
							baseInfo.put("samplingDate", inputEles.get(6).val());
							baseInfo.put("doctor", inputEles.get(7).val());
							baseInfo.put("reporter", inputEles.get(8).val());
							baseInfo.put("review", inputEles.get(9).val());
							baseInfo.put("date", inputEles.get(10).val());

							baseInfo.put("des1", textareaEles.get(0).text());
						} else if (inputEles.size() == 12) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("gender", inputEles.get(1).val());
							baseInfo.put("age", inputEles.get(2).val());
							baseInfo.put("dept", inputEles.get(3).val());
							baseInfo.put("number", inputEles.get(4).val());
							baseInfo.put("material", inputEles.get(5).val());
							baseInfo.put("samplingDate", inputEles.get(6).val());
							baseInfo.put("doctor", inputEles.get(7).val());
							baseInfo.put("reportName", inputEles.get(8).val());
							baseInfo.put("reporter", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("date", inputEles.get(11).val());

							baseInfo.put("des1", textareaEles.get(0).text());
						} else {
							System.out.println("eeeeeeeeeeeeeeeeeeee");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 9) {
						if (inputEles.size() == 8) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("womanName", inputEles.get(2).val());
							baseInfo.put("manName", inputEles.get(3).val());
							baseInfo.put("eggDate", inputEles.get(4).val());
							baseInfo.put("detection", inputEles.get(5).val());
							baseInfo.put("review", inputEles.get(6).val());
							baseInfo.put("date", inputEles.get(7).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 14) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("womanName", inputEles.get(2).val());
							baseInfo.put("manName", "");
							baseInfo.put("eggDate", "");

							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("date",
									inputEles.get(11).val() + inputEles.get(12).val() + inputEles.get(13).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 15) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("womanName", inputEles.get(2).val());
							baseInfo.put("manName", "");
							baseInfo.put("eggDate", "");

							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("date",
									inputEles.get(12).val() + inputEles.get(13).val() + inputEles.get(14).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 7) {
							Elements spanEles = document.select("span[name=print]");
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", spanEles.get(0).val());
							baseInfo.put("womanName", spanEles.get(1).val());
							baseInfo.put("manName", "");
							baseInfo.put("eggDate", "");

							baseInfo.put("detection", inputEles.get(1).val());
							baseInfo.put("review", inputEles.get(2).val());
							baseInfo.put("date",
									inputEles.get(4).val() + inputEles.get(5).val() + inputEles.get(6).val());

							Elements textEles = document.select("div[id=des]");
							baseInfo.put("des", textEles.get(0).text());
						} else {
							System.out.println("ffffffffffffffffffff");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else if (companyId == 6) {
						if (inputEles.size() == 12) {
							baseInfo.put("project", inputEles.get(0).val());
							baseInfo.put("sampleNumber", inputEles.get(1).val());
							baseInfo.put("experimentNumber", inputEles.get(2).val());
							baseInfo.put("testDate", inputEles.get(3).val());
							baseInfo.put("type", inputEles.get(4).val());
							baseInfo.put("content", inputEles.get(5).val());
							baseInfo.put("technology", inputEles.get(6).val());
							baseInfo.put("state", inputEles.get(7).val());
							baseInfo.put("quality", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());

							baseInfo.put("result", textareaEles.get(0).text());
						} else if (inputEles.size() == 14) {
							baseInfo.put("experimentNumber", inputEles.get(1).val());
							baseInfo.put("testDate", inputEles.get(7).val());
							baseInfo.put("type", inputEles.get(5).val());

							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("reportDate",
									inputEles.get(11).val() + inputEles.get(12).val() + inputEles.get(13).val());

							baseInfo.put("result", textareaEles.get(0).text());
						} else if (inputEles.size() == 15) {
							baseInfo.put("experimentNumber", inputEles.get(1).val());
							baseInfo.put("testDate", inputEles.get(7).val());
							baseInfo.put("type", inputEles.get(5).val());

							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("reportDate",
									inputEles.get(12).val() + inputEles.get(13).val() + inputEles.get(14).val());

							baseInfo.put("result", textareaEles.get(0).text());
						} else if (inputEles.size() == 13) {
							baseInfo.put("experimentNumber", inputEles.get(1).val());
							baseInfo.put("testDate", inputEles.get(7).val());
							baseInfo.put("type", inputEles.get(5).val());

							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(12).val());

							baseInfo.put("result", textareaEles.get(0).text());
						} else {
							System.out.println("ggggggggggggggggggg");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					} else {
						if (inputEles.size() == 13) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("audit", inputEles.get(11).val());
							baseInfo.put("date", inputEles.get(12).val());

							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 14) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());

							String date = inputEles.get(11).val() + inputEles.get(12).val() + inputEles.get(13).val();
							baseInfo.put("date", date);
						} else if (inputEles.size() == 15) {
							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("number", inputEles.get(1).val());
							baseInfo.put("name", inputEles.get(2).val());
							baseInfo.put("gender", inputEles.get(3).val());
							baseInfo.put("age", inputEles.get(4).val());
							baseInfo.put("type", inputEles.get(5).val());
							baseInfo.put("applyDate", inputEles.get(6).val());
							baseInfo.put("receiveDate", inputEles.get(7).val());
							baseInfo.put("state", inputEles.get(8).val());
							baseInfo.put("detection", inputEles.get(9).val());
							baseInfo.put("review", inputEles.get(10).val());
							baseInfo.put("audit", inputEles.get(11).val());
							baseInfo.put("date",
									inputEles.get(12).val() + inputEles.get(13).val() + inputEles.get(14).val());
							baseInfo.put("des", textareaEles.get(0).text());
						} else if (inputEles.size() == 6) {
							Elements spanEles = document.select("span[name=print]");

							baseInfo.put("detection", inputEles.get(0).val());
							baseInfo.put("review", inputEles.get(1).val());
							baseInfo.put("audit", inputEles.get(2).val());
							baseInfo.put("date",
									inputEles.get(3).val() + inputEles.get(4).val() + inputEles.get(5).val());

							baseInfo.put("cardId", spanEles.get(0).val());
							baseInfo.put("number", spanEles.get(1).val());
							baseInfo.put("name", spanEles.get(2).val());
							baseInfo.put("gender", spanEles.get(3).val());
							baseInfo.put("age", spanEles.get(4).val());
							baseInfo.put("type", spanEles.get(5).val());
							baseInfo.put("applyDate", spanEles.get(6).val());
							baseInfo.put("receiveDate", spanEles.get(7).val());
							baseInfo.put("state", spanEles.get(8).val());

							Elements textEle = document.select("div[id=des]");
							baseInfo.put("des", textEle.get(0).text());
						} else if (inputEles.size() == 7) {
							Elements spanEles = document.select("span[name=print]");

							baseInfo.put("cardId", inputEles.get(0).val());
							baseInfo.put("detection", inputEles.get(1).val());
							baseInfo.put("review", inputEles.get(2).val());
							baseInfo.put("audit", inputEles.get(3).val());
							baseInfo.put("date",
									inputEles.get(4).val() + inputEles.get(5).val() + inputEles.get(6).val());

							baseInfo.put("number", spanEles.get(0).val());
							baseInfo.put("name", spanEles.get(1).val());
							baseInfo.put("gender", spanEles.get(2).val());
							baseInfo.put("age", spanEles.get(3).val());
							baseInfo.put("type", spanEles.get(4).val());
							baseInfo.put("applyDate", spanEles.get(5).val());
							baseInfo.put("receiveDate", spanEles.get(6).val());
							baseInfo.put("state", spanEles.get(7).val());

							Elements textEle = document.select("div[id=des]");
							baseInfo.put("des", textEle.get(0).text());
						} else {
							System.out.println("hhhhhhhhhhhhhhhhhhhhhh");
						}
						pgs.setBaseInfo(baseInfo);
						reportService.updatePgsFilling(pgs);
					}
				}
			}
		}
	}

	@RequestMapping("hcvMysqlToMongo")
	public void hcvMysqlToMongo() {
		List<Report> hcvList = reportService.getAllHcvReport();
		for (Report report : hcvList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					Integer companyId = user.getCompanyId();
					String dataKey = reportService.getDataKey(report.getFileId());
					HCV hcv = reportService.getHCVReport(dataKey, report.getProjectId(), report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					if (companyId == 57 && hcv != null) {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}
						hcv.setBaseInfo(baseInfo);
						reportService.updateHcvFilling(hcv);
					} else {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("bbbbbbbbb");
						}
						hcv.setBaseInfo(baseInfo);
						reportService.updateHcvFilling(hcv);
					}
				}
			}
		}
	}

	@RequestMapping("egfrMysqlToMongo")
	public void egfrMysqlToMongo() {
		List<Report> egfrList = reportService.getAllEgfrReport();
		for (Report report : egfrList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					Integer companyId = user.getCompanyId();
					String dataKey = reportService.getDataKey(report.getFileId());
					EGFR egfr = reportService.getEGFRReport(dataKey, report.getProjectId(), report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					if (companyId == 57 && egfr != null) {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}
						egfr.setBaseInfo(baseInfo);
						reportService.updateEgfrFilling(egfr);
					} else {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("bbbbbbbbb");
						}
						egfr.setBaseInfo(baseInfo);
						reportService.updateEgfrFilling(egfr);
					}
				}
			}
		}
	}

	@RequestMapping("krasMysqlToMongo")
	public void krasMysqlToMongo() {
		List<Report> krasList = reportService.getAllKrasReport();
		for (Report report : krasList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					String dataKey = reportService.getDataKey(report.getFileId());
					KRAS kras = reportService.getKRASReport(dataKey, report.getProjectId(), report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					if (kras != null) {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}
						kras.setBaseInfo(baseInfo);
						reportService.updateKrasFilling(kras);
					}
				}
			}
		}
	}

	@RequestMapping("tbRifampicinMysqlToMongo")
	public void tbRifampicinMysqlToMongo() {
		List<Report> tbrifampicinList = reportService.getAllTBRifampicinReport();
		for (Report report : tbrifampicinList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					String dataKey = reportService.getDataKey(report.getFileId());
					TBRifampicin tbRifampicin = reportService.getTBRifampicinReport(dataKey, report.getProjectId(),
							report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					if (tbRifampicin != null) {
						if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}
						tbRifampicin.setBaseInfo(baseInfo);
						reportService.updateTBRifampicinFilling(tbRifampicin);
					}
				}
			}
		}
	}

	@RequestMapping("hbvMysqlToMongo_brief")
	public void hbvMysqlToMongo_brief() {
		List<Report> hbvBriefList = reportService.getAllHbvBriefReport();
		for (Report report : hbvBriefList) {
			if (report.getPrintSimple() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					Integer companyId = user.getCompanyId();
					String dataKey = reportService.getDataKey(report.getFileId());
					HBV hbv = reportService.getHBVReport(dataKey, report.getProjectId(), report.getAppId());
					String printSImple = report.getPrintSimple();
					Document document = Jsoup.parse(printSImple);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					if (companyId == 41 && hbv != null) {
						if (inputEles.size() == 30) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(26).val());
							baseInfo.put("reportDate", inputEles.get(27).val());
							baseInfo.put("inspectionPerson", inputEles.get(28).val());
							baseInfo.put("review", inputEles.get(29).val());

							Elements textEles = document.select("textarea");
							baseInfo.put("des", textEles.get(0).text());

							Elements tableEles = document.select("table");
							baseInfo.put("clinical", tableEles.get(0).toString());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}

					} else if (companyId == 636) {
						if (inputEles.size() == 13) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("sex", inputEles.get(1).val());
							baseInfo.put("gender", inputEles.get(2).val());
							baseInfo.put("inpatientNumber", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("bedNo", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("submissionDate", inputEles.get(7).val());
							baseInfo.put("reportNumber", inputEles.get(8).val());
							baseInfo.put("geneType", inputEles.get(9).val());
							baseInfo.put("reportDate", inputEles.get(10).val());
							baseInfo.put("inspectionPerson", inputEles.get(11).val());
							baseInfo.put("review", inputEles.get(12).val());
						} else if (inputEles.size() == 19) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("sex", inputEles.get(1).val());
							baseInfo.put("gender", inputEles.get(2).val());
							baseInfo.put("inpatientNumber", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("bedNo", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("submissionDate", inputEles.get(7).val());
							baseInfo.put("reportNumber", inputEles.get(8).val());
							baseInfo.put("geneType", inputEles.get(9).val());
							baseInfo.put("result1", inputEles.get(10).val());
							baseInfo.put("result2", inputEles.get(11).val());
							baseInfo.put("result3", inputEles.get(12).val());
							baseInfo.put("result4", inputEles.get(13).val());
							baseInfo.put("result5", inputEles.get(14).val());
							baseInfo.put("result6", inputEles.get(15).val());
							baseInfo.put("reportDate", inputEles.get(16).val());
							baseInfo.put("inspectionPerson", inputEles.get(17).val());
							baseInfo.put("review", inputEles.get(18).val());
						} else {
							System.out.println("bbbbbbbbbbbbbbb");
						}
					} else if (companyId == 57) {
						if (inputEles.size() == 15) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("geneType", inputEles.get(10).val());
							baseInfo.put("inspectionDate", inputEles.get(11).val());
							baseInfo.put("reportDate", inputEles.get(12).val());
							baseInfo.put("inspectionPerson", inputEles.get(13).val());
							baseInfo.put("review", inputEles.get(14).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("ccccccccccc");
						}
					} else {
						if (inputEles.size() == 30) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(26).val());
							baseInfo.put("reportDate", inputEles.get(27).val());
							baseInfo.put("inspectionPerson", inputEles.get(28).val());
							baseInfo.put("review", inputEles.get(29).val());

							Elements tableEles = document.select("table");
							baseInfo.put("clinical", tableEles.get(0).toString());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}
					}
					hbv.setBriefBaseInfo(baseInfo);
					reportService.updateHbvBriefFilling(hbv);
				}
			}
		}
	}

	@RequestMapping("hbvMysqlToMongo_detail")
	public void hbvMysqlToMongo_detail() {
		List<Report> hbvDetailList = reportService.getAllHbvDetailReport();
		for (Report report : hbvDetailList) {
			if (report.getPrintContext() != null) {
				User user = userService.selectUserById(report.getUserId());
				if (user.getUserId() != 23) {
					Integer companyId = user.getCompanyId();
					String dataKey = reportService.getDataKey(report.getFileId());
					HBV hbv = reportService.getHBVReport(dataKey, report.getProjectId(), report.getAppId());
					String printContext = report.getPrintContext();
					Document document = Jsoup.parse(printContext);
					Elements inputEles = document.select("input[type=text]");
					Map<String, String> baseInfo = new HashMap<String, String>();
					Elements textareaEles = document.select("textarea");
					if (companyId == 41 && hbv != null) {
						if (inputEles.size() == 30) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(26).val());
							baseInfo.put("reportDate", inputEles.get(27).val());
							baseInfo.put("inspectionPerson", inputEles.get(28).val());
							baseInfo.put("review", inputEles.get(29).val());

							Elements textEles = document.select("textarea");
							if (textEles != null && textEles.size() != 0) {
								baseInfo.put("des", textEles.get(0).text());
							}

							Elements tableEles = document.select("table");
							baseInfo.put("clinical", tableEles.get(0).toString());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("aaaaaaaaaaaaa");
						}

					} else if (companyId == 57) {
						if (inputEles.size() == 30) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("geneType", inputEles.get(10).val());

							// baseInfo.put("result3", inputEles.get(26).val());
							// baseInfo.put("result31",
							// inputEles.get(27).val());
							// baseInfo.put("result32",
							// inputEles.get(28).val());
							// baseInfo.put("result33",
							// inputEles.get(29).val());
							// baseInfo.put("result34",
							// inputEles.get(30).val());
							// baseInfo.put("result35",
							// inputEles.get(31).val());
							// baseInfo.put("result36",
							// inputEles.get(32).val());
							// baseInfo.put("result37",
							// inputEles.get(33).val());
							// baseInfo.put("result4", inputEles.get(34).val());
							// baseInfo.put("result5", inputEles.get(35).val());
							// baseInfo.put("result6", inputEles.get(36).val());
							// baseInfo.put("remark", inputEles.get(37).val());

							baseInfo.put("inspectionDate", inputEles.get(26).val());
							baseInfo.put("reportDate", inputEles.get(27).val());
							baseInfo.put("inspectionPerson", inputEles.get(28).val());
							baseInfo.put("review", inputEles.get(29).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}

							if (textareaEles != null && textareaEles.size() == 1) {
								baseInfo.put("des", textareaEles.get(0).text());
							} else if (textareaEles != null && textareaEles.size() == 2) {
								baseInfo.put("des1", textareaEles.get(0).text());
								baseInfo.put("des", textareaEles.get(1).text());
							}

						} else if (inputEles.size() == 43) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("geneType", inputEles.get(10).val());

							baseInfo.put("result3", inputEles.get(27).val());
							baseInfo.put("result31", inputEles.get(28).val());
							baseInfo.put("result32", inputEles.get(29).val());
							baseInfo.put("result33", inputEles.get(30).val());
							baseInfo.put("result34", inputEles.get(31).val());
							baseInfo.put("result35", inputEles.get(32).val());
							baseInfo.put("result36", inputEles.get(33).val());
							baseInfo.put("result37", inputEles.get(34).val());
							baseInfo.put("result4", inputEles.get(35).val());
							baseInfo.put("result5", inputEles.get(36).val());
							baseInfo.put("result6", inputEles.get(37).val());
							baseInfo.put("remark", inputEles.get(38).val());

							baseInfo.put("inspectionDate", inputEles.get(39).val());
							baseInfo.put("reportDate", inputEles.get(40).val());
							baseInfo.put("inspectionPerson", inputEles.get(41).val());
							baseInfo.put("review", inputEles.get(42).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}

							if (textareaEles != null && textareaEles.size() == 1) {
								baseInfo.put("des", textareaEles.get(0).text());
							} else if (textareaEles != null && textareaEles.size() == 2) {
								baseInfo.put("des1", textareaEles.get(0).text());
								baseInfo.put("des", textareaEles.get(1).text());
							}

						} else {
							System.out.println("ccccccccccc");
						}
					} else {
						if (inputEles.size() == 30) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(26).val());
							baseInfo.put("reportDate", inputEles.get(27).val());
							baseInfo.put("inspectionPerson", inputEles.get(28).val());
							baseInfo.put("review", inputEles.get(29).val());

							Elements tableEles = document.select("table");
							baseInfo.put("clinical", tableEles.get(0).toString());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else if (inputEles.size() == 14) {
							baseInfo.put("name", inputEles.get(0).val());
							baseInfo.put("id", inputEles.get(1).val());
							baseInfo.put("sampleNumber", inputEles.get(2).val());
							baseInfo.put("sampleType", inputEles.get(3).val());
							baseInfo.put("dept", inputEles.get(4).val());
							baseInfo.put("submissionDate", inputEles.get(5).val());
							baseInfo.put("doctor", inputEles.get(6).val());
							baseInfo.put("age", inputEles.get(7).val());
							baseInfo.put("bedNo", inputEles.get(8).val());
							baseInfo.put("inpatientNumber", inputEles.get(9).val());
							baseInfo.put("inspectionDate", inputEles.get(10).val());
							baseInfo.put("reportDate", inputEles.get(11).val());
							baseInfo.put("inspectionPerson", inputEles.get(12).val());
							baseInfo.put("review", inputEles.get(13).val());

							Elements radioEles = document.select("input[type=radio]");
							if (radioEles.get(1).attr("checked").equals("checked")) {
								baseInfo.put("sex", "女");
							} else {
								baseInfo.put("sex", "男");
							}
						} else {
							System.out.println("dddddddddddddddd");
						}
					}
					hbv.setDetailBaseInfo(baseInfo);
					reportService.updateHbvDetailFilling(hbv);
				}
			}
		}
	}

	/**
     * 
     * @author MQ
     * @date 2016年8月25日下午1:14:09
     * @description 打印项目报告
     * @param projectId
     *            项目id
     *
     */
    @ActionLog(value = "打印Pgs项目报告", button = "打印数据报告")
	@RequestMapping("printPgsProject")
	@ResponseBody
	public void printPgsProject(Integer projectId) {
		List<Pgs> pgsList = reportService.getPgsProjectReport(projectId);
        Pgs pgs = reportService.getPgsProjectInfo(pgsList.get(0).getProjectId());
        Map<String, Object> context = new HashMap<String, Object>();
        if(pgs==null){
            context.put("exists", 0);
        } else {
            context.put("exists", 1);
            context.put("pgs", pgs);
        }
        String path = ConstantsData.getLoginUser().getCompanyId() + "/PGS/project/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/PGS/print.vm";
		}
		context.put("pgsList", pgsList);
		returnToVelocity(path, context, projectId);
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
	@RequestMapping("printTBRifampicin_bak")
	@ResponseBody
	public void printTBRifampicin_bak(Integer appId, String dataKey, Integer projectId, Integer flag) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		TBRifampicin tbrifampicin = reportService.getTBRifampicinReport(dataKey, projectId, appId);
		tbrifampicin.setReport(CustomStringUtils.htmlbr(tbrifampicin.getReport()));
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		// 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
		if (StringUtils.isNotEmpty(report.getPrintContext())) {
			context.put("printContext", report.getPrintContext());
		}
		context.put("tbrifampicin", tbrifampicin);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printTBRifampicin(Integer appId, String dataKey, Integer projectId, Integer flag) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		TBRifampicin tbrifampicin = reportService.getTBRifampicinReport(dataKey, projectId, appId);
		tbrifampicin.setReport(CustomStringUtils.htmlbr(tbrifampicin.getReport()));
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		context.put("tbrifampicin", tbrifampicin);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@RequestMapping("printHBV_bak")
	@ResponseBody
	public void printHBV_bak(Integer appId, String dataKey, Integer projectId, Integer flag) {
		// 获取结果视图路径
		String path = null;
		if (flag == 0) { // 详细报告
			path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print_detail.vm";
			if (ReportAction.class.getResource("/templates/report/" + path) == null) {
				path = "default/" + appId + "/print_detail.vm";
			}
		} else { // 简要报告
			path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print_brief.vm";
			if (ReportAction.class.getResource("/templates/report/" + path) == null) {
				path = "default/" + appId + "/print_brief.vm";
			}
		}
		Map<String, Object> context = new HashMap<String, Object>();
		HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		// 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
		if (flag == 0 && StringUtils.isNotEmpty(report.getPrintContext())) {
			context.put("printContext", report.getPrintContext());
		}
		if (flag == 1 && StringUtils.isNotEmpty(report.getPrintSimple())) {
			context.put("printSimple", report.getPrintSimple());
		}
		context.put("hbv", hbv);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printHBV(Integer appId, String dataKey, Integer projectId, Integer flag) {
		// 获取结果视图路径
		String path = null;
		if (flag == 0) { // 详细报告
			path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print_detail.vm";
			if (ReportAction.class.getResource("/templates/report/" + path) == null) {
				path = "default/" + appId + "/print_detail.vm";
			}
		} else { // 简要报告
			path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print_brief.vm";
			if (ReportAction.class.getResource("/templates/report/" + path) == null) {
				path = "default/" + appId + "/print_brief.vm";
			}
		}
		Map<String, Object> context = new HashMap<String, Object>();
		HBV hbv = reportService.getHBVReport(dataKey, projectId, appId);
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		context.put("hbv", hbv);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@RequestMapping("printHCV_bak")
	@ResponseBody
	public void printHCV_bak(Integer appId, String dataKey, Integer projectId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		HCV hcv = reportService.getHCVReport(dataKey, projectId, appId);
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		// 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
		if (StringUtils.isNotEmpty(report.getPrintContext())) {
			context.put("printContext", report.getPrintContext());
		}
		context.put("hcv", hcv);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printHCV(Integer appId, String dataKey, Integer projectId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		HCV hcv = reportService.getHCVReport(dataKey, projectId, appId);
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		context.put("hcv", hcv);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
	}

	/**
	 * 打印EGFR
	 * 
	 * @param appId
	 * @param dataKey
	 * @param projectId
	 * @return
	 * @author lin
	 * @throws IOException
	 * @date 2016年3月21日下午2:51:25
	 */
	@ActionLog(value = "打印EGFR数据报告", button = "打印数据报告")
	@RequestMapping("printEGFR_bak")
	@ResponseBody
	public void printEGFR_bak(Integer appId, String dataKey, Integer projectId) throws IOException {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		EGFR egfr = reportService.getEGFRReport(dataKey, projectId, appId);
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		// 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
		if (StringUtils.isNotEmpty(report.getPrintContext())) {
			context.put("printContext", report.getPrintContext());
		}
		context.put("egfr", egfr);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
	}

	/**
	 * 打印EGFR
	 * 
	 * @param appId
	 * @param dataKey
	 * @param projectId
	 * @return
	 * @author lin
	 * @throws IOException
	 * @date 2016年3月21日下午2:51:25
	 */
	@ActionLog(value = "打印EGFR数据报告", button = "打印数据报告")
	@RequestMapping("printEGFR")
	@ResponseBody
	public void printEGFR(Integer appId, String dataKey, Integer projectId) throws IOException {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		EGFR egfr = reportService.getEGFRReport(dataKey, projectId, appId);

        // 如果是普陀区中心医院则对结果进行处理
        if (ConstantsData.getLoginCompanyId() == 25) {
            // 获取长度特征值
            String conclusion = egfr.getConclusion();
            String feature = null;
            String result = null;
            if (StringUtils.isNotBlank(conclusion)) {
                if (conclusion.startsWith("未检测到EGFR基因")) {
                    result = "未检测到";
                    feature = null;
                } else if (conclusion.startsWith("野生型")) {
                    result = "野生型";
                    feature = null;
                } else if (conclusion.startsWith("检测到EGFR") && !conclusion.contains("测序质量差")) {
                    result = conclusion.substring("检测到EGFR基因".length(), conclusion.lastIndexOf("突变"));
                    feature = egfr.getPos();
                }
                if (result != null) {
                    Medicine medicine = medicineService.getByFeatureAndResultDetail(feature, result, appId);
                    if (medicine == null) {
                        medicine = medicineService.getByFeatureAndResultDetail(null, "其他突变", appId);
                    }
                    if (medicine != null) {
                        egfr.setConclusion(egfr.getConclusion() + "\n" + medicine.getAdvice());
                    }
                }
            }
        }

		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		context.put("egfr", egfr);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@RequestMapping("printKRAS_bak")
	@ResponseBody
	public void printKRAS_bak(Integer appId, String dataKey, Integer projectId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		KRAS kras = reportService.getKRASReport(dataKey, projectId, appId);
		kras.setPosition(CustomStringUtils.htmlbr(kras.getPosition()));
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		// 首先检索该报告是否保存过，若保存过，则直接将保存内容返回
		if (StringUtils.isNotEmpty(report.getPrintContext())) {
			context.put("printContext", report.getPrintContext());
		}
		context.put("kras", kras);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
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
	@ResponseBody
	public void printKRAS(Integer appId, String dataKey, Integer projectId) {
		String path = ConstantsData.getLoginCompanyId() + "/" + appId + "/print.vm";
		if (ReportAction.class.getResource("/templates/report/" + path) == null) {
			path = "default/" + appId + "/print.vm";
		}
		Map<String, Object> context = new HashMap<String, Object>();
		KRAS kras = reportService.getKRASReport(dataKey, projectId, appId);
		kras.setPosition(CustomStringUtils.htmlbr(kras.getPosition()));
		Integer userId = ConstantsData.getLoginUserId();
		Integer fileId = dataService.getDataByKey(dataKey).getFileId();
		Report report = reportService.getReport(userId, appId, projectId, fileId, ReportType.DATA);
		context.put("kras", kras);
		context.put("report", report);
		returnToVelocity(path, context, projectId);
	}

	@ActionLog(value = "打印数据报告时点击保存按钮修改数据报告", button = "修改数据报告")
	@RequestMapping("updateContext")
	@ResponseBody
	public Integer updateContext(Report report) {
		return reportService.updateReport(report);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月25日上午11:10:56
	 * @description 修改pgs打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印Pgs数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updatePgsFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updatePgsFilling(Pgs pgs) {
		return reportService.updatePgsFilling(pgs);
	}

    /**
     * 
     * @author MQ
     * @date 2016年8月25日下午3:33:48
     * @description
     * @param pgs
     * @return
     *
     */
    @ActionLog(value = "打印Pgs项目报告时修改用户填写的信息", button = "修改项目报告")
    @RequestMapping("updatePgsProjectFilling")
    @ResponseBody
    public String updatePgsProjectFilling(Pgs pgs, Integer exists) {
        try {
            if (exists == 0) { // 代表第一次保存, 需要插入到mongo中
                pgs.setFlag(1);
                pgs.setId(null);
                return reportService.save(pgs).getId().toString();
            } else { // 不是第一次保存, 更新相应的额报告
                return reportService.updatePgsProjectilling(pgs).toString();
            }
        } catch (Exception e) {
            return "0";
        }
    }

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日上午11:10:56
	 * @description 修改hcv打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印Hcv数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateHcvFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateHcvFilling(HCV hcv) {
		return reportService.updateHcvFilling(hcv);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日下午14:07:56
	 * @description 修改egfr打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印Egfr数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateEgfrFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateEgfrFilling(EGFR egfr) {
		return reportService.updateEgfrFilling(egfr);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日下午14:07:56
	 * @description 修改kras打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印Kras数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateKrasFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateKrasFilling(KRAS kras) {
		return reportService.updateKrasFilling(kras);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日下午14:07:56
	 * @description 修改kras打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印TBRifampicin数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateTBRifampicinFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateTBRifampicinFilling(TBRifampicin tbRifampicin) {
		return reportService.updateTBRifampicinFilling(tbRifampicin);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日下午14:07:56
	 * @description 修改hbv简要打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印hbv简要数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateHbvBriefFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateHbvBriefFilling(HBV hbv) {
		return reportService.updateHbvBriefFilling(hbv);
	}

	/**
	 * 
	 * @author MQ
	 * @date 2016年7月27日下午14:07:56
	 * @description 修改hbv详细打印报告填写内容
	 *
	 */
	@ActionLog(value = "打印hbv详细数据报告时修改用户填写的信息", button = "修改数据报告")
	@RequestMapping("updateHbvDetailFilling")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Integer updateHbvDetailFilling(HBV hbv) {
		return reportService.updateHbvDetailFilling(hbv);
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
		String path = IconConstants.getCompanyPath(file);
		File targetFile = new File(path);
		if (targetFile.isFile()) {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
		} else {
			return null;
		}
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
		String path = IconConstants.getDeptPath(file);
		File targetFile = new File(path);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
	}

    /**
     * 获取报告图片
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "reportImage", method = RequestMethod.GET)
    public ResponseEntity<byte[]> reportImage(String file) throws IOException {
        String path = SparkPro.TOOLSPATH + File.separator + file;
        File targetFile = new File(path);
        // log.info("部门logo目录的绝对路径{}",targetFile.getAbsolutePath());
        return new ResponseEntity<byte[]>(
                FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

	/**
	 * 将Map中的数据返回到velocity模板中
	 */
	public void returnToVelocity(String path, Map<String, Object> context, Integer projectId) {
		try {
			Project project = projectService.selectByPrimaryKey(projectId);
			context.put("uploadPath", "/upload/");
			context.put("project", project);
			HttpServletResponse response = ConstantsData.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println(velocityUtil.mergeReportTemplate(path, context));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ActionLog(value = "打开报告列表", button = "百菌探报告")
	@RequestMapping("bsi/reportMain")
	public ModelAndView reportMain() {
		ModelAndView mv = new ModelAndView("bsi/report_main");
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> periodMap = taskService.findTaskPeriodNum(118, userId);
		List<String> batchList = dataService.getBatchList(userId);
		periodMap.put("uploaded", batchList.size());
		mv.addObject("periodMap", periodMap);
		mv.addObject("batchList", batchList);
		mv.addObject("nowDate", new Date());
		log.info("血流用户{}查看我的报告列表", ConstantsData.getLoginUserName());
		return mv;
	}

	@ActionLog(value = "获取所有数据任务列表", button = "百菌探报告")
	@RequestMapping("bsi/reportList")
	public ModelAndView reportList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
		ModelAndView mv = new ModelAndView("bsi/report_list");
		Page pager = new Page(page, size);
		Integer userId = ConstantsData.getLoginUserId();
		PageList<Task> pageList = taskService.findTasksByUser(pager, userId, 118);
		Map<String, Object> periodMap = taskService.findTaskPeriodNum(118, userId);
		mv.addObject("pageList", pageList);
		mv.addObject("periodMap", periodMap);
		mv.addObject("nowDate", new Date());
		log.info("血流用户{}查看我的报告列表", ConstantsData.getLoginUserName());
		return mv;
	}

    // @ActionLog(value = "报告菜单", button = "乳腺癌报告")
    // @RequestMapping("rocky/reportMain_bak")
    // public ModelAndView rockyReportMain_bak(@RequestParam(defaultValue = "1")
    // int page,
    // @RequestParam(defaultValue = "20") int size, String sample, String
    // condition,
    // @RequestParam(defaultValue = "updateDate") String sidx,
    // @RequestParam(defaultValue = "desc") String sord,
    // @RequestParam(name = "batches", required = false) ArrayList<String>
    // batches,
    // @RequestParam(name = "periods", required = false) ArrayList<Integer>
    // periods, String beginDate,
    // String endDate) throws ParseException {
    // ModelAndView mv = new ModelAndView("rocky/report/report_main");
    // Integer userId = ConstantsData.getLoginUserId();
    // Map<String, Object> periodMap =
    // taskService.findTaskPeriodNum(IconConstants.APP_ID_ROCKY, userId);
    // List<String> batchList = dataService.getBatchList(userId);
    // Page pager = new Page(page, size);
    // PageList<Task> pageList = taskService.findRockyTasks(pager, sample,
    // condition, sidx, sord, batches, periods,
    // beginDate == null ? null : new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").parse(beginDate + " 00:00:00"),
    // endDate == null ? null : new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").parse(endDate + " 23:59:59"));
    // mv.addObject("pageList", pageList);
    // periodMap.put("uploaded", batchList.size());
    // mv.addObject("periodMap", periodMap);
    // mv.addObject("batchList", batchList);
    // mv.addObject("sampleFilter", sample);
    // mv.addObject("conditionFilter", condition);
    // mv.addObject("sidx", sidx);
    // mv.addObject("sord", sord);
    // log.info("乳腺癌用户{}查看我的报告列表", ConstantsData.getLoginUserName());
    // return mv;
    // }

    @ActionLog(value = "报告菜单", button = "乳腺癌报告")
    @RequestMapping("rocky/reportMain")
    @ResponseBody
    public Map<String, Object> rockyReportMain(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size, String sample, String condition,
            @RequestParam(defaultValue = "updateDate") String sidx, @RequestParam(defaultValue = "desc") String sord,
            String batches, String periods,
            String beginDate, String endDate) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userId = ConstantsData.getLoginUserId();
        Map<String, Object> periodMap = taskService.findTaskPeriodNum(IconConstants.APP_ID_ROCKY, userId);
        List<String> batchList = dataService.getBatchList(userId);
        Page pager = new Page(page, size);

        ArrayList<String> queryBatches = null;
        if (StringUtils.isNotBlank(batches)) {
            queryBatches = new ArrayList<String>();
            if (batches.indexOf(",") > -1) {
                queryBatches.addAll(Arrays.asList(batches.split(",")));
            } else {
                queryBatches.add(batches);
            }
        }

        ArrayList<Integer> queryPeriods = null;
        if (StringUtils.isNotBlank(periods)) {
            queryPeriods = new ArrayList<Integer>();
            if (periods.indexOf(",") > -1) {
                for (String s : periods.split(",")) {
                    queryPeriods.add(Integer.parseInt(s));
                }
            } else {
                queryPeriods.add(Integer.parseInt(periods));
            }
        }

        if (StringUtils.isBlank(beginDate)) {
            beginDate = null;
        }
        if (StringUtils.isBlank(endDate)) {
            endDate = null;
        }

        PageList<Task> pageList = taskService.findRockyTasks(pager, sample, condition, sidx, sord, queryBatches,
                queryPeriods,
                beginDate == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginDate + " 00:00:00"),
                endDate == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate + " 23:59:59"));
        map.put("pageList", pageList);
        periodMap.put("uploaded", batchList.size());
        map.put("periodMap", periodMap);
        map.put("batchList", batchList);
        map.put("sampleFilter", sample);
        map.put("conditionFilter", condition);
        map.put("sidx", sidx);
        map.put("sord", sord);
        log.info("乳腺癌用户{}查看我的报告列表", ConstantsData.getLoginUserName());
        return map;
    }

	@ActionLog(value = "报告菜单", button = "乳腺癌报告")
	@RequestMapping("rocky/data/report")
	public ModelAndView rockyDataReport(String dataKey, Integer projectId, Integer appId) {
		ModelAndView mv = new ModelAndView("rocky/report/report_data_main");
		Rocky rocky = reportService.getRockyReport(dataKey, projectId, appId);
		mv.addObject("rocky", rocky);
		log.info("乳腺癌用户{}查看数据报告", ConstantsData.getLoginUserName());
		return mv;
	}

    /**
     * 
     * @author miaoqi
     * @date 2016年9月8日上午10:26:46
     * @description 获取乳腺癌数据报告
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     *
     */
    @ActionLog(value = "报告菜单", button = "乳腺癌报告")
    @RequestMapping("getRockyInfo")
    @ResponseBody
    public Map<String, Object> getRockyInfo(String dataKey, Integer projectId, Integer appId) {
        Map<String, Object> map = getCommonInfo(projectId);
        Rocky rocky = reportService.getRockyReport(dataKey, projectId, appId);
        map.put("rocky", rocky);
        log.info("乳腺癌用户{}查看数据报告", ConstantsData.getLoginUserName());
        return map;
    }

	/**
	 * 根据条件获取数据列表
	 * 
	 * @param session
	 * @param page
	 *            当前页
	 * @param size
	 *            每页行数
	 * @param condition
	 *            检索条件
	 * @param sort
	 *            排序字段 0:sortDate 1:sortBatch 2:sortName 3:sortPeriod
	 * @param sortType
	 *            排序类型
	 * @return
	 */
	@ActionLog(value = "条件检索任务列表", button = "报告搜索/分页")
	@RequestMapping("bsi/searchReportList")
	public ModelAndView reportList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size, String condition, @RequestParam(defaultValue = "0") int sort,
			@RequestParam(defaultValue = "desc") String sortDate, @RequestParam(defaultValue = "asc") String sortBatch,
			@RequestParam(defaultValue = "asc") String sortName, @RequestParam(defaultValue = "asc") String sortPeriod,
			String batch, String period, String beginDate, String endDate, String sampleName) {
		Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
		Matcher m = p.matcher(condition);
		StringBuffer con_sb = new StringBuffer();
		while (m.find()) {
			String rep = "\\\\" + m.group(0);
			m.appendReplacement(con_sb, rep);
		}
		m.appendTail(con_sb);
		ModelAndView mv = new ModelAndView("bsi/report_list");
		Page pager = new Page(page, size);
		PageList<Task> pageList = taskService.findTasksByUserCondition(pager, ConstantsData.getLoginUserId(), condition,
				sort, sortDate, sortBatch, sortName, sortPeriod, batch, period, beginDate, endDate, 118, sampleName);
		mv.addObject("pageList", pageList);
		log.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
		return mv;
	}

	@ActionLog(value = "获取所有数据任务列表", button = "报告详情翻页")
	@RequestMapping("bsi/batchReportList")
	public ModelAndView batchReportList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, String batch, Integer appId) {
		ModelAndView mv = new ModelAndView("bsi/report_data_pagination");
		Page pager = new Page(page, size);
		PageList<Task> pageList = taskService.findTasksByBatch(pager, ConstantsData.getLoginUserId(), appId, batch);
		mv.addObject("pageList", pageList);
		log.info("血流用户{}获取所有数据任务列表", ConstantsData.getLoginUserName());
		return mv;
	}

}
