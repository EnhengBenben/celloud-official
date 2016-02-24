package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.DiscountType;
import com.celloud.constants.ExpenseType;
import com.celloud.constants.PriceType;
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
import com.celloud.model.mongo.AppSnapshot;
import com.celloud.model.mongo.ExpenseDiscount;
import com.celloud.model.mongo.Expenses;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Price;
import com.celloud.model.mysql.Project;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.Task;
import com.celloud.model.mysql.User;
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

    @Override
    public Integer create(Task task) {
        task.setPeriod(TaskPeriod.WAITTING);
        task.setCreateDate(new Date());
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
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public synchronized Task updateToDone(Integer appId, Integer projectId,
            String dataKey, String dataKeys, String context) {
        Task task = taskMapper.findTaskByProData(projectId, dataKey);
        Date endDate = new Date();
        task.setEndDate(endDate);
        task.setPeriod(TaskPeriod.DONE);
        taskMapper.updateByPrimaryKeySelective(task);

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

        Price price = priceMapper.selectByItemId(appId, PriceType.isApp);
        if (price != null) {
            Integer userId = task.getUserId();
            Project pro = projectMapper.selectByPrimaryKey(projectId);
            User user = userMapper.selectByPrimaryKey(userId);

            // 增加消费记录
            AppSnapshot appSnapshot = new AppSnapshot();
            appSnapshot.setAppId(appId);
            appSnapshot.setAppName(price.getAppName());
            appSnapshot.setDataKey(dataKey);
            appSnapshot.setFiles(dataList);
            appSnapshot.setProjectId(projectId);
            appSnapshot.setProjectName(pro.getProjectName());
            appSnapshot.setStartDate(task.getStartDate());
            appSnapshot.setEndDate(endDate);
            appSnapshot.setUserId(userId);
            appSnapshot.setUserName(user.getUsername());

            BigDecimal appOldPrice = price.getPrice();
            BigDecimal appDiscountPrice = price.getDiscountPrice();
            BigDecimal realPrice = null;
            Float discountRate = price.getDiscountRate();
            List<ExpenseDiscount> discountList = new ArrayList<>();
            // 实际价格 = 原价 * app折扣
            if (appDiscountPrice == null || appDiscountPrice.equals("")) {
                realPrice = appOldPrice;
            } else {
                realPrice = appDiscountPrice;
            }
            if (discountRate != null) {
                ExpenseDiscount discount = new ExpenseDiscount();
                discount.setName(DiscountType.Limited_Time_Discount);
                discount.setDiscountRate(discountRate);
                discountList.add(discount);
            }
            Expenses expenses = new Expenses();
            expenses.setUserId(userId);
            expenses.setExpenseType(ExpenseType.isRun);
            expenses.setPrice(appOldPrice);
            expenses.setRealPrice(realPrice);
            expenses.setSnapshot(appSnapshot);
            expenses.setCreateDate(new Date());
            expenses.setDiscount(discountList);
            reportDao.saveData(expenses);
        }
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
        return null;
    }

    @Override
    public Integer deleteTask(Integer projectId) {
        return null;
    }

}
