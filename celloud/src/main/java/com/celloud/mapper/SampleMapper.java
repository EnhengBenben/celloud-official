package com.celloud.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Sample;

public interface SampleMapper {
    int deleteByPrimaryKey(Integer sampleId);

    int insertSelective(Sample record);

    Sample selectByPrimaryKey(Integer sampleId);

    int updateByPrimaryKeySelective(Sample record);

    int updateByPrimaryKey(Sample record);

    int updateAddTypeById(@Param("sampleIds") List<Integer> sampleIds,
            @Param("isAdd") Integer isAdd,
            @Param("updateDate") Date updateDate);

    List<Sample> selectAllByUser(@Param("userId") Integer userId,
            @Param("isAdd") Integer isAdd, @Param("state") Integer state,
            @Param("experState") Integer experState);

    Sample selectByName(@Param("userId") Integer userId,
            @Param("sampleName") String sampleName,
            @Param("state") Integer state);

    int deleteList(@Param("sampleIds") List<Integer> sampleIds);

    List<Sample> getSamples(@Param("userId") Integer userId,
            @Param("experState") Integer experState,
            @Param("state") Integer state);

    Sample getByNameExperState(@Param("userId") Integer userId,
            @Param("sampleName") String sampleName,
            @Param("experState") Integer experState,
            @Param("state") Integer state, @Param("isAdd") Integer isAdd);

    int addSampleTagRelat(@Param("sampleId") Integer sampleId,
            @Param("tagId") Integer tagId);
}