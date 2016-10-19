package com.celloud.manager.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.alimail.AliEmail;
import com.celloud.manager.alimail.AliEmailUtils;
import com.celloud.manager.alimail.AliSubstitution;
import com.celloud.manager.alimail.EmailParams;
import com.celloud.manager.alimail.EmailType;
import com.celloud.manager.mapper.InvoiceMapper;
import com.celloud.manager.mapper.RechargeMapper;
import com.celloud.manager.model.Invoice;
import com.celloud.manager.model.Recharge;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.InvoiceService;


@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;
    @Resource
    private RechargeMapper rechargeMapper;
	@Resource
	private AliEmailUtils aliEmailUtils;

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
		AliEmail aliEmail = AliEmail.template(EmailType.INVOICE)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.INVOICE.username.name(), username));
		aliEmailUtils.simpleSend(aliEmail, aliEmailUtils.getFeedbackMailTo());
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
