package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.AppIsAdd;
import com.celloud.constants.AppOffline;
import com.celloud.constants.AppPermission;
import com.celloud.mapper.AppMapper;
import com.celloud.model.App;
import com.celloud.page.Page;
import com.celloud.page.PageList;
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
    public List<Map<String, String>> countMyApp(Integer userId, String time) {
        return appMapper.countMyAppByTime(userId, time, AppOffline.ON, AppIsAdd.ALREADY_ADDED);
    }

    @Override
    public List<Map<String, String>> getRanAPP(Integer userId) {
        return appMapper.getRanAPP(userId);
    }


    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer userId) {
        return appMapper.getAppByClassify(classifyId, userId, AppOffline.ON, AppPermission.PRIVATE,
                AppPermission.PUBLIC);
    }

    @Override
    public PageList<App> getAppPageListByClassify(Integer classifyId, Integer classifyPId, Integer userId,
            String sortField, String sortType, Page page) {
        List<App> list = appMapper.getAppPageListByClassify(classifyId, classifyPId, userId, sortField, sortType,
                AppOffline.ON, AppPermission.PRIVATE, AppPermission.PUBLIC, page);
        return new PageList<>(page, list);
    }

    @Override
    public App getAppById(Integer id, Integer userId) {
        return appMapper.getAppById(id, userId);
    }

    @Override
    public List<App> getMyAppList(Integer userId) {
        return appMapper.getMyAppList(userId, AppOffline.ON, AppIsAdd.ALREADY_ADDED);
    }

    @Override
    public Integer userAddApp(Integer userId, Integer appId) {
        return appMapper.userUpdateApp(userId, appId, AppIsAdd.ALREADY_ADDED);
    }

    @Override
    public Integer userRemoveApp(Integer userId, Integer appId) {
        return appMapper.userUpdateApp(userId, appId, AppIsAdd.NOT_ADDED);
    }

    @Override
    public List<App> findAppsByFormat(Integer userId, Integer formatId) {
        return appMapper.findAppsByFormat(userId, formatId, AppOffline.ON);
    }

    @Override
    public App findAppById(Integer appId) {
        return appMapper.selectByPrimaryKey(appId);
    }

}
