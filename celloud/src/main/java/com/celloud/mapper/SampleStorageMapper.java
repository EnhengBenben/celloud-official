package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.SampleStorage;

public interface SampleStorageMapper {
    int insert(SampleStorage record);

    int insertSelective(SampleStorage record);

    int addSampleStorageRelat(@Param("ssid") Integer ssid,
            @Param("sampleIds") List<Integer> sampleIds);

    List<SampleStorage> findAll(@Param("userId") Integer userId,
            @Param("state") Integer state);

    List<SampleStorage> sampleListInStorage(@Param("userId") Integer userId,
            @Param("state") Integer state, @Param("ssid") Integer ssid);
}