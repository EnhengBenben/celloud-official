package com.celloud.mapper;

import com.celloud.model.mysql.SampleLog;

public interface SampleLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SampleLog record);

    int insertSelective(SampleLog record);

    SampleLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SampleLog record);

    int updateByPrimaryKey(SampleLog record);

    int deleteBySampleId(Integer sampleId, Integer state);
}