package com.celloud.mapper;

import com.celloud.model.FileFormat;

public interface FileFormatMapper {
    int deleteByPrimaryKey(Integer formatId);

    int insert(FileFormat record);

    int insertSelective(FileFormat record);

    FileFormat selectByPrimaryKey(Integer formatId);

    int updateByPrimaryKeySelective(FileFormat record);

    int updateByPrimaryKey(FileFormat record);
}