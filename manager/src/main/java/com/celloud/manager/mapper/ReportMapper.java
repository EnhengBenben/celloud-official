package com.celloud.manager.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Report;
import com.celloud.manager.model.ReportWithBLOBs;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(ReportWithBLOBs record);

    int insertSelective(ReportWithBLOBs record);

    ReportWithBLOBs selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ReportWithBLOBs record);

    int updateByPrimaryKey(Report record);
    
    public int countReport(@Param("companyId") Integer companyId,@Param("state")int state,@Param("period")int period);
}