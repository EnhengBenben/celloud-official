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
    public Task findFirstTask(Integer appId);

    /**
     * 任务修改为正在运行
     * 
     * @param taskId
     * @return
     */
    public Integer updateToRunning(Integer taskId);

    /**
     * 任务运行结束，获取APP正在排队的任务
     * 
     * @param appId
     * @param projectId
     *            需改状态项目
     * @param dataKey
     *            需改状态数据
     * @param context
     *            项目报告内容
     * @return
     * @author leamo
     * @date 2016年1月14日 下午7:43:46
     */
    public Task updateToDone(Integer appId, Integer projectId,
            String dataKey, String dataKeys, String context);

    /**
     * 指定app正在运行的任务数
     * 
     * @param appId
     * @return
     */
    public Integer findRunningNumByAppId(Integer appId);

    /**
     * 根据proId获取报告信息、任务编号、app信息、数据个数
     * 
     * @param proId
     * @return
     */
    public Map<String, Object> findTaskInfoByProId(Integer projectId);

    /**
     * 根据dataKey appId proId获取任务信息
     * 
     * @param proId
     * @param appId
     * @param dataKey
     * @return
     */
    public Task findTaskDataAppPro(String dataKey, Integer appId,
            Integer projectId);

    /**
     * 根据proId删除未运行或正在运行的任务
     * 
     * @param proId
     * @return
     */
    public Integer deleteTask(Integer projectId);
}
