package com.celloud.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.SecRole;

public interface SecRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecRole record);

    int insertSelective(SecRole record);

    SecRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecRole record);

    int updateByPrimaryKey(SecRole record);

    List<SecRole> findRolesByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    List<SecRole> findRoleByIds(@Param("roleIds") Integer[] roleIds);

}