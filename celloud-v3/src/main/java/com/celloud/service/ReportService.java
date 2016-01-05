package com.celloud.service;

import java.util.List;
import java.util.Map;

/**
 * 报告接口
 *
 * @author han
 * @date 2015年12月25日 下午3:47:07
 */
public interface ReportService {
    /**
     * 
     * (重构)统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public Integer countReport(Integer userId);

    /**
     * 
     * (重构)按时间统计个人报告数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午3:47:42
     */
    public List<Map<String, String>> countReport(Integer userId, Integer time);
}
