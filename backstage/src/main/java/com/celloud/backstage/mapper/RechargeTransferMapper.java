package com.celloud.backstage.mapper;

import com.celloud.backstage.model.RechargeTransfer;

public interface RechargeTransferMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeTransfer record);

    int insertSelective(RechargeTransfer record);

    RechargeTransfer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeTransfer record);

    int updateByPrimaryKey(RechargeTransfer record);
}