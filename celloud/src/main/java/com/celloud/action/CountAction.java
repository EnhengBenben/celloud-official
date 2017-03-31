package com.celloud.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.TimeState;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ReportService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;

/**
 * 总览相关action
 * 
 * @author han
 * @date 2015年12月25日 下午3:34:05
 */
@Controller
@RequestMapping("count")
public class CountAction {
	Logger logger = LoggerFactory.getLogger(CountAction.class);
	@Resource
	private DataService dataService;
	@Resource
	private ReportService reportService;
	@Resource
	private AppService appService;
	@Resource
	private ExpensesService expensesService;

	/**
	 * 控制台统计
	 * 
	 * @return
	 */
    @ActionLog(value = "打开celloud控制台首页，获取统计信息", button = "总览")
	@RequestMapping("loginCount")
	@ResponseBody
	public Map<String, Object> loginCount() {
		Integer userId = ConstantsData.getLoginUserId();
		Integer countData = dataService.countData(userId);
		Long sumData = dataService.sumData(userId);
		Integer countReport = reportService.countReport(userId);
		if (countReport == null) {
			countReport = 0;
		}
		Integer countApp = appService.countMyApp(userId);
		DecimalFormat df = new DecimalFormat("#.00");
		String size = null;
		String format = null;
		if (sumData == null || sumData.equals(0l)) {
			size = "0";
			format = "K";
		} else if (sumData > 1073741824) {
			size = df.format(sumData / 1073741824f);
			format = "G";
		} else if (sumData > 1048576) {
			size = df.format(sumData / 1048576f);
			format = "M";
		} else {
			size = df.format(sumData / 1024f);
			format = "K";
		}
		BigDecimal countExpense = expensesService.getUserTotalExpenses(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("countData", countData);
		map.put("sumData", size);
		map.put("format", format);
		map.put("countApp", countApp);
		map.put("countReport", countReport);
		map.put("countExpense", countExpense.longValue());
		return map;
	}

	/**
	 * 按照月份统计文件
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按月份统计的数据量曲线图", button = "数据量每月")
	@ResponseBody
	@RequestMapping("fileMonthCount")
	public List<Map<String, String>> fileMonthCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> monthData = dataService.countData(userId, TimeState.MONTH);
		return monthData;
	}

	/**
	 * 按照月份统计文件大小
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按月份统计的数据大小曲线图", button = "总资源每月")
	@ResponseBody
	@RequestMapping("fileSizeMonthCount")
	public List<Map<String, String>> fileSizeMonthCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> monthData = dataService.sumData(userId, TimeState.MONTH);
		return monthData;
	}

	/**
	 * 按照天统计文件
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按天统计的数据量曲线图", button = "数据量每天")
	@ResponseBody
	@RequestMapping("fileDayCount")
	public List<Map<String, String>> fileDayCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = dataService.countData(userId, TimeState.DAY);
		return dayData;
	}

	/**
	 * 按照天统计文件大小
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按天统计的数据大小曲线图", button = "总资源每天")
	@ResponseBody
	@RequestMapping("fileSizeDayCount")
	public List<Map<String, String>> fileSizeDayCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = dataService.sumData(userId, TimeState.DAY);
		return dayData;
	}

	/**
	 * 按照月份统计报告
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按月份统计的报告量曲线图", button = "报告量每月")
	@ResponseBody
	@RequestMapping("reportMonthCount")
	public List<Map<String, String>> reportMonthCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = reportService.countReport(userId, TimeState.MONTH);
		return dayData;
	}

	/**
	 * 按照天统计报告
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按天统计的报告量曲线图", button = "报告量每天")
	@ResponseBody
	@RequestMapping("reportDayCount")
	public List<Map<String, String>> reportDayCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = reportService.countReport(userId, TimeState.DAY);
		return dayData;
	}

	/**
	 * 按照月份统计报告
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按月份统计的已添加APP数量曲线图", button = "已添加APP每月")
	@ResponseBody
	@RequestMapping("appMonthCount")
	public List<Map<String, String>> appMonthCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = appService.countMyApp(userId, TimeState.MONTH);
		return dayData;
	}

	/**
	 * 按照天统计报告
	 * 
	 * @return
	 */
    @ActionLog(value = "获取按天统计的已添加APP数量曲线图", button = "已添加APP每天")
	@ResponseBody
	@RequestMapping("appDayCount")
	public List<Map<String, String>> appDayCount() {
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> dayData = appService.countMyApp(userId, TimeState.DAY);
		return dayData;
	}

	/**
	 * 统计统计
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("systemCount")
	public Map<String, Object> systemCount() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = reportService.systemCount(userId);
		;
		// Long size = Long.parseLong(map.get("size").toString());
		// Map<String,String>fileMap = (Map<String, String>) map.get("fileNum");
		// String fNumStr = fileMap.get("runFileNum");
		// logger.info(fNumStr);
		// int runFileNum = Integer.parseInt(fNumStr);
		// String funNum = fileMap.get("fileNum");
		// logger.info(funNum);
		// int fileNum =Integer.parseInt(funNum);
		return map;

	}

	/**
	 * HBV 流程数据参数同比
	 * 
	 * @param appId
	 * @param path
	 * @return
	 * @date 2016-1-9 下午2:55:57
	 */
    @ActionLog(value = "获取HBV数据参数同比信息", button = "HBV数据报告")
	@ResponseBody
	@RequestMapping("hbvCompare")
	public String hbvCompare(Integer appId, String path) {
		return reportService.hbvCompare(appId, path);
	}

	/**
	 * TB 数据参数同比
	 * 
	 * @param appId
	 * @param path
	 * @return
	 * @date 2016-1-9 下午3:16:01
	 */
    @ActionLog(value = "获取TB数据参数同比信息", button = "TB数据报告")
	@ResponseBody
	@RequestMapping("tbCompare")
	public String tbCompare() {
        return reportService.tbrifampicinCompare();
	}
    
    /**
     * KRAS 数据参数同比
     * 
     * @param length
     * @return
     * @date 2016-1-9 下午3:09:58
     */
    @ActionLog(value = "获取KRAS数据参数同比信息", button = "EGFR/KRAS数据报告")
    @ResponseBody
    @RequestMapping("krasCompare")
    public String krasCompare(Integer length) {
        return reportService.krasCompare(length);
    }

    /**
     * EGFR 数据参数同比
     * 
     * @param length
     * @return
     * @date 2016-1-9 下午3:09:58
     */
    @ActionLog(value = "获取EGFR数据参数同比信息", button = "EGFR/KRAS数据报告")
    @ResponseBody
    @RequestMapping("egfrCompare")
    public String egfrCompare(Integer length) {
        return reportService.egfrCompare(length);
    }

	/**
	 * HCV 数据参数同比
	 * 
	 * @param appId
	 * @param path
	 * @return
	 * @date 2016-1-9 下午3:20:50
	 */
    @ActionLog(value = "获取HCV数据参数同比信息", button = "HCV数据报告")
	@ResponseBody
	@RequestMapping("hcvCompare")
	public String hcvCompare() {
		return reportService.hcvCompare();
	}

	/**
	 * PGS 数据参数同比
	 * 
	 * @param appId
	 * @param path
	 * @param columns
	 * @return
	 * @date 2016-1-9 下午3:27:25
	 */
    @ActionLog(value = "获取PGS数据参数同比信息", button = "PGS数据报告")
	@ResponseBody
	@RequestMapping("pgsCompare")
	public String pgsCompare(Integer appId, String columns) {
		return reportService.pgsCompare(appId, columns);
	}

    /**
     * split 数据参数同比
     * 
     * @param id
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:55:50
     */
    @ActionLog(value = "获取split数据参数同比信息", button = "split数据报告")
    @ResponseBody
    @RequestMapping("splitCompare")
    public Map<String, List<List<Float>>> splitCompare(String id) {
        Map<String, List<List<Float>>> countMapList = reportService
                .splitCompare(new ObjectId(id));
        return countMapList;
    }

	/**
	 * HBV 统计
	 * 
	 * @return
	 * @date 2016-1-10 上午12:26:32
	 */
    @ActionLog(value = "达安旗下用户获取HBV流程统计信息", button = "统计模块")
	@RequestMapping("hbvCount")
	public ModelAndView hbvCount() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = reportService.hbvCount(userId);
		return new ModelAndView("count/count_hbv").addObject("map", map);
	}

