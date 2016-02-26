package com.celloud.service;

import java.util.List;

import com.celloud.model.mongo.Expenses;

/**
 * 消费记录服务接口
 * 
 * @author leamo
 * @date 2016年2月23日 下午6:42:28
 */
public interface ExpensesService {

    /**
     * 保存消费记录
     * 
     * @param expenses
     * @author leamo
     * @date 2016年2月23日 下午6:43:52
     */
    public void saveExpenses(Expenses expenses);

    /**
     * 获取消费记录列表
     * 
     * @param userId
     * @param expenseType
     * @return
     * @author leamo
     * @date 2016年2月25日 下午3:11:02
     */
    public List<Expenses> getExpensesList(Integer userId, String expenseType);

}
