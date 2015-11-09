package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.celloud.service.impl.AppServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-15下午4:39:27
 * @version Revision: 1.0
 */
@ImplementedBy(AppServiceImpl.class)
public interface AppService {
    /**
     * 根据数据类型查询APP列表
     * 
     * @param formatId
     * @return
     */
    public List<App> getAppsByFormat(Integer formatId);

    /**
     * 根据编号获取软件信息
     * 
     * @param softwareId
     * @return
     */
    // TODO 尽量不要根据id检索某个属性，直接单查就好了
    public String getAppNameById(Long softwareId);

    /**
     * 查询所有的APP信息
     * 
     * @return
     */
    public List<App> getAllSoftware();

    /**
     * 获取APP所有分类分类
     * 
     * @param classifyId
     *            分类
     * @return
     */
    public Map<String, List<Classify>> getDoubleClassify(Integer classifyId);

    /**
     * 大分类查询APP
     * 
     * @param classifyId
     *            分类
     * @param companyId
     *            软件提供方
     * @return
     */
    public List<App> getAppByClassify(Integer classifyId, Integer pid,
            Integer companyId);

    /**
     * 根据id查询APP
     * 
     * @param id
     * @return
     */
    public App getAppById(Integer id);

    /**
     * 获取已添加的APP
     * 
     * @param userId
     * @return
     */
    public List<App> getMyAppList(Integer userId);

    /**
     * 获取软件截图
     * 
     * @param id
     *            APP编号
     * @return
     */
    public List<Screen> getScreenByAppId(Integer id);

    /**
     * 用户添加APP到可运行列表
     * 
     * @param userId
     * @param appId
     * @return
     */
    public Integer userAddApp(Integer userId, Integer appId);

    /**
     * 用户取消APP在可运行列表
     * 
     * @param userId
     * @param appId
     * @return
     */
    public Integer userRemoveApp(Integer userId, Integer appId);
}
