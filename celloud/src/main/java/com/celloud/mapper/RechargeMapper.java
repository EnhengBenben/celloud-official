package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Recharge;
import com.celloud.page.Page;

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