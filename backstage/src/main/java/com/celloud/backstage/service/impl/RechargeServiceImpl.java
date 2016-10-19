package com.celloud.backstage.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.InvoiceState;
import com.celloud.backstage.constants.RechargeType;
import com.celloud.backstage.mapper.RechargeMapper;
import com.celloud.backstage.mapper.RechargeTransferMapper;
import com.celloud.backstage.mapper.UserMapper;
import com.celloud.backstage.model.Recharge;
import com.celloud.backstage.model.RechargeTransfer;
import com.celloud.backstage.model.User;
import com.celloud.backstage.service.RechargeService;

@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private RechargeTransferMapper transferMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer saveRecharge(BigDecimal amount, Integer userId,
            RechargeType rechargeType, Integer rechargeId) {
        User user = userMapper.selectByPrimaryKey(userId);
        BigDecimal balances = user.getBalances().add(amount);
        user.setBalances(balances);
        userMapper.updateByPrimaryKeySelective(user);

        Recharge recharge = new Recharge();
        recharge.setAmount(amount);
        recharge.setBalances(balances);
        recharge.setCreateDate(new Date());
        recharge.setUserId(userId);
        recharge.setRechargeType(rechargeType.type());
        recharge.setRechargeId(rechargeId);
        recharge.setInvoiceState(!rechargeType.invoice()
                ? InvoiceState.NO_INVOICE : InvoiceState.UN_INVOICE);
        return rechargeMapper.insertSelective(recharge);
    }

    @Override
    public Integer createRechargeTransfer(RechargeTransfer transfer) {
        transfer.setCreateDate(new Date());
        transferMapper.insert(transfer);
        return saveRecharge(transfer.getAmount(), transfer.getUserId(),
                RechargeType.TRANSFER, transfer.getId());
    }

}
