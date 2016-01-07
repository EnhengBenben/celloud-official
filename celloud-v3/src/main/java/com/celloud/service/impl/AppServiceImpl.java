package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.AppIsAdd;
import com.celloud.constants.AppOffline;
import com.celloud.mapper.AppMapper;
import com.celloud.service.AppService;

/**
 * app接口实现类
 *
 * @author han
 * @date 2015年12月25日 下午5:54:15
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;

    @Override
    public Integer countMyApp(Integer userId) {
        return appMapper.countMyApp(userId, AppOffline.ON, AppIsAdd.ALREADY_ADDED);
    }

    @Override
    public List<Map<String, String>> countMyApp(Integer userId, Integer time) {
        return appMapper.countMyAppByTime(userId, time, AppOffline.ON, AppIsAdd.ALREADY_ADDED);
    }

    @Override
    public List<Map<String, String>> getRanAPP(Integer userId) {
        return appMapper.getRanAPP(userId);
    }

}
