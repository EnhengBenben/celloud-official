package com.celloud.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Invoice;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.InvoiceService;
import com.celloud.utils.ActionLog;

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
    @ActionLog(value = "提交申请发票表单", button = "申请发票")
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
    @ActionLog(value = "用户查看发票列表", button = "发票管理")
    @RequestMapping("list")
    @ResponseBody
    public PageList<Invoice> list(Page page) {
        logger.info("用户{}查看发票列表", ConstantsData.getLoginUserName());
        Integer userId = ConstantsData.getLoginUserId();
        return invoiceService.getInvoiceList(page, userId);
    }
    
    @ActionLog(value = "用户查看发票详情", button = "发票详情")
    @RequestMapping("detail")
    @ResponseBody
    public Invoice detail(Integer id) {
        logger.info("用户{}查看发票详情", ConstantsData.getLoginUserName());
        return invoiceService.getInvoiceDetail(id);
    }
}
