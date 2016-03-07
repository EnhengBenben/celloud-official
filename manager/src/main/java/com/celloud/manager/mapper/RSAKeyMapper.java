package com.celloud.manager.mapper;

import com.celloud.manager.model.RSAKey;

public interface RSAKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RSAKey record);

    int insertSelective(RSAKey record);

    RSAKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RSAKey record);

    int updateByPrimaryKey(RSAKey record);
}