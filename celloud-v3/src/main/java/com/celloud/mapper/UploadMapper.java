package com.celloud.mapper;

import com.celloud.model.Upload;

public interface UploadMapper {
    int deleteByPrimaryKey(Integer uploadId);

    int insert(Upload record);

    int insertSelective(Upload record);

    Upload selectByPrimaryKey(Integer uploadId);

    int updateByPrimaryKeySelective(Upload record);

    int updateByPrimaryKey(Upload record);
}