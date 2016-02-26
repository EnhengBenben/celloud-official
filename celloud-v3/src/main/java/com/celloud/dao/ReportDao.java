package com.celloud.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

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

    /**
     * 根据id获取数据报告信息
     * 
     * @param id
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:42:32
     */
    public <T> T getDataById(Class<T> T, ObjectId id);

    /**
     * 获取某APP的所有信息（用于数据参数同比）
     * 
     * @param T
     * @param retrievedFields
     *            显示的字段
     * @return
     * @author leamo
     * @date 2016年1月17日 下午1:49:57
     */
    public <T> List<T> getAllAppList(Class<T> T, String[] retrievedFields);

    /**
     * 获取报告指定字段
     * 
     * @param T
     * @param dataKey
     * @param projectId
     * @param appId
     * @param retrievedFields
     *            显示的字段
     * @return
     * @author leamo
     * @date 2016年1月18日 下午3:13:24
     */
    public <T> T getDataFileds(Class<T> T, String dataKey, Integer projectId,
            Integer appId, String[] retrievedFields);

    /**
     * 获取指定字段
     * 
     * @param T
     * @param Field
     * @param condition
     * @return
     * @author leamo
     * @date 2016年1月18日 上午11:52:49
     */
    public <T> List<T> getDataFieldInAndOrder(Class<T> T, String[] fields,
            Map<String, List<String>> conditionMap, String sortField);

    /**
     * 修改数据
     * 
     * @param T
     * @param id
     * @param field
     * @param obj
     * @author leamo
     * @date 2016年2月1日 下午6:05:02
     */
    public <T> void editData(Class<T> T, ObjectId id, String field,
            Object obj);
    
    /**
     * 保存数据
     * 
     * @param T
     * @author leamo
     * @date 2016年2月23日 下午5:59:42
     */
    public <T> void saveData(T T);

}
