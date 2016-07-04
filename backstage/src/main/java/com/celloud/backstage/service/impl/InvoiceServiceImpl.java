package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.InvoiceMapper;
import com.celloud.backstage.model.Invoice;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    public PageList<Map<String,String>> getInvoiceListByKeyword(Page page, Integer userId, String keyword) {
        List<Map<String, String>> list = invoiceMapper.pageQuery(page, userId, keyword);
        return new PageList<>(page, list);
    }

    @Override
    public Map<String, String> getInvoiceDetail(Integer id) {
        return invoiceMapper.findById(id);
    }

    @Override
    public int postInvoice(Invoice invoice, String remark, String email) {
        invoice.setUpdateDate(new Date());
        invoice.setRemark("快递单号:" + remark);
        invoice.setInvoiceState(1);
        int num = invoiceMapper.updateByPrimaryKeySelective(invoice);
        // 向email发送邮件通知发票寄出
        return num;
    }

}
