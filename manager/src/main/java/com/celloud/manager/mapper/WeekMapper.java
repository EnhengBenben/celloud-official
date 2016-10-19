package com.celloud.manager.mapper;

import com.celloud.manager.model.Week;

/**
 * 
 * @author MQ
 * @date 2016年8月8日下午4:53:12
 * @description 周统计接口
 *
 */
public interface WeekMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Week record);

    int insertSelective(Week record);

    Week selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Week record);

    int updateByPrimaryKey(Week record);

    void insertDataToWeek();
}