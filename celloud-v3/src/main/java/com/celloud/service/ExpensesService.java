package com.celloud.service;

import com.celloud.model.mongo.AppExpenses;
import com.celloud.model.mongo.Expenses;
import com.celloud.page.Page;
import com.celloud.page.PageList;

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
     *            消费类型
     * @return
     * @author leamo
     * @date 2016年2月25日 下午3:11:02
     */
    public PageList<AppExpenses> getRunExpensesList(Integer userId, Page page);

}
