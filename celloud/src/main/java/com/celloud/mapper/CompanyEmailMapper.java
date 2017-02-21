package com.celloud.mapper;

import java.util.List;

import com.celloud.model.mysql.CompanyEmail;

public interface CompanyEmailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CompanyEmail record);

    int insertSelective(CompanyEmail record);

    CompanyEmail selectByPrimaryKey(Integer id);

    List<CompanyEmail> selectBySelective(CompanyEmail queryCompanyEmail);

    int updateByPrimaryKeySelective(CompanyEmail record);

    int updateByPrimaryKey(CompanyEmail record);
}