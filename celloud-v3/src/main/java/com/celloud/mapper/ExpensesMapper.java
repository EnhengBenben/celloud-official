package com.celloud.mapper;

import com.celloud.model.mysql.Expenses;

public interface ExpensesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Expenses record);

    int insertSelective(Expenses record);

    Expenses selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Expenses record);

    int updateByPrimaryKey(Expenses record);
}