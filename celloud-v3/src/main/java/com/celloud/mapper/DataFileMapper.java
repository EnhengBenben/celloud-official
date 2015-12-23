package com.celloud.mapper;

import com.celloud.model.DataFile;

public interface DataFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(DataFile record);

    int insertSelective(DataFile record);

    DataFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(DataFile record);

    int updateByPrimaryKeyWithBLOBs(DataFile record);

    int updateByPrimaryKey(DataFile record);
}