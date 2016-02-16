package com.celloud.backstage.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.RSAKey;

public interface RSAKeyMapper {
    public int deleteByPrimaryKey(Integer id);

    public int deleteByModulus(String modulus);

    public int insert(RSAKey record);

    public int insertSelective(RSAKey record);

    public RSAKey selectByPrimaryKey(Integer id);
    
    public int deleteExpiresKeys(Date lastTime);

    public RSAKey getByModulus(@Param("modulus") String modulus);

    public int deleteAllKeys(@Param("userId") int userId);
}