	/**
	 * PGS 统计
	 * 
	 * @return
	 * @date 2016-1-10 上午12:44:21
	 */
    @ActionLog(value = "嘉宝旗下用户获取PGS流程统计信息", button = "统计模块")
	@RequestMapping("pgsCount")
	public ModelAndView pgsCount() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = reportService.pgsCount(userId);
		return new ModelAndView("count/count_pgs").addObject("map", map);
	}

	/**
	 * CMP 统计
	 * 
	 * @return
	 * @date 2016-1-10 上午1:00:07
	 */
    @ActionLog(value = "燕达旗下用户获取CMP流程统计信息", button = "统计模块")
	@RequestMapping("cmpCount")
	public ModelAndView cmpCount() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = reportService.cmpCount(userId);
		return new ModelAndView("count/count_cmp").addObject("map", map);
	}

	/**
	 * 统计模块Excel文件下载
	 * 
	 * @param response
	 * @param fileName
	 * @date 2016-1-10 下午10:18:26
	 */
    @ActionLog(value = "统计模块Excel文件下载", button = "下载统计信息")
	@RequestMapping("download")
	public void download(HttpServletResponse response, String fileName) {
		if (StringUtils.isNotEmpty(fileName))
			FileTools.fileDownLoad(response, PropertiesUtil.outputPath + fileName);
	}
}
