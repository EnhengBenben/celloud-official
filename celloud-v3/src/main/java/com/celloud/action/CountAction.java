package com.celloud.action;

import java.util.HashMap;
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

/**
 * 总览相关action
 * 
 * @author han
 * @date 2015年12月25日 下午3:34:05
 */
@Controller
@RequestMapping("count")
public class CountAction {
	private static final long serialVersionUID = 1L;
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
		List<Map<String, String>> monthData = dataService.countData(userId,
				TimeState.MONTH);
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
		List<Map<String, String>> monthData = dataService.sumData(userId,
				TimeState.MONTH);
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
		List<Map<String, String>> dayData = dataService.countData(userId,
				TimeState.DAY);
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
		List<Map<String, String>> dayData = dataService.sumData(userId,
				TimeState.DAY);
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
		List<Map<String, String>> dayData = reportService.countReport(userId,
				TimeState.MONTH);
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
		List<Map<String, String>> dayData = reportService.countReport(userId,
				TimeState.DAY);
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
		List<Map<String, String>> dayData = appService.countMyApp(userId,
				TimeState.MONTH);
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
		List<Map<String, String>> dayData = appService.countMyApp(userId,
				TimeState.DAY);
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
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = ConstantsData.getLoginUserId();
		List<Map<String, String>> monthData = dataService.countData(userId,
				TimeState.MONTH);
		List<Map<String, String>> dayData = dataService.countData(userId,
				TimeState.DAY);
		List<Map<String, String>> monthSize = dataService.sumData(userId,
				TimeState.MONTH);
		List<Map<String, String>> monthReport = reportService.countReport(
				userId, TimeState.MONTH);
		List<Map<String, String>> dayReport = reportService.countReport(userId,
				TimeState.DAY);
		List<Map<String, String>> dayApp = appService.countMyApp(userId,
				TimeState.MONTH);
		List<Map<String, String>> monthApp = appService.countMyApp(userId,
				TimeState.DAY);

		// 用户上传数据
		Long size = dataService.sumData(userId);

		// 已运行、未运行
		Map<String, String> fileNum = dataService.countUserRunFileNum(userId);
		// 已添加App使用次数
		List<Map<String, String>> appRum = reportService.countAppRunNum(userId);

		map.put("monthData", monthData);
		map.put("dayData", dayData);
		map.put("monthSize", monthSize);

		map.put("monthReport", monthReport);
		map.put("dayReport", dayReport);
		map.put("dayApp", dayApp);
		map.put("monthApp", monthApp);
		map.put("appRum", appRum);
		map.put("size", size);
		map.put("fileNum", fileNum);

		return map;
	}
}
