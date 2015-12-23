package com.celloud.mapper;

import com.celloud.model.Screen;

public interface ScreenMapper {
    int deleteByPrimaryKey(Integer screenId);

    int insert(Screen record);

    int insertSelective(Screen record);

    Screen selectByPrimaryKey(Integer screenId);

    int updateByPrimaryKeySelective(Screen record);

    int updateByPrimaryKey(Screen record);
}