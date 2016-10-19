package com.celloud.backstage.service.impl;

import java.text.SimpleDateFormat;
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
import com.celloud.backstage.utils.ResetPwdUtils;
import com.celloud.message.alimail.AliEmailUtils;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;
	@Resource
	private AliEmailUtils aliEmail;

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
    public int postInvoice(Invoice invoice, String postCompany, String postNumber, String email) {
        invoice.setUpdateDate(new Date());
        invoice.setRemark(postCompany + ":" + postNumber);
        invoice.setInvoiceState(1);
        int num = invoiceMapper.updateByPrimaryKeySelective(invoice);
        // 向email发送邮件通知发票寄出
        String invoiceContent = ResetPwdUtils.invoiceContent
                .replaceAll("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getCreateDate()))
                .replaceAll("money", invoice.getMoney().toString()).replace("postCompany", postCompany)
                .replace("postNumber", postNumber);
		aliEmail.simpleSend(ResetPwdUtils.invoiceTitle, invoiceContent, email);
        return num;
    }

}
