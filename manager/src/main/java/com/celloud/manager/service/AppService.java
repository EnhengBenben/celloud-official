package com.celloud.manager.service;

import java.math.BigDecimal;
import java.util.List;

import com.celloud.manager.model.App;
import com.celloud.manager.model.Price;

/**
 * APP管理操作service
 * 
 * @author leamo
 * @date 2016年3月21日 上午11:12:55
 */
public interface AppService {
    /**
     * 大客户的APP价格列表
     * 
     * @param companyId
     * @return
     * @author leamo
     * @date 2016年3月18日 下午4:47:11
     */
    public List<App> appPriceList(Integer companyId);

    /**
     * APP价格历史
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016年3月18日 下午4:47:11
     */
    public List<Price> appPriceHistory(Integer appId);

    /**
     * 修改app价格
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016年3月21日 下午2:30:07
     */
    public Integer updatePrice(Integer appId, BigDecimal price);
}
