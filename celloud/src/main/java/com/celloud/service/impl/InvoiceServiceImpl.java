package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mail.EmailUtils;
import com.celloud.mapper.InvoiceMapper;
import com.celloud.mapper.RechargeMapper;
import com.celloud.model.mysql.Invoice;
import com.celloud.model.mysql.Recharge;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.sendcloud.SendCloudUtils;
import com.celloud.sendcloud.mail.Email;
import com.celloud.sendcloud.mail.Substitution;
import com.celloud.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private SendCloudUtils sendCloud;
    @Resource
    private EmailUtils emailUtils;

    @Override
    public int applyInvoice(Integer userId, String username, Invoice invoice, Integer[] ids) {
        // 申请时间
        invoice.setCreateDate(new Date());
        List<Recharge> recharges = rechargeMapper.findRechargesInIds(userId, ids);
        BigDecimal money = BigDecimal.ZERO;
        for(Recharge recharge : recharges){
            money = money.add(recharge.getAmount());
        }
        invoice.setMoney(money);
        // 插入数据库
        int num = invoiceMapper.insertAndSetId(invoice);
        // 更新充值记录表外键值
        rechargeMapper.updateRechargeInvoiceId(invoice.getId(), ids);
        // 向celloud发送邮件
        Email<?> context = Email.template(EmailType.INVOICE)
                .substitutionVars(Substitution.sub().set(EmailParams.INVOICE.username.name(), username))
                .to(emailUtils.getFeedbackMailTo());
        sendCloud.sendTemplate(context);
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
