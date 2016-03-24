package com.celloud.manager.service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author han
 * @date 2016年3月10日 下午1:21:13
 */
public interface ConsoleService {
    /**
     * 总量统计
     * 
     * @param user
     * @return
     */
    public Map<String,Object> totalStatistics(Integer companyId);
    
    /**
     * 控制台数据统计
     *
     * @return
     * @author han
     * @date 2016年3月11日 下午1:59:33
     */
    public Map<String,Object> getStatisticsData(Integer companyId);
    
    public List<Map<String,Integer>> getBrowserData();

}
