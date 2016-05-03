package com.celloud.backstage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Task;
import com.celloud.backstage.page.Page;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);
    
    
    List<Map<String,String>> getRunningTimeByPage(Page page,@Param("keyword") String keyword);

    Map<String, String> getQueuingTime();
    
    int getTotalRecordCount();
    
    int getCountByState(int state);
    
    int getFailCount();
}