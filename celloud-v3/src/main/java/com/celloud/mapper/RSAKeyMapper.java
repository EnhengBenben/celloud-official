package com.celloud.mapper;

import com.celloud.model.RSAKey;

public interface RSAKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RSAKey record);

    int insertSelective(RSAKey record);

    RSAKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RSAKey record);

    int updateByPrimaryKey(RSAKey record);
}