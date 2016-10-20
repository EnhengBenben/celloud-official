package com.celloud.manager.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.model.Expenses;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.ExpensesService;


/**
 * 费用管理
 * 
 * @author leamo
 * @date 2016年2月25日 下午2:29:59
 */
@Controller
@RequestMapping("expense")
public class ExpensesAction {
    Logger logger = LoggerFactory.getLogger(ExpensesAction.class);
    @Resource
    private ExpensesService expensesService;

    /**
     * 跳转消费记录页面
     * 
     * @param page
     * @param size
     * @return
     * @author leamo
     * @date 2016年2月29日 下午2:20:24
     */
    @RequestMapping("toRunExpenseList")
    public ModelAndView toRunExpenseList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        ModelAndView mv = new ModelAndView("expense/expense_pay_detail");
        Page pager = new Page(page, size);
        Integer userId = ConstantsData.getLoginUserId();
        PageList<Expenses> expensePageList = expensesService
                .getRunExpensesList(userId, pager);
        mv.addObject("expensePageList", expensePageList);
        mv.addObject("expenseTotal",
                expensesService.getUserTotalExpenses(userId));
        logger.info("用户{}查看消费记录", userId);
        return mv;
    }

    /**
     * 获取用户总消费金额
     * 
     * @return
     * @author leamo
     * @date 2016年3月8日 上午11:31:22
     */
    @RequestMapping("getTotalConsumption")
    @ResponseBody
    public BigDecimal getTotalConsumption() {
        return expensesService
                .getUserTotalExpenses(ConstantsData.getLoginUserId());
    }
}
