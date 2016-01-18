package com.celloud.backstage.mapper;

import com.celloud.backstage.model.Report;
import com.celloud.backstage.model.ReportWithBLOBs;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(ReportWithBLOBs record);

    int insertSelective(ReportWithBLOBs record);

    ReportWithBLOBs selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ReportWithBLOBs record);

    int updateByPrimaryKey(Report record);
}