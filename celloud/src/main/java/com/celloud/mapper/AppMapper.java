package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.App;
import com.celloud.model.mysql.AppVO;
import com.celloud.page.Page;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

	int insertUserAppRight(@Param("userId") Integer userId, @Param("appId") Integer appId,
			@Param("authFrom") Integer authFrom, @Param("isAdd") Integer isAdd);

    /**
     * 获取已添加的APP数量
     * 
     * @param userId
     * @param offLine
     * @param isAdd
     * @return
     * @author han
     * @date 2015年12月31日 上午10:26:42
     */
    public Integer countMyApp(@Param("userId") Integer userId,
            @Param("offLine") Integer offLine, @Param("isAdd") Integer isAdd);

    /**
     * 按时间段获取已添加的APP运行次数
     * 
     * @param userId
     * @param time
     * @param offLine
     * @param isAdd
     * @return
     * @author han
     * @date 2015年12月31日 上午10:26:20
     */
    public List<Map<String, String>> countMyAppRanNumByTime(
            @Param("userId") Integer userId, @Param("time") String time,
            @Param("offLine") Integer offLine, @Param("isAdd") Integer isAdd,
            @Param("flag") Integer flag, @Param("period") Integer period);

    public List<Map<String, String>> countMyAppByTime(
            @Param("userId") Integer userId, @Param("time") String time,
            @Param("offLine") Integer offLine, @Param("isAdd") Integer isAdd);

    /**
     * 获取用户已运行的APP
     * 
     * @param userId
     * @return
     * @date 2016-1-7 下午1:55:17
     */
    public List<Map<String, String>> getRanAPP(@Param("userId") Integer userId);

    /**
     * 根据app分类查询app列表
     * 
     * @param classifyId
     * @param companyId
     * @return
     * @author han
     * @date 2016年1月6日 上午10:33:01
     */
    public List<App> getAppByClassify(@Param("classifyId") Integer classifyId,
            @Param("userId") Integer userId, @Param("offLine") Integer offLine,
            @Param("attribute_private") Integer attribute_private,
            @Param("attribute_public") Integer attribute_public);

    /**
     * 获取APP分页列表
     * 
     * @param classifyId
     * @param classifyPId
     * @param userId
     * @param sortField
     * @param sortType
     * @param offLine
     * @param priceType
     * @param page
     * @return
     * @author han
     * @date 2016年1月6日 上午10:45:21
     */
    public List<App> getAppPageListByClassify(
            @Param("classifyId") Integer classifyId,
            @Param("classifyPId") Integer classifyPId,
            @Param("userId") Integer userId,
            @Param("sortField") String sortField,
            @Param("sortType") String sortType,
            @Param("offLine") Integer offLine,
            @Param("priceType") Byte priceType,
            @Param("attribute_private") Integer attribute_private,
            @Param("attribute_public") Integer attribute_public, Page page);

    /**
     * 根据APPid查询APP
     * 
     * @param id
     * @param userId
     * @param priceType
     * @return
     * @author han
     * @date 2016年1月6日 下午1:16:22
     */
    public App getAppById(@Param("appId") Integer appId,
            @Param("userId") Integer userId,
			@Param("priceType") Byte priceType, @Param("attribute") Integer attribute);

    /**
     * 获取已添加的APP
     * 
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 下午1:38:17
     */
    public List<App> getMyAppList(@Param("userId") Integer userId,
            @Param("offLine") Integer offLine, @Param("isAdd") Integer isAdd);

	public List<App> getRightAppList(@Param("authFrom") Integer authFrom, @Param("userId") Integer userId,
			@Param("offLine") Integer offLine);

    /**
     * 用户添加或取消APP到可运行列表
     * 
     * @param userId
     * @param appId
     * @return
     * @author han
     * @date 2016年1月7日 下午1:44:17
     */
    public Integer userUpdateApp(@Param("userId") Integer userId,
            @Param("appId") Integer appId, @Param("isAdd") Integer isAdd);

    /**
     * 按周统计该用的报告
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> countWeekByUserId(
            @Param("userId") Integer userId);

    /**
     * 根据数据类型索取用户可运行APP
     * 
     * @param formatId
     * @param userId
     * @return
     */
    public List<App> findAppsByFormat(@Param("userId") Integer userId,
            @Param("formatId") Integer formatId,
            @Param("offLine") Integer offLine, @Param("isAdd") Integer isAdd);

    /**
     * 批量获取APP列表
     * 
     * @param appIds
     * @return
     * @author leamo
     * @date 2016年1月14日 下午1:49:46
     */
    public List<App> findAppsByIds(@Param("appIds") String appIds);

    /**
     * 根据标签获取APP列表
     * 
     * @param tagId
     * @return
     * @author leamo
     * @date 2016年4月22日 上午10:32:45
     */
    public App findAppsByTag(@Param("tagId") Integer tagId);

    /**
     * 
     * @description 根据用户id查找appIds
     * @author miaoqi
     * @date 2016年10月28日下午2:40:17
     *
     * @param loginUserId
     * @return
     */
    public List<Integer> findAppIdsByUserId(@Param("userId") Integer loginUserId);

	public int addUserAppRight(@Param("userId") Integer userId, @Param("appIds") Integer[] appIds,
			@Param("isAdded") int isAdded, @Param("authFrom") Integer authFrom);

	public Integer deleteByAuthFrom(@Param("userId") Integer userId, @Param("authFrom") Integer authFrom,
			@Param("appIds") Integer[] appIds);
	
    /**
     * 
     * @description 根据tagId获取appId
     * @author miaoqi
     * @date 2017年2月13日 下午4:03:08
     * @param tagId
     * @return
     */
    Integer getAppIdByTagId(@Param("tagId") Integer tagId);

    /**
     * 
     * @description 根据classifyId获取app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:17:23
     * @param classifyId
     * @return
     */
    List<AppVO> selectByClassifyId(Page page, @Param("classifyId") Integer classifyId,
            @Param("userId") Integer userId, @Param("offLine") Integer offLine);

    /**
     * 
     * @description 根据classifyId获取app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:17:23
     * @param classifyId
     * @return
     */
    List<AppVO> selectByClassifyPId(Page page, @Param("classifyId") Integer classifyId, @Param("userId") Integer userId,
            @Param("offLine") Integer offLine);

    /**
     * 
     * @description 根据userId和appId更新是否添加状态
     * @author miaoqi
     * @date 2017年3月24日 下午1:56:27
     * @param userId
     * @param appId
     * @param isAdd
     * @return
     */
    int updateUserAppRight(@Param("userId") Integer userId, @Param("appId") Integer appId,
            @Param("isAdd") Integer isAdd);

    /**
     * 
     * @description 根据userId, appId获取用户app授权信息
     * @author miaoqi
     * @date 2017年3月25日 下午11:12:55
     * @param userId
     * @param appId
     * @return
     */
    Map<String, Object> selectUserAppRight(@Param("userId") Integer userId, @Param("appId") Integer appId);
    
    /**
     * 
     * @description 根据classifyId获取app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:17:23
     * @param classifyId
     * @return
     */
    List<AppVO> selectBySelective(Page page, @Param("app") App app, @Param("userId") Integer userId,
            @Param("offLine") Integer offLine);

}
