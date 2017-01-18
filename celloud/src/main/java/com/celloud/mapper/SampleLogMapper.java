package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.SampleLog;

public interface SampleLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SampleLog record);

    int insertSelective(SampleLog record);

    SampleLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SampleLog record);

    int updateByPrimaryKey(SampleLog record);

    int deleteBySampleId(@Param("sampleId") Integer sampleId,
            @Param("state") Integer state);

    int updateStateBySampleId(@Param("sampleId") Integer sampleId,
            @Param("state") Integer state,
            @Param("experState") Integer experState);

    int deleteBySampling(Integer sampleId);
}