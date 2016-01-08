package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.Screen;

public interface ScreenMapper {
    int deleteByPrimaryKey(Integer screenId);

    int insert(Screen record);

    int insertSelective(Screen record);

    Screen selectByPrimaryKey(Integer screenId);

    int updateByPrimaryKeySelective(Screen record);

    int updateByPrimaryKey(Screen record);

    /**
     * 获取软件截图
     *
     * @param id
     * @return
     * @author han
     * @date 2016年1月7日 下午1:36:19
     */
    public List<Screen> getScreenByAppId(@Param("appId") Integer id);
}