package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Auth;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

	Auth selectByRefreshToken(@Param("refreshToken") String refreshToken);
}