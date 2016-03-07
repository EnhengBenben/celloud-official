package com.celloud.manager.mapper;

import com.celloud.manager.model.Expenses;

public interface ExpensesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Expenses record);

    int insertSelective(Expenses record);

    Expenses selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Expenses record);

    int updateByPrimaryKey(Expenses record);
}