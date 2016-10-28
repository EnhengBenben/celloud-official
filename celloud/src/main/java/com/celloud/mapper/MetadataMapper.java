package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Metadata;

public interface MetadataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Metadata record);

    int insertSelective(Metadata record);

    Metadata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Metadata record);

    int updateByPrimaryKey(Metadata record);

	public List<Metadata> getMetadata(@Param("appId") Integer appId, @Param("flag") Integer flag,
			@Param("state") Integer state);

}