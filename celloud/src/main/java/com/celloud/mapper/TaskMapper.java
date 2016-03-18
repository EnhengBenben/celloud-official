package com.celloud.mapper;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Task;

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
}