package com.celloud.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.TaskPeriod;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.model.DataFile;
import com.celloud.model.Report;
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
    @Resource
    DataFileMapper dataMapper;
    @Resource
    ReportMapper reportMapper;
    @Override
    public Integer create(Task task) {
        task.setCreateDate(new Date());
        return taskMapper.insertSelective(task);
    }

    @Override
    public Task findFirstTask(Integer appId) {
        return taskMapper.findFirstTaskByAppId(appId, TaskPeriod.WAITTING);
    }

    @Override
    public Integer updateToRunning(Integer taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setState(TaskPeriod.RUNNING);
        return taskMapper.updateByPrimaryKeyWithBLOBs(task);
    }

    @Override
    public Task updateToDone(Integer appId, Integer projectId, String dataKey,
            String context) {
        taskMapper.updatePeriodByProAndData(projectId, dataKey,
                TaskPeriod.DONE, null, new Date());
        DataFile data = dataMapper.selectByDataKey(dataKey);
        Report report = new Report();
        report.setProjectId(projectId);
        report.setPeriod(ReportPeriod.COMPLETE);
        report.setState(DataState.ACTIVE);
        report.setFlag(ReportType.DATA);
        report.setFileId(data.getFileId());
        reportMapper.updateReportPeriod(report);
        Integer runNum = reportMapper.selectRunNumByPro(projectId,
                DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE);
        if (runNum == 0) {
            report.setFlag(ReportType.PROJECT);
            report.setEndDate(new Date());
            reportMapper.updateReportPeriod(report);
        }
        return taskMapper.findFirstTaskByAppId(appId, TaskPeriod.WAITTING);
    }

    @Override
    public Integer findRunningNumByAppId(Integer appId) {
        return taskMapper.findAppRunningNum(appId, TaskPeriod.RUNNING);
    }

    @Override
    public Map<String, Object> findTaskInfoByProId(Integer projectId) {
        return taskMapper.findTaskInfoByProId(projectId);
    }

    @Override
    public Task findTaskDataAppPro(String dataKey, Integer appId,
            Integer projectId) {
        return null;
    }

    @Override
    public Integer deleteTask(Integer projectId) {
        return null;
    }

}
