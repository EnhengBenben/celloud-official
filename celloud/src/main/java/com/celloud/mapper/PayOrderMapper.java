package com.celloud.mapper;

import com.celloud.model.mysql.PayOrder;

public interface PayOrderMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(PayOrder record);

    public int insertSelective(PayOrder record);

    public PayOrder selectByPrimaryKey(Integer id);

    public PayOrder selectByTradeNo(String tradeNo);

    public int updateByPrimaryKeySelective(PayOrder record);

    public int updateByPrimaryKey(PayOrder record);
}