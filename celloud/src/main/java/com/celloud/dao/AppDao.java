package com.celloud.dao;

import java.util.List;
import java.util.Map;

import com.celloud.dao.impl.AppDaoImpl;
import com.celloud.sdo.App;
import com.celloud.sdo.Classify;
import com.celloud.sdo.Screen;
import com.google.inject.ImplementedBy;

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
	public List<App> getAppsByFormat(Integer formatId);

	/**
	 * 根据编号获取软件信息
	 * 
	 * @param softwareId
	 * @return
	 */
	public String getAppNameById(Long softwareId);

	/**
	 * 查询所有的APP信息
	 * 
	 * @return
	 */
	public List<App> getAllSoftware();

    /**
     * 获取APP所有父分类及某父分类下的子分类
     * 
     * @param classifyId
     *            父分类
     * @return
     */
    public Map<String, List<Classify>> getDoubleClassify(Integer classifyId);

    /**
     * 分类查询APP
     * 
     * @param classifyId
     *            分类
     * @param pid
     *            0:-父分类 1：子分类
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
}