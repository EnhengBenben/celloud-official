package com.celloud.manager.service;

import java.util.List;
import java.util.Map;

import com.celloud.manager.model.User;

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
    public Long totalDataSize(Integer companyId);
    
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
    /**
     * 按用户统计数据
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月15日 下午4:01:37
     */
    public List<Map<String,Object>> getBigCustomerDataCountByUser(Integer companyId);
    
    /**
     * 医院数据个数统计
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月16日 上午10:34:40
     */
    public List<Map<String,Object>> getBigCustomerDataCountByCompany(Integer companyId);
    
    /**
     * 大客户数据统计
     *
     * @return
     * @author han
     * @date 2016年3月16日 上午11:19:08
     */
    public List<Map<String,Object>> getBigCustomerDataCount();
    
    /**
     * 大客户下的用户列表
     *
     * @param companyId
     * @return
     * @author han
     * @date 2016年3月22日 上午11:28:21
     */
    public List<User> getUser(Integer companyId);
    
    public List<Map<String,Object>> getUserData(String userIds,String start,String end);
    
    /**
     * 
     * @author MQ
     * @date 2016年7月11日下午4:56:01
     * @description 获取一周的登录用户top10
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getWeekUserLogin(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月11日下午6:15:16
     * @description 获取每周App运行次数top10
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getWeekAppRun(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月12日上午10:00:33
     * @description 获取每周用户及数据大小top10
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getWeekDataSize(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午9:46:24
     * @description 获取历史每周登录次数
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekUserLogin(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午9:59:38
     * @description 获取历史每周活跃用户
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekActiveUser(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午10:43:31
     * @description 获取历史每周App运行次数
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppRun(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日上午11:16:36
     * @description 获取历史周App活跃数
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekAppActive(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年7月13日下午1:02:32
     * @description 获取历史周数据大小
     * @param companyId
     * @return
     *
     */
    public List<Map<String, Object>> getHistoryWeekDataSize(Integer companyId);
}
