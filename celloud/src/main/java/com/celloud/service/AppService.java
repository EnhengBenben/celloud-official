package com.celloud.service;

import java.util.List;
import java.util.Map;

import com.celloud.model.mysql.App;
import com.celloud.model.mysql.AppVO;
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
     * 
     * @description 根据主键查询app
     * @author miaoqi
     * @date 2017年3月24日 下午3:54:53
     * @param id
     * @return
     */
    public App get(Integer id);

    /**
     * 
     * @description 根据tagId获取appId
     * @author miaoqi
     * @date 2017年2月13日 下午4:02:04
     * @param tagId
     * @return
     */
    public Integer getAppIdByTagId(Integer tagId);

    /**
     * 获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    public Integer countMyApp(Integer userId);

    /**
     * 按时间段获取已添加的APP数量
     * 
     * @param userId
     * @return
     * @date 2015年12月25日 下午5:44:58
     */
    public List<Map<String, String>> countMyApp(Integer userId, String time);

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
	 * 获取用户被授权的所有APP
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年12月20日下午4:03:41
	 */
	public List<App> getRightAppList(Integer authFrom, Integer userId);

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

    /**
     * 根据数据类型索取用户可运行APP
     * 
     * @param formatId
     * @param userId
     * @return
     */
    public List<App> findAppsByFormat(Integer userId, Integer formatId);

    /**
     * 根据ID获取详细信息
     * 
     * @param appId
     * @return
     * @author leamo
     * @date 2016-1-10 下午8:03:53
     */
    public App findAppById(Integer appId);

    /**
     * 批量获取APP列表
     * 
     * @param appIds
     * @return
     * @author leamo
     * @date 2016年1月14日 下午1:49:46
     */
    public List<App> findAppsByIds(String appIds);

    /**
     * 批量获取APP名称
     * 
     * @param appIds
     * @return
     * @author leamo
     * @date 2016年1月14日 下午2:29:23
     */
    public String findAppNamesByIds(String appIds);
    
    /**
     * 单查
     * 
     * @param appId
     * @return
     * @author lin
     * @date 2016年1月19日下午7:03:41
     */
    public App selectByPrimaryKey(Integer appId);

    /**
     * 根据标签获取APP列表
     * 
     * @param tagId
     * @return
     * @author leamo
     * @date 2016年4月22日 上午10:32:45
     */
    public App findAppsByTag(Integer tagId);

    /**
     * 判断用户是否可运行此APP
     * 
     * @param appId
     * @param userId
     * @return
     * @author leamo
     * @date 2016年7月28日 下午4:27:24
     */
    public Boolean checkPriceToRun(List<Integer> appIds, Integer userId);

	public int addUserAppRight(Integer userId, Integer[] appIds, Integer authFrom);

	public boolean appDeleteByAuthFrom(Integer userId, Integer[] appIds);

    /**
     * 
     * @description 根据classifyId查看app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:34:19
     * @param classifyId
     * @param userId
     * @return
     */
    public PageList<AppVO> listByClassifyPId(Page page, Integer classifyId, Integer userId);

    /**
     * 
     * @description 根据classifyId查看app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:34:19
     * @param classifyId
     * @param userId
     * @return
     */
    public PageList<AppVO> listByClassifyId(Page page, Integer classifyId, Integer userId);

    /**
     * 
     * @description
     * @author miaoqi
     * @date 2017年3月24日 下午1:48:25
     * @param userId
     * @param appId
     * @return
     */
    public Boolean updateUserAppRight(Integer userId, Integer appId, Integer isAdd);

    /**
     * 
     * @description 根据userId, appId获取用户app授权信息
     * @author miaoqi
     * @date 2017年3月25日 下午11:11:20
     * @param userId
     * @param appId
     * @return
     */
    public Map<String, Object> getUserAppRight(Integer userId, Integer appId);

    /**
     * 
     * @description 根据非空条件查看app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:34:19
     * @param classifyId
     * @param userId
     * @return
     */
    public PageList<AppVO> selectBySelective(Page page, App app, Integer userId);

}
