package com.celloud.dao;

import java.util.List;

import com.celloud.dao.impl.AppDaoImpl;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.google.inject.ImplementedBy;
import com.nova.pager.Page;
import com.nova.pager.PageList;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-14下午5:33:54
 * @version Revision: 1.0
 */
@ImplementedBy(AppDaoImpl.class)
public interface AppDao {
    /**
     * 根据数据类型查询APP列表
     * 
     * @param formatId
     * @return
     */
    public List<App> getAppsByFormat(Integer formatId, Integer userId);

    /**
     * 根据编号获取软件信息
     * 
     * @param softwareId
     * @return
     */
    public String getAppNameById(Long softwareId);

    /**
     * 获取分类信息
     * 
     * @param id
     * @return
     */
    public Classify getClassifyById(Integer id);

    /**
     * 查询分类列表
     * 
     * @param pid
     *            父级分类id,0-查询父分类列表
     * @return
     */
    public List<Classify> getClassify(Integer pid);

    /**
     * 根据app分类查询app列表
     * 
     * @param classifyId
     * @param companyId
     * @return
     */
    public List<App> getAppByClassify(Integer classifyId, Integer companyId);

    /**
     * 根据APPid查询APP
     * 
     * @param id
     * @return
     */
    public App getAppById(Integer id, Integer userId);

    /**
     * 获取APP分页列表
     * 
     * @param classifyId
     * @param isPid
     * @param companyId
     * @param sortField
     * @param sortType
     * @param page
     * @return
     */
    public PageList<App> getAppPageListByClassify(Integer classifyId,
            Integer classifyPId, Integer companyId, String sortField,
            String sortType, Page page);

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