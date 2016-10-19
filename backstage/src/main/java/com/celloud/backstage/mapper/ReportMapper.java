package com.celloud.backstage.mapper;

import org.apache.ibatis.annotations.Param;

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
    /**
     * 硬删除 tb_report
     *
     * @param userId
     * @return
     * @author han
     * @date 2016年2月22日 下午5:14:27
     */
    public int deleteByUserId(@Param("userId") Integer userId);
}