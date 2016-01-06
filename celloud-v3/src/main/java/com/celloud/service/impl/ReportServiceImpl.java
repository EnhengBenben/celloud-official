package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportType;
import com.celloud.mapper.ReportMapper;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ReportService;

/**
 * 报告接口 实现类 重构
 * 
 * @author han
 * @date 2015年12月25日 下午3:50:02
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Resource
    ReportMapper reportMapper;

    @Override
    public Integer countReport(Integer userId) {
        return reportMapper.countReport(userId, DataState.ACTIVE,
                ReportType.PROJECT);
    }

    @Override
    public List<Map<String, String>> countReport(Integer userId, Integer time) {
        return reportMapper.countReportByTime(userId, time, DataState.ACTIVE,
                ReportType.PROJECT);
    }

    @Override
    public PageList<Map<String, Object>> getReportPageList(Integer userId,
            Page pager, String condition, String start, String end,
            Integer appId) {
        List<Map<String, Object>> list = reportMapper.getReportList(userId,
                pager, condition, start, end, appId);
        return new PageList<>(pager, list);
    }
}
