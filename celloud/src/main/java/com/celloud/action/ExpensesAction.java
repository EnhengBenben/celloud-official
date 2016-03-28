package com.celloud.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Expenses;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ExpensesService;
import com.celloud.utils.ActionLog;

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
    @ActionLog(value = "打开消费记录页面", button = "消费记录查看详情")
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
    @ActionLog(value = "查看用户总消费金额", button = "消费记录")
    @RequestMapping("getTotalConsumption")
    @ResponseBody
    public BigDecimal getTotalConsumption() {
        return expensesService
                .getUserTotalExpenses(ConstantsData.getLoginUserId());
    }
}
