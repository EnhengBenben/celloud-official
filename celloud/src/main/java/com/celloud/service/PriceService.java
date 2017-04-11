package com.celloud.service;

import com.celloud.model.mysql.Price;

public interface PriceService {

    /**
     * 
     * @description 根据app获取app价格 
     * @author miaoqi
     * @date 2017年3月24日 下午5:22:49
     * @param appId
     */
    Price getPriceByApp(Integer appId);

}
