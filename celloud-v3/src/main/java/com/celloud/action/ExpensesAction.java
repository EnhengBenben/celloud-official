package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.ExpenseType;
import com.celloud.model.mongo.Expenses;
import com.celloud.service.ExpensesService;

/**
 * 费用管理
 * 
 * @author leamo
 * @date 2016年2月25日 下午2:29:59
 */
@Controller
@RequestMapping("Expenses")
public class ExpensesAction {
    Logger logger = LoggerFactory.getLogger(ExpensesAction.class);
    @Resource
    private ExpensesService expensesService;


    @RequestMapping("toExpenseList")
    public ModelAndView toExpenseList() {
        ModelAndView mv = new ModelAndView("expense/expense_pay_detail.jsp");
        List<Expenses> expenseList = expensesService.getExpensesList(
                ConstantsData.getLoginUserId(), ExpenseType.isRun);
        mv.addObject("expenseList", expenseList);
        return mv;
    }
}
