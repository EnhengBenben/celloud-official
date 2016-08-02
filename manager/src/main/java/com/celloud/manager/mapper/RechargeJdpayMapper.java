package com.celloud.manager.mapper;

import com.celloud.manager.model.RechargeJdpay;

public interface RechargeJdpayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeJdpay record);

    int insertSelective(RechargeJdpay record);

    RechargeJdpay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeJdpay record);

    int updateByPrimaryKey(RechargeJdpay record);

	RechargeJdpay selectByTradeNo(String v_oid);
}