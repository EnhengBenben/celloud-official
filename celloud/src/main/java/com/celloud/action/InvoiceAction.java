package com.celloud.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Invoice;
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

    @ActionLog(value = "提交申请发票表单", button = "申请发票")
    @RequestMapping("applyInvoice")
    @ResponseBody
    public int applyInvoice(Invoice invoice, String[] ids) {
        logger.info("用户{}申请发票", ConstantsData.getLoginUserName());
        invoice.setUserId(ConstantsData.getLoginUserId());
        return invoiceService.applyInvoice(invoice, ids);
    }
}
