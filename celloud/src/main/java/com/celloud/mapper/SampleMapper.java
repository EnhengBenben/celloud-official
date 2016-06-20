package com.celloud.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Sample;

public interface SampleMapper {
    int deleteByPrimaryKey(Integer sampleId);

    int insert(Sample record);

    int insertSelective(Sample record);

    Sample selectByPrimaryKey(Integer sampleId);

    int updateByPrimaryKeySelective(Sample record);

    int updateByPrimaryKey(Sample record);

    int updateAddTypeById(@Param("sampleIds") List<Integer> sampleIds,
            @Param("isadd") Integer isadd,
            @Param("updateDate") Date updateDate);

    List<Sample> selectAllByUser(@Param("userId") Integer userId,
            @Param("isAdd") Integer isAdd, @Param("state") Integer state);

    Sample selectByName(@Param("userId") Integer userId,
            @Param("sampleName") String sampleName,
            @Param("state") Integer state);
}