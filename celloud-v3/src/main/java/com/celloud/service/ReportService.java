package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 报告接口
 * 
 * @author han
 * @date 2015年12月25日 下午3:47:07
 */
public interface ReportService {
    /**
     * 
     * 统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public Integer countReport(Integer userId);

    /**
     * 
     * 按时间统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public List<Map<String, String>> countReport(Integer userId, Integer time);

    /**
     * 报告检索
     * 
     * @param userId
     *            ：用户ID
     * @param pager
     *            ：分页类
     * @param condition
     *            ：检索条件
     * @param start
     *            ：开始时间
     * @param end
     *            ：结束时间
     * @param appId
     *            ：APPID
     * @return
     * @date 2016-1-5 下午3:26:05
     */
    PageList<Map<String, Object>> getReportPageList(Integer userId, Page pager,
            String condition, String start, String end, Integer appId);
}
