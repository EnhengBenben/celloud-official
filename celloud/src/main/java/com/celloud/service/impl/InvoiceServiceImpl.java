package com.celloud.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.InvoiceMapper;
import com.celloud.model.mysql.Invoice;
import com.celloud.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public int applyInvoice(Invoice invoice, String ids[]) {
        // 申请时间
        invoice.setCreateDate(new Date());
        // 插入数据库
        int num = invoiceMapper.insertAndSetId(invoice);
        // 更新充值记录表外键值

        // 向celloud发送邮件

        return num;
    }

}
