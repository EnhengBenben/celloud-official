package com.celloud.backstage.service;

import java.math.BigDecimal;

import com.celloud.backstage.constants.RechargeType;
import com.celloud.backstage.model.RechargeTransfer;

/**
 * 账单管理接口
 * 
 * @author leamo
 * @date 2016年6月27日 下午2:12:40
 */
public interface RechargeService {
    /**
     * 新增充值记录
     * 
     * @param bill
     * @author leamo
     * @date 2016年6月27日 下午2:17:01
     */
    public Integer saveRecharge(BigDecimal amount, Integer userId,
            RechargeType rechargeType, Integer rechargeId);

    /**
     * 创建转账充值账单
     * 
     * @param transfer
     * @return
     * @author leamo
     * @date 2016年7月12日 下午4:01:22
     */
    public Integer createRechargeTransfer(RechargeTransfer transfer);
}
