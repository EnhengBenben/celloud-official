package com.celloud.dao;

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

}
