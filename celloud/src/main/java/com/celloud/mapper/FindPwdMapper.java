package com.celloud.mapper;

import com.celloud.model.mysql.FindPwd;

public interface FindPwdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FindPwd record);

    int insertSelective(FindPwd record);

    FindPwd selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FindPwd record);

    int updateByPrimaryKey(FindPwd record);
}