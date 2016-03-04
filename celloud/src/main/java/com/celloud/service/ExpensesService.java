package com.celloud.service;

import com.celloud.model.mysql.Expenses;
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
     * 保存整个项目运行的消费记录（假设一个数据运行一次）
     * 
     * @param projectId
     *            运行的项目编号
     * @author leamo
     * @date 2016年3月4日 下午1:54:36
     */
    public void saveRunExpenses(Integer projectId);

    /**
     * 保存运行的消费记录
     * 
     * @param projectId
     *            运行的项目编号
     * @param dataKeys
     *            所运行的数据编号 格式：datakey1,datakey2
     * @author leamo
     * @date 2016年3月4日 下午1:54:36
     */
    public void saveRunExpenses(Integer projectId, String dataKeys);

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
    public PageList<Expenses> getRunExpensesList(Integer userId, Page page);

}
