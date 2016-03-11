package com.celloud.manager.service;

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

}
