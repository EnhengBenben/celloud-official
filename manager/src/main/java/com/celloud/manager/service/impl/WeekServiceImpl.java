package com.celloud.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.celloud.manager.mapper.WeekMapper;
import com.celloud.manager.model.Week;
import com.celloud.manager.service.WeekService;

/** 
 * @author MQ: 
 * @date 2016年8月8日 下午6:09:52 
 * @description 
 */
@Controller
public class WeekServiceImpl implements WeekService {

    @Resource
    private WeekMapper weekMapper;

    @Override
    public void add(Week week) {
        weekMapper.insertSelective(week);
    }

}
