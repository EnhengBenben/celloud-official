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
    
}
