package com.celloud.mapper;

import com.celloud.model.Task;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

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
    Task findFirstTaskByAppId(Integer appId, Integer state);

    /**
     * 获取APP正在运行的任务数
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:23:32
     */
    Integer findAppRunningNum(Integer appId, Integer state);

}