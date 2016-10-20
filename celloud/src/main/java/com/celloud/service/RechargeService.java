package com.celloud.service;

import java.math.BigDecimal;

import com.celloud.constants.RechargeType;
import com.celloud.model.mysql.Recharge;
import com.celloud.page.Page;
import com.celloud.page.PageList;

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

    public PageList<Recharge> listRecharges(Page page);
}
