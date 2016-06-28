package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.celloud.constants.InvoiceState;
import com.celloud.constants.RechargeType;
import com.celloud.mapper.RechargeMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.Recharge;
import com.celloud.model.mysql.User;
import com.celloud.service.RechargeService;

public class RechargeImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer saveRecharge(BigDecimal amount, Integer userId,
            Integer rechargeType, Integer rechargeId) {
        User user = userMapper.selectByPrimaryKey(userId);
        BigDecimal balances = user.getBalances().add(amount);
        user.setBalances(balances);
        userMapper.updateByPrimaryKey(user);

        Recharge recharge = new Recharge();
        recharge.setAmount(amount);
        recharge.setBalances(balances);
        recharge.setCreateDate(new Date());
        recharge.setUserId(userId);
        recharge.setRechargeType(rechargeType);
        recharge.setRechargeId(rechargeId);
        recharge.setInvoiceState(RechargeType.getIcon(rechargeType)
                ? InvoiceState.NO_INVOICE : InvoiceState.UN_INVOICE);
        return rechargeMapper.insertSelective(recharge);
    }

}
