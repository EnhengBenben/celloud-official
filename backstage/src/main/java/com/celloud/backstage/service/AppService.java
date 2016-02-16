package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.App;

public interface AppService {
    /**
     * 获取私有的app列表
     *
     * @param companyId 大客户编号
     * @param attribute 软件可添加权限 0-public（所有人可添加）  1-private（软件所有公司的用户可添加）
     * @return
     * @author han
     * @date 2016年1月29日 下午2:08:29
     */
    
    List<App> getAppListByCompany(Integer companyId);
    
    /**
     * 获取公共的非工具类appList
     *
     * @return
     * @author han
     * @date 2016年1月29日 下午2:12:11
     */
   
    List<App> getAppListPulbicAdded();
}
