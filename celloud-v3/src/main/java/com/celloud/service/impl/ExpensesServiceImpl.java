package com.celloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.dao.ReportDao;
import com.celloud.model.mongo.Expenses;
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
    ReportDao reportDao;

    @Override
    public void saveExpenses(Expenses expenses) {
        reportDao.saveData(expenses);
    }

    @Override
    public List<Expenses> getExpensesList(Integer userId, String expenseType) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("userId", userId);
        conditionMap.put("expenseType", expenseType);
        return reportDao.getDataListAndOrder(Expenses.class, conditionMap,
                "-createDate");
    }
}
