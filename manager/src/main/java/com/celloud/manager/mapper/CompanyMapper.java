package com.celloud.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Company;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    
    public List<Company> getBigCustomerCompany(@Param("role")Integer role,@Param("state")Integer state);
}