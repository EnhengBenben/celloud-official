package com.celloud.manager.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.PriceType;
import com.celloud.manager.mapper.ExpensesMapper;
import com.celloud.manager.mapper.PriceMapper;
import com.celloud.manager.mapper.ReportMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.Expenses;
import com.celloud.manager.model.User;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.ExpensesService;


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

	@Override
	public Integer saveExpenses(Integer from, Integer to, String toUserName, BigDecimal amount) {
		Expenses expense = new Expenses();
		expense.setItemId(0);
		expense.setItemType(PriceType.isGrant);
		expense.setUserId(from);
		expense.setCreateDate(new Date());
		expense.setRemark("【赠予用户：(" + to + "," + toUserName + ")】");
		expense.setPrice(amount);
		expensesMapper.insertSelective(expense);
		Integer expenseId = expense.getId();
		User user = userMapper.selectByPrimaryKey(from);
		BigDecimal balances = user.getBalances();
		balances = balances.subtract(amount);
		user.setBalances(balances);
		userMapper.updateByPrimaryKeySelective(user);
		return expenseId;
	}
}
