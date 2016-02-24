package com.celloud.service.impl;

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
        Integer appId;

        reportDao.saveData(expenses);
    }
}
