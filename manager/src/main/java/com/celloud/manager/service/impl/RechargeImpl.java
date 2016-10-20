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
import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.constants.InvoiceState;
import com.celloud.manager.constants.RechargeType;
import com.celloud.manager.mapper.RechargeMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.Recharge;
import com.celloud.manager.model.User;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.RechargeService;
import com.celloud.manager.utils.DateUtil;

@Service("rechargeServiceImpl")
public class RechargeImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private UserMapper userMapper;
	@Resource
	private AliEmailUtils emailUtils;

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
		int result = rechargeMapper.insertSelective(recharge);
		//构造邮件内容
		AliEmail aliEmail = AliEmail.template(EmailType.RECHARGE)
				.substitutionVars(AliSubstitution.sub().set(EmailParams.RECHARGE.date.name(), stringDate)
						.set(EmailParams.RECHARGE.adCharge.name(), amount.toString())
						.set(EmailParams.RECHARGE.cashBalance.name(), balances.toString()));
		emailUtils.simpleSend(aliEmail, user.getEmail());
		return result;
    }

    @Override
    public PageList<Recharge> listRecharges(Page page) {
        List<Recharge> recharges = rechargeMapper.findRecharges(ConstantsData.getLoginUserId(), page);
        PageList<Recharge> pageRecharges = new PageList<>(page, recharges);
        return pageRecharges;
    }

}
