package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.TaskPeriod;
import com.celloud.dao.ReportDao;
import com.celloud.mapper.DataFileMapper;
import com.celloud.mapper.PriceMapper;
import com.celloud.mapper.ProjectMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ExpensesService;
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
    @Resource
    ProjectMapper projectMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PriceMapper priceMapper;
    @Resource
    ReportDao reportDao;
    @Resource
    ExpensesService expenseService;

    @Override
    public Integer create(Task task) {
        task.setPeriod(TaskPeriod.WAITTING);
        task.setCreateDate(new Date());
        task.setUpdateDate(new Date());
        return taskMapper.insertSelective(task);
    }

    @Override
    public Task findFirstTask(Integer appId) {
        return taskMapper.findFirstTaskByAppId(appId, TaskPeriod.WAITTING,
                DataState.ACTIVE);
    }

    @Override
    public Integer updateToRunning(Integer taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setPeriod(TaskPeriod.RUNNING);
        Date d = new Date();
        task.setStartDate(d);
        task.setUpdateDate(d);
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public synchronized Task updateToDone(Integer appId, Integer projectId,
            String dataKey, String dataKeys, String context) {
        // 修改任务状态
        Task task = taskMapper.findTaskByProData(projectId, dataKey);
        Date endDate = new Date();
        task.setEndDate(endDate);
        task.setUpdateDate(endDate);
        task.setPeriod(TaskPeriod.DONE);
        taskMapper.updateByPrimaryKeySelective(task);
        // 修改数据报告运行状态
        Report report = new Report();
        report.setProjectId(projectId);
        report.setPeriod(ReportPeriod.COMPLETE);
        report.setState(DataState.ACTIVE);
        report.setFlag(ReportType.DATA);
        List<DataFile> dataList = dataMapper.selectByDataKeys(dataKeys);
        for (DataFile data : dataList) {
            report.setFileId(data.getFileId());
            reportMapper.updateReportPeriod(report);
        }
        int runNum = reportMapper.selectRunNumByPro(projectId, DataState.ACTIVE,
                ReportType.DATA, ReportPeriod.COMPLETE);
        // 修改项目报告运行状态
        report.setFlag(ReportType.PROJECT);
        report.setFileId(null);
        report.setContext(context);
        if (runNum == 0) {
            report.setPeriod(ReportPeriod.COMPLETE);
            report.setEndDate(new Date());
        } else {
            report.setPeriod(ReportPeriod.RUNNING_HAVE_REPORT);
        }
        reportMapper.updateReportPeriod(report);
        // 保存消费记录
        expenseService.saveRunExpenses(projectId, appId, task.getUserId(),
                dataList);
        return task;
    }

    @Override
    public Integer findRunningNumByAppId(Integer appId) {
        return taskMapper.findAppRunningNum(appId, TaskPeriod.RUNNING,
                DataState.ACTIVE);
    }

    @Override
    public Map<String, Object> findTaskInfoByProId(Integer projectId) {
        return taskMapper.findTaskInfoByProId(projectId);
    }

    @Override
    public Task findTaskDataAppPro(String dataKey, Integer appId,
            Integer projectId) {
        return taskMapper.findTaskDataAppPro(DataState.ACTIVE, dataKey, appId,
                projectId);
    }

    @Override
    public Integer deleteTask(Integer projectId) {
        return taskMapper.deleteTask(projectId, DataState.DEELTED, new Date());
    }

    @Override
    public PageList<Task> findTasksByUser(Page page, Integer userId) {
        List<Task> list = taskMapper.findTasksByUser(page, userId,
                DataState.ACTIVE);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Task> findTasksByUserCondition(Page page, Integer userId,
            String condition, Integer sort, String sortDate, String sortBatch,
            String sortName, String sortPeriod) {
        List<Task> list = taskMapper.findTasksByUserCondition(page, userId,
                condition, sort, sortDate, sortBatch, sortName, sortPeriod,
                DataState.ACTIVE);
        return new PageList<>(page, list);
    }

    @Override
    public PageList<Task> findNextOrPrevTasks(Page page, Integer userId,
            String condition, Integer sort, String sortDate, String sortBatch,
            String sortName, String sortPeriod, Boolean isPrev,
            Integer totalPage) {
        List<Task> list = this.findTaskListByCondition(page, userId, condition,
                sort, sortDate, sortBatch, sortName, sortPeriod, isPrev,
                totalPage);
        if (list != null) {
            return new PageList<>(page, list);
        } else {
            return null;
        }
    }

    private List<Task> findTaskListByCondition(Page pager, Integer userId,
            String condition, Integer sort, String sortDate, String sortBatch,
            String sortName, String sortPeriod, Boolean isPrev,
            Integer totalPage) {
        Integer currentPage = pager.getCurrentPage();
        List<Task> list = new ArrayList<>();
        // 查询报告
        list = taskMapper.findTasksByUserCondition(pager, userId, condition,
                sort, sortDate, sortBatch, sortName, sortPeriod,
                DataState.ACTIVE);
        if (list != null) {
            Task t = list.get(0);
            if (t != null && t.getPeriod() == 2) {// 如果找到符合条件的返回
                return list;
            }
            if (isPrev && currentPage > 1) {// 向前翻页 取上一份报告 并且当前页码 大于1 向上还有元素
                Page pager1 = new Page(--currentPage, 1);
                return this.findTaskListByCondition(pager1, userId, condition,
                        sort, sortDate, sortBatch, sortName, sortPeriod, isPrev,
                        totalPage);
            } else if (!isPrev && currentPage < totalPage) {// 向后翻页 取下一份报告
                Page pager1 = new Page(++currentPage, 1);
                return this.findTaskListByCondition(pager1, userId, condition,
                        sort, sortDate, sortBatch, sortName, sortPeriod, isPrev,
                        totalPage);
            }
        }
        return null;
    }

    @Override
    public Integer addOrUpdateUploadTaskByParam(Task task, Boolean isUpdate) {
        Task task1 = taskMapper.findTaskByParamsAndPeriod(task.getUserId(),
                DataState.ACTIVE, task.getPeriod(), task.getParams());
        Integer result = 0;
        if (task1 == null) {
            task.setState(DataState.ACTIVE);
            Date d = new Date();
            task.setCreateDate(d);
            task.setUpdateDate(d);
            result = taskMapper.insert(task);
        } else {
            if (isUpdate) {
                task1.setDataKey(task.getDataKey());
                result = taskMapper.updateByPrimaryKeySelective(task1);
            }
        }
        return result;
    }

    @Override
    public Task findTaskByDataKeyAndApp(String dataKey, Integer appId) {
        return taskMapper.findTaskByDataKeyAndApp(DataState.ACTIVE, dataKey,
                appId, TaskPeriod.UPLOADING);
    }

    @Override
    public Integer updateTask(Task task) {
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public PageList<Task> findTasksByBatch(Page page, Integer userId,
            Integer appId, String batch, String dataKey) {
        List<Task> list = taskMapper.findTasksByBatch(page, userId, appId,
                TaskPeriod.DONE, DataState.ACTIVE, batch, dataKey);
        return new PageList<>(page, list);
    }
}
