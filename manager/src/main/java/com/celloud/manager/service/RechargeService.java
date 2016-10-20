package com.celloud.manager.service;

import java.math.BigDecimal;

import com.celloud.manager.constants.RechargeType;
import com.celloud.manager.model.Recharge;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

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
     * @author leamo
     * @date 2016年6月27日 下午2:17:01
     */
    public Integer saveRecharge(BigDecimal amount, Integer userId,
            RechargeType rechargeType, Integer rechargeId);

    public PageList<Recharge> listRecharges(Page page);
}
