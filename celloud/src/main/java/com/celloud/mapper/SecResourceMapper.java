package com.celloud.mapper;

import java.util.List;

import com.celloud.model.mysql.SecResource;

public interface SecResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecResource record);

    int insertSelective(SecResource record);

    SecResource selectByPrimaryKey(Integer id);

    List<SecResource> findPermissionResourcesByUserId(Integer userId);

    int updateByPrimaryKeySelective(SecResource record);

    int updateByPrimaryKey(SecResource record);
}