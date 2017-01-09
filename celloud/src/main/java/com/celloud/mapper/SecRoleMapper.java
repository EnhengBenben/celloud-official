package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.SecRole;

public interface SecRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecRole record);

    int insertSelective(SecRole record);

    SecRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecRole record);

    int updateByPrimaryKey(SecRole record);

    List<SecRole> findRolesByUserId(Integer userId);

	List<SecRole> findRoles(@Param("userId") Integer userId, @Param("authFrom") Integer authFrom);

	int insertUserRoles(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds,
			@Param("authFrom") Integer authFrom);

	int deleteByAuthFrom(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds,
			@Param("authFrom") Integer authFrom);
}