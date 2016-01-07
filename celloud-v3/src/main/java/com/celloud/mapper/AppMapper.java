package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.App;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);

    /**
     * (重构)获取已添加的APP数量
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
     * (重构)按时间段获取已添加的APP数量
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
}
