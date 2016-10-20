package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.SampleOrder;

public interface SampleOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SampleOrder record);

    int insertSelective(SampleOrder record);

    SampleOrder selectByPrimaryKey(@Param("id") Integer id,
            @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(SampleOrder record);

    int updateByPrimaryKey(SampleOrder record);
}