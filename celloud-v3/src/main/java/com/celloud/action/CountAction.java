package com.celloud.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.celloud.service.ReportService;
import com.celloud.utils.FileTools;

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

	/**
	 * 控制台统计
	 * 
	 * @return
	 */
	@RequestMapping("loginCount")
	public ModelAndView loginCount() {
		ModelAndView mv = new ModelAndView("count/count_user");
		Integer userId = ConstantsData.getLoginUserId();
		Integer countData = dataService.countData(userId);
		Long sumData = dataService.sumData(userId);
		Integer countReport = reportService.countReport(userId);
		Integer countApp = appService.countMyApp(userId);
		mv.addObject("countData", countData);
		mv.addObject("sumData", sumData);
		mv.addObject("countReport", countReport);
		mv.addObject("countApp", countApp);
		return mv;
	}

	/**
	 * 按照月份统计文件
	 * 
	 * @return
	 */
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
		return reportService.systemCount(userId);
		
	}

    /**
     * HBV 流程数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午2:55:57
     */
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
    @ResponseBody
    @RequestMapping("tbCompare")
    public String tbCompare(Integer appId, String path) {
        return FileTools.getLimitLines(path + appId, 1, 10);
    }

    /**
     * EGFR 和 KRAS 数据参数同比
     * 
     * @param appId
     * @param path
     * @param length
     * @return
     * @date 2016-1-9 下午3:09:58
     */
    @ResponseBody
    @RequestMapping("egfrCompare")
    public String egfrCompare(Integer appId, String path, String length) {
        return reportService.egfrCompare(appId, path, length);
    }

    /**
     * HCV 数据参数同比
     * 
     * @param appId
     * @param path
     * @return
     * @date 2016-1-9 下午3:20:50
     */
    @ResponseBody
    @RequestMapping("hcvCompare")
    public String hcvCompare(Integer appId, String path) {
        return reportService.hcvCompare(appId, path);
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
    @ResponseBody
    @RequestMapping("pgsCompare")
    public String pgsCompare(Integer appId, String path, String columns) {
        return reportService.pgsCompare(appId, path, columns);
    }
}
