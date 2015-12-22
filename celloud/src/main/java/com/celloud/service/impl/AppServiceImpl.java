package com.celloud.service.impl;

import java.util.List;

import com.celloud.dao.AppDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.celloud.service.AppService;
import com.google.inject.Inject;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:39:56
 * @version Revision: 1.0
 */
public class AppServiceImpl implements AppService {
    @Inject
    private AppDao appDao;

    @Override
    public List<App> getAppsByFormat(Integer formatId,Integer userId) {
        return appDao.getAppsByFormat(formatId, userId);
    }

    @Override
    public String getAppNameById(Long softwareId) {
        return appDao.getAppNameById(softwareId);
    }

    @Override
    public Classify getClassifyById(Integer id) {
        return appDao.getClassifyById(id);
    }

    @Override
    public List<Classify> getClassify(Integer pid) {
        return appDao.getClassify(pid);
    }

    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer companyId) {
        return appDao.getAppByClassify(classifyId, companyId);
    }

    @Override
    public App getAppById(Integer id, Integer userId) {
        return appDao.getAppById(id, userId);
    }

    @Override
    public PageList<App> getAppPageListByClassify(Integer classifyId,
            Integer classifyPId, Integer companyId, String sortField,
            String sortType, Page page) {
        return appDao.getAppPageListByClassify(classifyId, classifyPId,
                companyId, sortField, sortType, page);
    }

    @Override
    public List<App> getMyAppList(Integer userId) {
        return appDao.getMyAppList(userId);
    }

    @Override
    public List<Screen> getScreenByAppId(Integer id) {
        return appDao.getScreenByAppId(id);
    }

    @Override
    public Integer userAddApp(Integer userId, Integer appId) {
        return appDao.userAddApp(userId, appId);
    }

    @Override
    public Integer userRemoveApp(Integer userId, Integer appId) {
        return appDao.userRemoveApp(userId, appId);
    }

}
