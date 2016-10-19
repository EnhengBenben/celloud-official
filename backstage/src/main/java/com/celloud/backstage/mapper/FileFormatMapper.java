package com.celloud.backstage.mapper;

import java.util.List;

import com.celloud.backstage.model.FileFormat;

public interface FileFormatMapper {
    int deleteByPrimaryKey(Integer formatId);

    int insert(FileFormat record);

    int insertSelective(FileFormat record);

    FileFormat selectByPrimaryKey(Integer formatId);

    int updateByPrimaryKeySelective(FileFormat record);

    int updateByPrimaryKey(FileFormat record);
    
    public List<FileFormat> getFileFormatList();
}