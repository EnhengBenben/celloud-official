package com.celloud.mapper;

import java.util.List;

import com.celloud.model.mysql.BoxConfig;

public interface BoxConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BoxConfig record);

    int insertSelective(BoxConfig record);

    BoxConfig selectByPrimaryKey(Integer id);
    
    public List<BoxConfig> selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(BoxConfig record);

    int updateByPrimaryKey(BoxConfig record);
}