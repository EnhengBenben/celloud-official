package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.InvoiceMapper;
import com.celloud.mapper.RechargeMapper;
import com.celloud.model.mysql.Invoice;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;
    @Resource
    private RechargeMapper rechargeMapper;

    @Override
    public int applyInvoice(Invoice invoice, Integer ids[]) {
        // 申请时间
        invoice.setCreateDate(new Date());
        // 插入数据库
        int num = invoiceMapper.insertAndSetId(invoice);
        // 更新充值记录表外键值
        rechargeMapper.updateRechargeInvoiceId(invoice.getId(), ids);
        // 向celloud发送邮件

        return num;
    }

    @Override
    public PageList<Invoice> getInvoiceList(Page page, Integer userId) {
        List<Invoice> list = invoiceMapper.pageQuery(page, userId);
        return new PageList<>(page, list);
    }

    @Override
    public Invoice getInvoiceDetail(Integer id) {
        return invoiceMapper.findById(id);
    }

}
