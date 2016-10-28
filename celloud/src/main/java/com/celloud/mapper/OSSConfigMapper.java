package com.celloud.mapper;

import com.celloud.model.mysql.OSSConfig;

public interface OSSConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OSSConfig record);

    int insertSelective(OSSConfig record);

    OSSConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OSSConfig record);

    int updateByPrimaryKey(OSSConfig record);

	OSSConfig getLatest();
}