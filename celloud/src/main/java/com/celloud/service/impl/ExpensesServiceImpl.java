package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ExpensesMapper;
import com.celloud.model.mysql.Expenses;
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

    @Override
    public void saveRunExpenses(Integer projectId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveRunExpenses(Integer projectId, String dataKeys) {
        // TODO Auto-generated method stub

    }

    @Override
    public PageList<Expenses> getRunExpensesList(Integer userId, Page page) {
        return null;
    }

}
