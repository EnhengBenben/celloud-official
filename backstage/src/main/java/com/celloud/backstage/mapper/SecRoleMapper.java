package com.celloud.backstage.mapper;

import java.util.List;

import com.celloud.backstage.model.SecRole;

public interface SecRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecRole record);

    int insertSelective(SecRole record);

    SecRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecRole record);

    int updateByPrimaryKey(SecRole record);

    List<SecRole> findRolesByUserId(Integer userId);
}