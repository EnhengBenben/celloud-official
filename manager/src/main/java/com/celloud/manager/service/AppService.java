package com.celloud.manager.service;

import java.math.BigDecimal;
import java.util.List;

import com.celloud.manager.model.App;
import com.celloud.manager.model.Price;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

/**
 * APP管理操作service
 * 
 * @author leamo
 * @date 2016年3月21日 上午11:12:55
 */
public interface AppService {
    /**
     * 大客户的APP价格列表
     * 
     * @param companyId
     * @return
     * @author leamo
     * @date 2016年3月18日 下午4:47:11
     */
    public List<App> appPriceList(Integer companyId);

    /**
     * APP价格历史
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016年3月18日 下午4:47:11
     */
    public List<Price> appPriceHistory(Integer appId);

    /**
     * 修改app价格
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016年3月21日 下午2:30:07
     */
    public Integer updatePrice(Integer appId, BigDecimal price);

    /**
     * 获取私有的app列表
     *
     * @param companyId
     *            大客户编号
     * @param attribute
     *            软件可添加权限 0-public（所有人可添加） 1-private（软件所有公司的用户可添加）
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

    public int addApp(App app, String[] screenNames, Integer[] formatIds, Integer[] calssifyIds);

    public int updateApp(App app, String[] screenNames, String[] delScreenNames, Integer[] formatIds,
            Integer[] calssifyIds);

    public int appNameExist(Integer appId, String appName);

    public App getAppById(Integer appId);
}
