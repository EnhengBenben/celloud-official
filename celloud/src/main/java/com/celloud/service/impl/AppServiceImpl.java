package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import com.celloud.dao.AppDao;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.celloud.service.AppService;
import com.google.inject.Inject;

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
    public List<App> getAppsByFormat(Integer formatId) {
        return appDao.getAppsByFormat(formatId);
    }

    @Override
    public String getAppNameById(Long softwareId) {
        return appDao.getAppNameById(softwareId);
    }

    @Override
    public List<App> getAllSoftware() {
        return appDao.getAllSoftware();
    }

    @Override
    public Map<String, List<Classify>> getDoubleClassify(Integer classifyId) {
        return appDao.getDoubleClassify(classifyId);
    }

    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer pid,
            Integer companyId) {
        return appDao.getAppByClassify(classifyId, pid, companyId);
    }

    @Override
    public App getAppById(Integer id) {
        return appDao.getAppById(id);
    }

    @Override
    public List<App> getMyAppList(Integer userId) {
        return appDao.getMyAppList(userId);
    }

    @Override
    public List<Screen> getScreenByAppId(Integer id) {
        return appDao.getScreenByAppId(id);
    }

}
