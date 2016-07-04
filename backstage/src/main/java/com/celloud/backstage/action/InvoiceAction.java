package com.celloud.backstage.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.model.Invoice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.InvoiceService;

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
     * @date 2016年6月30日下午3:37:34
     * @description 分页查询发票
     *
     */
    @RequestMapping("list")
    public ModelAndView list(Page page, String keyword) {
        logger.info("用户{}查看发票列表", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("expense/invoice_main");
        Integer userId = ConstantsData.getLoginUserId();
        PageList<Invoice> invoicePageList = invoiceService.getInvoiceListByKeyword(page, userId, keyword);
        mv.addObject("invoicePageList", invoicePageList);
        mv.addObject("keyword", keyword);
        return mv;
    }
    
    @RequestMapping("detail")
    public ModelAndView detail(Integer invoiceId) {
        logger.info("用户{}查看发票详情", ConstantsData.getLoginUserName());
        ModelAndView mv = new ModelAndView("expense/invoice_detail");
        Map<String, String> invoice = invoiceService.getInvoiceDetail(invoiceId);
        if (StringUtils.isNotBlank(invoice.get("remark"))) {
            String postCompany = invoice.get("remark").split(":")[1];
            String postNumber = invoice.get("remark").split(":")[2];
            mv.addObject("postCompany", postCompany);
            mv.addObject("postNumber", postNumber);
        }
        mv.addObject("invoice", invoice);
        return mv;
    }

    @RequestMapping("edit")
    @ResponseBody
    public int edit(Invoice invoice, String email, String postCompany, String postNumber) {
        logger.info("用户{}邮寄发票", ConstantsData.getLoginUserName());
        return invoiceService.postInvoice(invoice, postCompany + ":" + postNumber, email);
    }
}
