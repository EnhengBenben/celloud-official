package com.celloud.manager.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.PriceType;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.PriceMapper;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Price;
import com.celloud.manager.service.AppService;

/**
 * APP操作实现类
 * 
 * @author leamo
 * @date 2016年3月21日 上午11:12:17
 */
@Service("appService")
public class AppServiceImpl implements AppService {
    @Resource
    private AppMapper appMapper;
    @Resource
    private PriceMapper priceMapper;

    @Override
    public List<App> appPriceList(Integer companyId) {
        return appMapper.appPriceList(companyId, AppOffline.ON,
                PriceType.isApp);
    }

    @Override
    public List<Price> appPriceHistory(Integer appId) {
        return priceMapper.getPriceList(appId, PriceType.isApp);
    }

    @Override
    public Integer updatePrice(Integer appId, BigDecimal price) {
        Price priceObjcx = new Price();
        priceObjcx.setItemId(appId);
        priceObjcx.setItemType(PriceType.isApp);
        priceObjcx.setExpireDate(new Date());
        priceMapper.updateByItemId(priceObjcx);
        priceObjcx.setId(null);
        priceObjcx.setCreateDate(new Date());
        priceObjcx.setExpireDate(null);
        priceObjcx.setPrice(price);
        return priceMapper.insertSelective(priceObjcx);
    }

}
