package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.ActionLog;
import com.celloud.page.Page;

public interface ActionLogMapper {
    public int insert(ActionLog record);

    public int insertSelective(ActionLog record);

    public List<ActionLog> findLogs(@Param("userId") Integer userId, Page page);
}