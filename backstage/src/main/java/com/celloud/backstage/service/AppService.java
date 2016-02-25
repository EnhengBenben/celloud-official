package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.App;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

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
    
    /**
     * app分页列表
     *
     * @param page
     * @return
     * @author han
     * @date 2016年2月23日 下午1:08:22
     */
    PageList<App> getAppByPage(Page page);
    
    /**
     * app上线
     *
     * @param appId
     * @return
     * @author han
     * @date 2016年2月23日 下午2:07:15
     */
    public int updateAppOn(Integer appId);
    
    /**
     * app下线
     *
     * @param appId
     * @return
     * @author han
     * @date 2016年2月23日 下午2:08:19
     */
    public int updateAppOff(Integer appId);
    
    public int addApp(App app,String[]screenNames,Integer[] formatIds,Integer[] calssifyIds);
    
    public int updateApp(App app,String[]screenNames);
    public int appNameExist(String appName);
}
