package com.celloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.constants.PriceType;
import com.celloud.mapper.PriceMapper;
import com.celloud.model.mysql.Price;
import com.celloud.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Price getPriceByApp(Integer appId) {
        return priceMapper.selectByItemId(appId, PriceType.isApp);
    }

}
