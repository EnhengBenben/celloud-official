package com.celloud.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Recharge;
import com.celloud.manager.page.Page;


public interface RechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    Recharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

    List<Recharge> findRecharges(@Param("userId") Integer userId, Page page);

    List<Recharge> findRechargesInIds(@Param("userId") Integer userId, @Param("ids") Integer[] ids);

    void updateRechargeInvoiceId(@Param("invoiceId") Integer invoiceId, @Param("rechargeIds") Integer[] rechargeIds);
}