package com.celloud.backstage.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.celloud.backstage.constants.InvoiceState;
import com.celloud.backstage.constants.RechargeType;
import com.celloud.backstage.mapper.RechargeMapper;
import com.celloud.backstage.mapper.UserMapper;
import com.celloud.backstage.model.Recharge;
import com.celloud.backstage.model.User;
import com.celloud.backstage.service.RechargeService;


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
        recharge.setInvoiceState(rechargeType == RechargeType.PRESENT
                ? InvoiceState.NO_INVOICE : InvoiceState.UN_INVOICE);
        return rechargeMapper.insertSelective(recharge);
    }

}
