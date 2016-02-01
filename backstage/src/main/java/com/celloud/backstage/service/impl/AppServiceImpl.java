package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.AppOffline;
import com.celloud.backstage.constants.AppPermission;
import com.celloud.backstage.mapper.AppMapper;
import com.celloud.backstage.model.App;
import com.celloud.backstage.service.AppService;

@Service("appService")
public class AppServiceImpl implements AppService{
    
    @Resource
    private AppMapper appMapper;

    @Override
    public List<App> getAppListByCompany(Integer companyId) {
        if(companyId==null){
            return null;
        }
        return appMapper.getAppListByCompany(companyId,AppPermission.PRIVATE, AppOffline.ON);
    }
        

    @Override
    public List<App> getAppListPulbicAdded() {
        return appMapper.getAppListAdded(AppPermission.PUBLIC, AppOffline.ON);
    }
    
}
