package com.celloud.mapper;

import com.celloud.model.mysql.SampleOrder;

public interface SampleOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SampleOrder record);

    int insertSelective(SampleOrder record);

    SampleOrder selectByPrimaryKey(Integer id, Integer userId);

    int updateByPrimaryKeySelective(SampleOrder record);

    int updateByPrimaryKey(SampleOrder record);
}