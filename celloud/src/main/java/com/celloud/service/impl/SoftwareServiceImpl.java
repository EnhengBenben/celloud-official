package com.celloud.service.impl;

import java.util.List;

import com.celloud.dao.SoftwareDao;
import com.celloud.sdo.Software;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:39:56
 * @version Revision: 1.0
 */
public class SoftwareServiceImpl implements SoftwareService {
    @Inject
    private SoftwareDao appDao;
    @Override
    public List<Software> getAppsByFormat(Integer formatId) {
	return appDao.getAppsByFormat(formatId);
    }

    @Override
    public String getAppNameById(Long softwareId) {
	return appDao.getAppNameById(softwareId);
    }

}
