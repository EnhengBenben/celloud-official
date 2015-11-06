package com.celloud.service;

import com.celloud.sdo.Task;
import com.celloud.service.impl.TaskServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 操作tb_task接口
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午3:24:15
 * @version Revision: 1.0
 */
@ImplementedBy(TaskServiceImpl.class)
public interface TaskService {
    /**
     * 新增任务
     * 
     * @param task
     * @return
     */
    public Long create(Task task);

    /**
     * 获取等待运行APP时间最长的任务
     * 
     * @param appId
     * @return
     */
    public Task getFirstTask(Long appId);

    /**
     * 任务修改为正在运行
     * 
     * @param taskId
     * @return
     */
    public Integer updateToRunning(Long taskId);

    /**
     * 任务修改为运行结束
     * 
     * @param taskId
     * @return
     */
    public Integer updateToDone(Long taskId);

    /**
     * 指定app正在运行的任务数
     * 
     * @param appId
     * @return
     */
    public Integer getRunningNumByAppId(Long appId);
}
