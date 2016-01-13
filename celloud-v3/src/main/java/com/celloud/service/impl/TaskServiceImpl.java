package com.celloud.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.TaskState;
import com.celloud.mapper.TaskMapper;
import com.celloud.model.Task;
import com.celloud.service.TaskService;

/**
 * 任务服务类
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午4:07:53
 * @version Revision: 1.0
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Resource
    TaskMapper taskMapper;
    @Override
    public Integer create(Task task) {
        task.setCreateDate(new Date());
        return taskMapper.insertSelective(task);
    }

    @Override
    public Task getFirstTask(Integer appId) {
        return taskMapper.findFirstTaskByAppId(appId, TaskState.WAITTING);
    }

    @Override
    public Integer updateToRunning(Integer taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setState(TaskState.RUNNING);
        return taskMapper.updateByPrimaryKeyWithBLOBs(task);
    }

    @Override
    public Integer updateToDone(Integer taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setState(TaskState.DONE);
        return taskMapper.updateByPrimaryKeyWithBLOBs(task);
    }

    @Override
    public Integer getRunningNumByAppId(Integer appId) {
        return taskMapper.findAppRunningNum(appId, TaskState.RUNNING);
    }

    @Override
    public Map<String, Object> getTaskInfoByProId(Integer proId) {
        return null;
    }

    @Override
    public Task getTaskDataAppPro(String dataKey, Integer appId, Integer proId) {
        return null;
    }

    @Override
    public Integer deleteTask(Integer proId) {
        return null;
    }

}
