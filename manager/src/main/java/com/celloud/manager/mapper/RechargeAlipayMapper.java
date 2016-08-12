package com.celloud.manager.mapper;

import com.celloud.manager.model.RechargeAlipay;

public interface RechargeAlipayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeAlipay record);

    int insertSelective(RechargeAlipay record);

    RechargeAlipay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeAlipay record);

    int updateByPrimaryKey(RechargeAlipay record);

    RechargeAlipay selectByTradeNo(String out_trade_no);
}