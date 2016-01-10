package com.celloud.dao;

import java.util.List;

/**
 * mongodb 操作接口
 * 
 * @author lin
 * @date 2016-1-8 下午4:12:24
 */
public interface ReportDao {

    /**
     * 从mongodb中查询数据报告的通用方法
     * 
     * @param T
     *            ：指定返回类型及表名
     * @param dataKey
     * @param projectId
     *            ：项目ID
     * @param appId
     * @return
     */
    public <T> T getDataReport(Class<T> T, String dataKey, Integer projectId,
            Integer appId);

    /**
     * 获取某人某个APP下的所有数据报告（用于统计）
     * 
     * @param T
     *            ：指定返回类型及表名
     * @param userId
     * @return
     * @date 2016-1-9 下午11:48:18
     */
    public <T> List<T> getAppList(Class<T> T, Integer userId);

}
