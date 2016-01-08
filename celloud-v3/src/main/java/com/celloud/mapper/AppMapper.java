package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.App;
import com.celloud.page.Page;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

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
    public Integer countMyApp(@Param("userId") Integer userId, @Param("offLine") Integer offLine,
            @Param("isAdd") Integer isAdd);

    /**
     * 按时间段获取已添加的APP数量
     *
     * @param userId
     * @param time
     * @param offLine
     * @param isAdd
     * @return
     * @author han
     * @date 2015年12月31日 上午10:26:20
     */
    public List<Map<String, String>> countMyAppByTime(@Param("userId") Integer userId, @Param("time") Integer time,
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
     * 根据数据类型查询APP列表
     *
     * @param formatId
     * @param userId
     * @return
     * @author han
     * @date 2016年1月5日 下午4:54:33
     */
    public List<App> getAppsByFormat(@Param("formatId") Integer formatId, @Param("userId") Integer userId,
            @Param("offLine") Integer offLine, @Param("attribute_private") Integer attribute_private,
            @Param("attribute_public") Integer attribute_public);

    /**
     * 根据app分类查询app列表
     *
     * @param classifyId
     * @param companyId
     * @return
     * @author han
     * @date 2016年1月6日 上午10:33:01
     */
    public List<App> getAppByClassify(@Param("classifyId") Integer classifyId, @Param("userId") Integer userId,
            @Param("offLine") Integer offLine, @Param("attribute_private") Integer attribute_private,
            @Param("attribute_public") Integer attribute_public);

    /**
     * 获取APP分页列表
     *
     * @param classifyId
     * @param classifyPId
     * @param userId
     * @param sortField
     * @param sortType
     * @param page
     * @return
     * @author han
     * @date 2016年1月6日 上午10:45:21
     */
    public List<App> getAppPageListByClassify(@Param("classifyId") Integer classifyId,
            @Param("classifyPId") Integer classifyPId, @Param("userId") Integer userId,
            @Param("sortField") String sortField, @Param("sortType") String sortType, @Param("offLine") Integer offLine,
            @Param("attribute_private") Integer attribute_private, @Param("attribute_public") Integer attribute_public,
            Page page);

    /**
     * 根据APPid查询APP
     *
     * @param id
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 下午1:16:22
     */
    public App getAppById(@Param("appId") Integer appId, @Param("userId") Integer userId);

    /**
     * 获取已添加的APP
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年1月6日 下午1:38:17
     */
    public List<App> getMyAppList(@Param("userId") Integer userId, @Param("offLine") Integer offLine);

    /**
     * 用户添加或取消APP到可运行列表
     *
     * @param userId
     * @param appId
     * @return
     * @author han
     * @date 2016年1月7日 下午1:44:17
     */
    public Integer userUpdateApp(@Param("userId") Integer userId, @Param("appId") Integer appId,
            @Param("isAdd") Integer isAdd);
    /**
     * 按周统计该用的报告
     * @param userId
     * @return
     */
    public List<Map<String, String>>  countWeekByUserId(@Param("userId") Integer userId);
}
