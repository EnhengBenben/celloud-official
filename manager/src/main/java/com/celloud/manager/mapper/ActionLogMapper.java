package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.ActionLog;
import com.celloud.manager.page.Page;


public interface ActionLogMapper {
    public int insert(ActionLog record);

    public int insertSelective(ActionLog record);

    public List<ActionLog> findLogs(@Param("userId") Integer userId, Page page);
    
    public List<Map<String,Integer>> countBrowser(@Param("testAccountIds") String testAccountIds);
}