package com.celloud.mapper;

import com.celloud.model.mysql.SampleStorage;

public interface SampleStorageMapper {
    int insert(SampleStorage record);

    int insertSelective(SampleStorage record);
}