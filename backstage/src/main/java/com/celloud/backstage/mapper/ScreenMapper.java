package com.celloud.backstage.mapper;

import com.celloud.backstage.model.Screen;

public interface ScreenMapper {
    int deleteByPrimaryKey(Integer screenId);

    int insert(Screen record);

    int insertSelective(Screen record);

    Screen selectByPrimaryKey(Integer screenId);

    int updateByPrimaryKeySelective(Screen record);

    int updateByPrimaryKey(Screen record);
    
    /**
     * 批量插入
     *
     * @param list
     * @return
     * @author han
     * @date 2016年2月24日 下午3:50:39
     */
    public int insertScreen(Screen screen);
}