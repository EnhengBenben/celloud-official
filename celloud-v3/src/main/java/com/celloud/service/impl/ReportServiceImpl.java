package com.celloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.DataState;
import com.celloud.constants.ReportType;
import com.celloud.constants.TimeState;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;

/**
 * 报告接口 实现类
 * 
 * @author han
 * @date 2015年12月25日 下午3:50:02
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Resource
    ReportMapper reportMapper;
    @Resource
    DataFileMapper dataMapper;
    @Resource
    AppMapper appMapper;
   
    Logger log = Logger.getLogger(this.getClass());

    @Override
    public Integer countReport(Integer userId) {
        return reportMapper.countReport(userId, DataState.ACTIVE, ReportType.PROJECT);
    }

    @Override
    public List<Map<String, String>> countReport(Integer userId, Integer time) {
        return reportMapper.countReportByTime(userId, time, DataState.ACTIVE, ReportType.PROJECT);
    }

    @Override
    public PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager, String condition, String start,
            String end, Integer appId) {
        long s = System.currentTimeMillis();
        System.out.println("【Service】 start " + s);
        List<Map<String, Object>> list = reportMapper.getReportList(userId, pager, condition, start, end, appId);
        long e = System.currentTimeMillis();
        System.out.println("【Service】 end " + e);
        System.out.println("【Service】 time: " + (e - s));
        return new PageList<>(pager, list);
    }
    @Override
	public List<Map<String, String>> countAppRunNum(Integer userId) {
		return reportMapper.countAppRunNumByUserId(userId);
    }

	@Override
	public Map<String, Object> systemCount(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/***按月统计的每月上传的:数据量\数据大小\报告数量\APP运行次数***/
		List<Map<String, String>> monthData = dataMapper.countDataByTime(userId, TimeState.MONTH,DataState.ACTIVE);
		List<Map<String, String>> monthSize = dataMapper.sumDataByTime(userId, TimeState.MONTH,DataState.ACTIVE);
		List<Map<String, String>> monthReport = reportMapper.countReportByTime(userId, TimeState.MONTH,DataState.ACTIVE, ReportType.DATA);
		List<Map<String, String>> monthApp = appMapper.countMyAppByTime(userId, TimeState.MONTH,DataState.ACTIVE,DataState.ACTIVE);
		/****按周统计每周上传:数据量\数据大小\报告数量\APP数量*****/
		List<Map<String, String>> weekData =dataMapper.countDataFileWeek(userId);
		List<Map<String, String>> weekReport =reportMapper.countReportWeekByUserId(userId);
		List<Map<String, String>> weekApp = appMapper.countWeekByUserId(userId);

		// 用户上传数据
		Long size = dataMapper.sumData(userId,DataState.ACTIVE);
		// 已运行、未运行
		Map<String, String> fileNum = dataMapper.countFileNumByUserId(userId);
		// 已添加App使用次数
		List<Map<String, String>> appRum = reportMapper.countAppRunNumByUserId(userId);

		map.put("monthData", monthData);
		map.put("weekData", weekData);
		map.put("monthSize", monthSize);

		map.put("monthReport", monthReport);
		map.put("monthApp", monthApp);
		map.put("appRum", appRum);
		map.put("size", size);
		map.put("weekReport", weekReport);
		map.put("weekApp", weekApp);
		map.put("fileNum", fileNum);
		
		log.info("monthData"+monthData.size());
		log.info("monthSize"+monthSize.size());
		log.info("monthReport"+monthReport.size());
		log.info("monthApp"+monthApp.size());
		log.info("appRum"+appRum.size());
		log.info("appRum"+size);
		log.info("fileNum"+fileNum.size());
		log.info("weekData"+weekData.size());
		log.info("weekReport"+weekReport.size());
		log.info("weekApp"+weekApp.size());

		return map;
	}
}
