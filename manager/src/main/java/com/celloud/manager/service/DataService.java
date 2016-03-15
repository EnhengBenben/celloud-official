package com.celloud.manager.service;

import java.util.List;
import java.util.Map;

public interface DataService {
    /**
     * 数据个数
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月14日 下午4:23:07
     */
    public int totalDataNum(Integer companyId);
    /**
     * 数据大小
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月14日 下午4:23:23
     */
    public long totalDataSize(Integer companyId);
    
    /**
     * 获取所有大客户数据按月份统计
     *
     * @return
     * @author han
     * @date 2016年3月14日 下午6:37:58
     */
    public Map<String,Object> getBigCustomerDataCountByMon();
    
    /**
     * 获取某个大客户数据按月份统计
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月14日 下午6:38:32
     */
    public List<Map<String,Object>> getBigCustomerDataCountByMon(Integer companyId,String order);
    
}
