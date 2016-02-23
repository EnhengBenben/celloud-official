package com.celloud.mapper;

import com.celloud.model.mysql.Discount;

public interface DiscountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Discount record);

    int insertSelective(Discount record);

    Discount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Discount record);

    int updateByPrimaryKey(Discount record);
}