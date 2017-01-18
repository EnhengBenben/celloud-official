package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.InvoiceState;
import com.celloud.constants.RechargeType;
import com.celloud.mapper.RechargeMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.message.category.MessageCategoryCode;
import com.celloud.message.category.MessageCategoryUtils;
import com.celloud.model.mysql.Recharge;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.service.RechargeService;
import com.celloud.utils.DateUtil;
import com.celloud.wechat.ParamFormat;
import com.celloud.wechat.ParamFormat.Param;
import com.celloud.wechat.constant.WechatParams;

@Service("rechargeServiceImpl")
public class RechargeImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private UserMapper userMapper;
	@Resource
	private MessageCategoryUtils mcu;

    @Override
    public Integer saveRecharge(BigDecimal amount, Integer userId, RechargeType rechargeType, Integer rechargeId) {
        User user = userMapper.selectByPrimaryKey(userId);
        BigDecimal balances = user.getBalances().add(amount);
        user.setBalances(balances);
        userMapper.updateByPrimaryKeySelective(user);

        Recharge recharge = new Recharge();
        recharge.setAmount(amount);
        recharge.setBalances(balances);
		Date date = new Date();
		String stringDate = DateUtil.getDateToString(date, DateUtil.YMDHMS);
        recharge.setCreateDate(new Date());
        recharge.setUserId(userId);
        recharge.setRechargeType(rechargeType.type());
        recharge.setRechargeId(rechargeId);
        recharge.setInvoiceState(!rechargeType.invoice() ? InvoiceState.NO_INVOICE : InvoiceState.UN_INVOICE);

		//构造邮件内容
		AliEmail aliEmail = AliEmail.template(EmailType.RECHARGE)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.RECHARGE.date.name(), stringDate)
						.set(EmailParams.RECHARGE.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.RECHARGE.adCharge.name(), amount.toString())
						.set(EmailParams.RECHARGE.cashBalance.name(), balances.toString()));
		//构造微信发送消息
		Param wechat = ParamFormat.param()
				.set(WechatParams.BALANCE_CHANGE.first.name(), "您好，您的 CelLoud 账户有一笔现金充值到账。", "#222222")
				.set(WechatParams.BALANCE_CHANGE.date.name(), stringDate, null)
				.set(WechatParams.BALANCE_CHANGE.adCharge.name(), amount.toString(), null)
				.set(WechatParams.BALANCE_CHANGE.cashBalance.name(), balances.toString(), "#222222");
		mcu.sendMessage(userId, MessageCategoryCode.BALANCES, aliEmail, wechat, null, null);
        return rechargeMapper.insertSelective(recharge);
    }

    @Override
    public PageList<Recharge> listRecharges(Page page) {
        List<Recharge> recharges = rechargeMapper.findRecharges(ConstantsData.getLoginUserId(), page);
        PageList<Recharge> pageRecharges = new PageList<>(page, recharges);
        return pageRecharges;
    }

}
