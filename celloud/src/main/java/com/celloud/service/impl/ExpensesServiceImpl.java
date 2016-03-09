package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ExpensesRemark;
import com.celloud.constants.PriceType;
import com.celloud.constants.ReportType;
import com.celloud.mapper.ExpensesMapper;
import com.celloud.mapper.PriceMapper;
import com.celloud.mapper.ReportMapper;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Expenses;
import com.celloud.model.mysql.Price;
import com.celloud.model.mysql.Report;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ExpensesService;

/**
 * 消费服务类
 * 
 * @author leamo
 * @date 2016年2月24日 下午4:10:03
 */
@Service("expensesService")
public class ExpensesServiceImpl implements ExpensesService {
    @Resource
    ExpensesMapper expensesMapper;
    @Resource
    PriceMapper priceMapper;
    @Resource
    ReportMapper reportMapper;

    @Override
    public void saveProRunExpenses(Integer projectId, List<DataFile> dataList) {
        Report report = reportMapper.getReportByProjectId(projectId,
                ReportType.PROJECT);
        Integer appId = report.getAppId();
        Integer userId = report.getUserId();
        Price price = priceMapper.selectByItemId(appId, PriceType.isApp);
        Expenses expense = new Expenses();
        expense.setItemId(appId);
        expense.setItemType(PriceType.isApp);
        expense.setUserId(userId);
        expense.setCreateDate(new Date());
        for (DataFile d : dataList) {
            expense.setId(null);
            int fileExpenseNum = expensesMapper
                    .getFileExpenseNum(d.getFileId());
            if (fileExpenseNum == 0) {
                expense.setPrice(price.getPrice());
            } else {
                expense.setPrice(new BigDecimal(0));
                expense.setRemark(ExpensesRemark.RERUN_FREE);
            }
            int expenseId = expensesMapper.insertSelective(expense);
            expensesMapper.addFileExpenseRelat(expenseId, projectId,
                    d.getFileId(), d.getDataKey(), d.getFileName());
        }
    }

    @Override
    public void saveRunExpenses(Integer projectId, Integer appId,
            Integer userId, List<DataFile> dataList) {
        Price price = priceMapper.selectByItemId(appId, PriceType.isApp);
        Expenses expense = new Expenses();
        expense.setItemId(appId);
        expense.setItemType(PriceType.isApp);
        expense.setUserId(userId);
        expense.setCreateDate(new Date());
        int fileExpenseNum = 0;
        int expenseId = 0;
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                DataFile d = dataList.get(i);
                if (i == 0) {
                    fileExpenseNum = expensesMapper
                            .getFileExpenseNum(d.getFileId());
                    if (fileExpenseNum == 0) {
                        expense.setPrice(price.getPrice());
                    } else {
                        expense.setPrice(new BigDecimal(0));
                        expense.setRemark(ExpensesRemark.RERUN_FREE);
                    }
                    expenseId = expensesMapper.insertSelective(expense);
                    expensesMapper.addFileExpenseRelat(expenseId, projectId,
                            d.getFileId(), d.getDataKey(), d.getFileName());
                }
            }
        }
    }

    @Override
    public PageList<Expenses> getRunExpensesList(Integer userId, Page page) {
        List<Expenses> elist = expensesMapper.getAllRunExpensesByUser(page,
                userId, PriceType.isApp);
        return new PageList<>(page, elist);
    }

    @Override
    public BigDecimal getUserTotalExpenses(Integer userId) {
        return expensesMapper.getTotalExpensesByUser(userId);
    }
}
