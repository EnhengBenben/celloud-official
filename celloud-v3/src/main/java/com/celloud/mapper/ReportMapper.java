package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.Report;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    /**
     * (重构)统计个人报告数量
     *
     * @param userId
     * @param state
     * @param flag
     * @return
     * @author han
     * @date 2015年12月31日 上午10:29:12
     */
    public Integer countReport(@Param("userId") Integer userId, @Param("state") Integer state,
            @Param("flag") Integer flag);

    /**
     * (重构)按时间段统计个人报告数量
     *
     * @param userId
     * @param time
     * @param state
     * @param flag
     * @return
     * @author han
     * @date 2015年12月31日 上午10:29:19
     */
    public List<Map<String, String>> countReportByTime(@Param("userId") Integer userId, @Param("time") Integer time,
            @Param("state") Integer state, @Param("flag") Integer flag);
}