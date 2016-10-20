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
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Expenses;
import com.celloud.model.mysql.Price;
import com.celloud.model.mysql.Report;
import com.celloud.model.mysql.User;
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
    @Resource
    UserMapper userMapper;

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
        User user = userMapper.selectByPrimaryKey(userId);
        BigDecimal balances = user.getBalances();
        for (DataFile d : dataList) {
            expense.setId(null);
            int fileExpenseNum = expensesMapper.getFileExpenseNum(d.getFileId(),
                    appId, PriceType.isApp);
            BigDecimal amount = BigDecimal.ZERO;
            if (fileExpenseNum == 0) {
                amount = price == null ? BigDecimal.ZERO : price.getPrice();
            } else {
                expense.setRemark(ExpensesRemark.RERUN_FREE);
            }
            expense.setPrice(amount);
            expensesMapper.insertSelective(expense);
            expensesMapper.addFileExpenseRelat(expense.getId(), projectId,
                    d.getFileId(), d.getDataKey(), d.getFileName());
            balances = balances.subtract(amount);
        }
        user.setBalances(balances);
        userMapper.updateByPrimaryKeySelective(user);
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
            User user = userMapper.selectByPrimaryKey(userId);
            BigDecimal balances = user.getBalances();
            for (int i = 0; i < dataList.size(); i++) {
                DataFile d = dataList.get(i);
                BigDecimal amount = BigDecimal.ZERO;
                if (i == 0) {
                    fileExpenseNum = expensesMapper.getFileExpenseNum(
                            d.getFileId(), appId, PriceType.isApp);
                    if (fileExpenseNum == 0) {
                        amount = price == null ? BigDecimal.ZERO
                                : price.getPrice();
                    } else {
                        expense.setRemark(ExpensesRemark.RERUN_FREE);
                    }
                    expense.setPrice(amount);
                    expensesMapper.insertSelective(expense);
                    expenseId = expense.getId();
                }
                expensesMapper.addFileExpenseRelat(expenseId, projectId,
                        d.getFileId(), d.getDataKey(), d.getFileName());
                balances = balances.subtract(amount);
            }
            user.setBalances(balances);
            userMapper.updateByPrimaryKeySelective(user);
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
        BigDecimal total = expensesMapper.getTotalExpensesByUser(userId);
        return total == null || total.toString().equals("") ? new BigDecimal(0)
                : total;
    }
}
