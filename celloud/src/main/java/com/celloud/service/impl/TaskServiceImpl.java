package com.celloud.service.impl;

import java.util.Map;

import com.celloud.dao.TaskDao;
import com.celloud.sdo.Task;
import com.celloud.service.TaskService;
import com.google.inject.Inject;

/**
 * 任务服务类
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-11-3下午4:07:53
 * @version Revision: 1.0
 */
public class TaskServiceImpl implements TaskService {
    @Inject
    TaskDao taskDao;
    @Override
    public Long create(Task task) {
        return taskDao.create(task);
    }

    @Override
    public Task getFirstTask(Long appId) {
        return taskDao.getFirstTask(appId);
    }

    @Override
    public Integer updateToRunning(Long taskId) {
        return taskDao.updateToRunning(taskId);
    }

    @Override
    public Integer updateToDone(Long taskId) {
        return taskDao.updateToDone(taskId);
    }

    @Override
    public Integer getRunningNumByAppId(Long appId) {
        return taskDao.getRunningNumByAppId(appId);
    }

    @Override
    public Map<String, Object> getTaskInfoByProId(Long proId) {
        return taskDao.getTaskInfoByProId(proId);
    }

    @Override
    public Task getTaskDataAppPro(String dataKey, Long appId, Long proId) {
        return taskDao.getTaskDataAppPro(dataKey, appId, proId);
    }

}
