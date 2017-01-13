package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.SampleStorage;
import com.celloud.page.Page;

public interface SampleStorageMapper {
    int insert(SampleStorage record);

    int insertSelective(SampleStorage record);

    int addSampleStorageRelat(@Param("ssid") Integer ssid,
            @Param("sampleIds") List<Integer> sampleIds);

    int updateByPrimaryKeySelective(SampleStorage record);

    List<SampleStorage> findAll(Page page, @Param("userId") Integer userId,
            @Param("state") Integer state);

    List<Map<String, Object>> sampleListInStorage(@Param("userId") Integer userId, @Param("state") Integer state,
            @Param("ssid") Integer ssid);
}