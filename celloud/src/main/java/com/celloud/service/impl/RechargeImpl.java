package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.InvoiceState;
import com.celloud.constants.RechargeType;
import com.celloud.mapper.RechargeMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.Recharge;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.RechargeService;

@Service("rechargeServiceImpl")
public class RechargeImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer saveRecharge(BigDecimal amount, Integer userId, RechargeType rechargeType, Integer rechargeId) {
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
        recharge.setInvoiceState(rechargeType.invoice() ? InvoiceState.NO_INVOICE : InvoiceState.UN_INVOICE);
        return rechargeMapper.insertSelective(recharge);
    }

    @Override
    public PageList<Recharge> listRecharges(Page page) {
        List<Recharge> recharges = rechargeMapper.findRecharges(ConstantsData.getLoginUserId(), page);
        PageList<Recharge> pageRecharges = new PageList<>(page, recharges);
        return pageRecharges;
    }

}
