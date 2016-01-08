package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.App;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * app接口
 *
 * @author han
 * @date 2015年12月25日 下午5:43:03
 */
public interface AppService {
    /**
     * (重构)获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    public Integer countMyApp(Integer userId);

    /**
     * (重构)按时间段获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    public List<Map<String, String>> countMyApp(Integer userId, Integer time);

    /**
     * 获取用户已运行的APP
     * 
     * @param userId
     * @return
     * @date 2016-1-7 下午2:07:45
     */
    public List<Map<String, String>> getRanAPP(Integer userId);


    /**
     * 根据app分类查询app列表
     *
     * @param classifyId
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 上午10:30:34
     */
    public List<App> getAppByClassify(Integer classifyId, Integer userId);

    /**
     * 获取APP分页列表
     *
     * @param classifyId
     * @param classifyPId
     * @param companyId
     * @param sortField
     * @param sortType
     * @param page
     * @return
     * @author han
     * @date 2016年1月6日 上午10:44:30
     */
    public PageList<App> getAppPageListByClassify(Integer classifyId, Integer classifyPId, Integer userId,
            String sortField, String sortType, Page page);

    /**
     * 根据id查询APP
     *
     * @param id
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 下午1:15:30
     */
    public App getAppById(Integer id, Integer userId);

    /**
     * 获取已添加的APP
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 下午1:36:58
     */
    public List<App> getMyAppList(Integer userId);

    /**
     * 用户添加APP到可运行列表
     *
     * @param userId
     * @param appId
     * @return
     * @author han
     * @date 2016年1月7日 下午1:41:28
     */
    public Integer userAddApp(Integer userId, Integer appId);

    /**
     * 用户取消APP在可运行列表
     *
     * @param userId
     * @param appId
     * @return
     * @author han
     * @date 2016年1月7日 下午1:41:35
     */
    public Integer userRemoveApp(Integer userId, Integer appId);

}
