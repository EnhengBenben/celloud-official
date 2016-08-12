package com.celloud.manager.service;

import java.math.BigDecimal;

import com.celloud.manager.model.Expenses;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;


/**
 * 消费记录服务接口
 * 
 * @author leamo
 * @date 2016年2月23日 下午6:42:28
 */
public interface ExpensesService {
	/**
	 * 新增赠予消费
	 * 
	 * @param from
	 * @param to
	 * @param toUserName
	 * @param amount
	 * @return
	 * @author lin
	 * @date 2016年8月3日下午6:38:15
	 */
	public Integer saveExpenses(Integer from, Integer to, String toUserName, BigDecimal amount);

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

    /**
     * 查询用户总消费金额
     * 
     * @param userId
     * @return
     * @author leamo
     * @date 2016年3月8日 上午11:26:58
     */
    public BigDecimal getUserTotalExpenses(Integer userId);

}
