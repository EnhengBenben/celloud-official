package com.celloud.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * mongodb 操作接口
 * 
 * @author lin
 * @date 2016-1-8 下午4:12:24
 */
public interface ReportDao {

    /**
     * 
     * @author MQ
     * @date 2016年8月26日上午11:00:31
     * @description 保存一个对象并返回key
     * @param t
     * @return
     *
     */
    public <T> Key<T> saveObj(T t);

    /**
     * @author MQ
     * @date 2016年7月7日下午3:02:47
     * @description 根据项目id查询所有的数据报告
     * @param clazz
     * @param projectId
     * @return
     *
     */
    public <T> List<T> getDataByProjectId(Class<T> clazz, Integer projectId);
    
    /**
     * 
     * @author MQ
     * @date 2016年8月25日下午2:53:25
     * @description 根据项目id查询项目报告
     * @param clazz
     *            类型
     * @param proejctId
     *            项目id
     * @return 项目对象
     *
     */
    public <T> T getProjectByProjectId(Class<T> clazz, Integer projectId);

    /**
     * HCV数据参数同比直接在mongo中分组
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> Iterable getHCVCompare(Class<T> clazz);
    
    /**
     * TBRifampicin数据参数同比直接在mongo中分组排序
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> Iterable getTBRifampicinCompare(Class<T> clazz);
    
    /**
     * 从数据库中直接进行分组排序
     * @param clazz
     * @param length
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> Iterable getEGFROrKRASCompare(Class<T> clazz, Integer length);
    
    /**
     * 根据appid获取某几列的字段值
     * @param appId
     * @param columns
     * @return
     */
    public <T> List<T> getDataFieldsByAppId(Class<T> clazz, Integer appId, String[] columns);

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
    public <T> UpdateResults editData(Class<T> T, ObjectId id, String field,
            Object obj);

    /**
     * 保存数据
     * 
     * @param t
     * @author leamo
     * @date 2016年2月23日 下午5:59:42
     */
    public <T> void saveData(T t);

    /**
     * 根据获取全部信息列表
     * 
     * @param T
     * @param conditionMap
     *            筛选条件
     * @param sortField
     *            排序字段
     * @return
     * @author leamo
     * @date 2016年2月25日 下午3:59:53
     */
    public <T> List<T> getDataListAndOrder(Class<T> T,
            Map<String, Object> conditionMap, String sortField);

    /**
     * 分页查询数据
     * 
     * @param T
     * @param conditionMap
     * @param sortField
     * @param page
     * @return
     * @author leamo
     * @date 2016年2月26日 下午5:14:46
     */
    public <T> PageList<T> getDataPageListAndOrder(Class<T> T,
            Map<String, Object> conditionMap, String sortField, Page page);

    /**
     * 根据条件查询TBINH报告的数量
     * 
     * @param integer
     * @param geneName
     * @param isWild
     * @return
     */
    public Integer getTBINHisWild(Integer userId, String simpleGeneName, Integer isWild);
    
    /**
     * 根据bean从mongo中获取全部数据
     * @param clazz
     * @return
     */
    public <T> List<T> getAllByClass(Class<T> clazz);

    /**
     * 删除数据
     * 
     * @param T
     * @param dataKey
     * @param projectId
     * @param appId
     * @return
     * @author leamo
     * @date 2016年5月27日 上午11:34:15
     */
    public <T> void delete(Class<T> T, String dataKey, Integer projectId,
            Integer appId);

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
    public <T> List<T> queryByFilters(Class<T> clazz, Map<String, Object> filters);

}
