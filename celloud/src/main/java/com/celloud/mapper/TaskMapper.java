package com.celloud.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Task;
import com.celloud.page.Page;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    /**
     * 根据项目id 和 数据编号修改任务运行状态
     * 
     * @param projectId
     * @param dataKey
     * @param period
     * @param startDate
     * @param endDate
     * @return
     * @author leamo
     * @date 2016年1月14日 下午7:59:40
     */
    int updatePeriodByProAndData(@Param("projectId") Integer projectId,
            @Param("dataKey") String dataKey, @Param("period") Integer period,
            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);
    
	/**
	 * 根据projectId删除任务
	 * 
	 * @param projectId
	 * @return
	 * @author lin
	 * @date 2016年5月16日下午5:01:03
	 */
	public Integer deleteTask(@Param("projectId") Integer projectId, @Param("state") Integer state);

    int updateByPrimaryKey(Task record);

    /**
     * 获取等待运行APP时间最长的任务
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:15:21
     */
    Task findFirstTaskByAppId(@Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state);

    /**
     * 获取APP正在运行的任务数
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:23:32
     */
    Integer findAppRunningNum(@Param("appId") Integer appId,
            @Param("period") Integer period, @Param("state") Integer state);

    /**
     * 根据项目编号获取任务信息
     * 
     * @param projectId
     * @return
     * @author leamo
     * @date 2016年1月14日 下午7:11:18
     */
    Map<String, Object> findTaskInfoByProId(Integer projectId);

    /**
     * 根据报告、数据获取任务信息
     * 
     * @param projectId
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年1月16日 下午4:44:17
     */
    Task findTaskByProData(@Param("projectId") Integer projectId,
            @Param("dataKey") String dataKey);

    /**
     * 查询用户数据任务列表
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016年4月21日 下午2:32:22
     */
    List<Task> findTasksByUser(Page page, @Param("userId") Integer userId,
            @Param("state") Integer state);

    /**
     * 按条件检索数据列表
     * 
     * @param page
     * @param userId
     * @param condition
     * @param sort
     * @param sortDateType
     * @param sortNameType
     * @param state
     * @param reportType
     * @return
     */
    List<Task> findTasksByUserCondition(Page page,
            @Param("userId") Integer userId,
            @Param("condition") String condition, @Param("sort") Integer sort,
            @Param("sortDate") String sortDate,
            @Param("sortBatch") String sortBatch,
            @Param("sortName") String sortName,
            @Param("sortPeriod") String sortPeriod,
            @Param("state") Integer state);

    /**
     * 按照运行状态和参数查找任务
     * 
     * @param userId
     * @param state
     * @param period
     * @param params
     * @return
     * @author leamo
     * @date 2016年5月16日 下午3:27:39
     */
    Task findTaskByParamsAndPeriod(@Param("userId") Integer userId,
            @Param("state") Integer state, @Param("period") Integer period,
            @Param("params") String params);

    /**
     * 根据数据编号获取任务信息
     * 
     * @param state
     * @param dataKey
     * @return
     * @author leamo
     * @date 2016年5月16日 下午4:03:13
     */
    Task findTaskByDataKeyAndApp(@Param("state") Integer state,
            @Param("dataKey") String dataKey, @Param("appId") Integer appId);
}