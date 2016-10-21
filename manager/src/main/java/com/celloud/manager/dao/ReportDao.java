package com.celloud.manager.dao;

import java.util.List;
import java.util.Map;

/**
 * mongodb 操作接口
 * 
 * @author lin
 * @date 2016-1-8 下午4:12:24
 */
public interface ReportDao {

    /**
     * 
     * @description 根据条件查询数据, 默认按照创建时间倒序
     * @author miaoqi
     * @date 2016年10月19日上午10:32:53
     *
     * @param clazz
     * @param filters
     *            条件map,key是条件名,value是条件值
     * @return
     */
    public <T> List<T> queryByFilters(Class<T> clazz, Map<String, Object> filters, String[] fields);

}
