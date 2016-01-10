package com.celloud.service;

import java.util.Map;

import com.celloud.model.Task;

/**
 * 运行任务服务类
 * 
 * @author leamo
 * @date 2016-1-10 下午8:10:18
 */
public interface TaskService {
    /**
     * 新增任务
     * 
     * @param task
     * @return
     */
    public Integer create(Task task);

    /**
     * 获取等待运行APP时间最长的任务
     * 
     * @param appId
     * @return
     */
    public Task getFirstTask(Integer appId);

    /**
     * 任务修改为正在运行
     * 
     * @param taskId
     * @return
     */
    public Integer updateToRunning(Integer taskId);

    /**
     * 任务修改为运行结束
     * 
     * @param taskId
     * @return
     */
    public Integer updateToDone(Integer taskId);

    /**
     * 指定app正在运行的任务数
     * 
     * @param appId
     * @return
     */
    public Integer getRunningNumByAppId(Integer appId);

    /**
     * 根据proId获取报告信息、任务编号、app信息、数据个数
     * 
     * @param proId
     * @return
     */
    public Map<String, Object> getTaskInfoByProId(Integer proId);

    /**
     * 根据dataKey appId proId获取任务信息
     * 
     * @param proId
     * @param appId
     * @param dataKey
     * @return
     */
    public Task getTaskDataAppPro(String dataKey, Integer appId, Integer proId);

    /**
     * 根据proId删除未运行或正在运行的任务
     * 
     * @param proId
     * @return
     */
    public Integer deleteTask(Integer proId);
}
