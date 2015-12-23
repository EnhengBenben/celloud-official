package com.celloud.mapper;

import com.celloud.model.Report;
import com.celloud.model.ReportWithBLOBs;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(ReportWithBLOBs record);

    int insertSelective(ReportWithBLOBs record);

    ReportWithBLOBs selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ReportWithBLOBs record);

    int updateByPrimaryKey(Report record);
}