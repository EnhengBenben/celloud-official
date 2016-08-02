package com.celloud.manager.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.model.Invoice;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.InvoiceService;


/**
 * @author MQ:
 * @date 2016年6月30日 上午9:59:17
 * @description 发票管理
 */
@Controller
@RequestMapping("invoice")
public class InvoiceAction {

    private Logger logger = LoggerFactory.getLogger(InvoiceAction.class);

    @Resource
    private InvoiceService invoiceService;

    /**
     * @author MQ
     * @date 2016年6月30日下午3:37:46
     * @description 申请发票
     * @return
     *
     */
    @RequestMapping("apply")
    @ResponseBody
    public int apply(Invoice invoice, Integer[] rechargeIds) {
        logger.info("用户{}申请发票", ConstantsData.getLoginUserName());
        invoice.setUserId(ConstantsData.getLoginUserId());
        String username = ConstantsData.getLoginUserName();
        Integer userId = ConstantsData.getLoginUserId();
        return invoiceService.applyInvoice(userId, username, invoice, rechargeIds);
    }

    /**
     * @author MQ
     * @date 2016年6月30日下午3:37:34
     * @description 分页查询发票
     *
     */
    @RequestMapping("list")
    public ModelAndView list(Page page) {
        logger.info("用户{}查看发票列表", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("expense/expense_invoice");
        Integer userId = ConstantsData.getLoginUserId();
        PageList<Invoice> invoicePageList = invoiceService.getInvoiceList(page, userId);
        mv.addObject("invoicePageList", invoicePageList);
        return mv;
    }
    
    @RequestMapping("detail")
    @ResponseBody
    public Invoice detail(Integer id) {
        logger.info("用户{}查看发票详情", ConstantsData.getLoginUserName());
        return invoiceService.getInvoiceDetail(id);
    }
}
