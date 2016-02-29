package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Report;
import com.celloud.page.Page;

public interface ReportMapper {

    int deleteByPrimaryKey(Integer reportId);

    /**
     * 软删除报告
     * 
     * @param projectId
     *            ：项目ID
     * @param state
     *            ：删除状态
     * @return
     * @date 2016-1-8 下午2:27:43
     */
    Integer deleteByState(@Param("projectId") Integer projectId,
            @Param("state") Integer state);

    int insert(Report record);

    int insertSelective(Report record);

    int insertSelectiveReturnKey(Report record);

    Report selectByPrimaryKey(Integer reportId);
    
	/**
	 * 检索数据报告
	 * 
	 * @param userId
	 * @param appId
	 * @param projectId
	 * @param fileId
	 * @param flag
	 * @return
	 * @author lin
	 * @date 2016年1月18日上午11:16:24
	 */
	Report getReport(@Param("userId") Integer userId, @Param("appId") Integer appId,@Param("projectId") Integer projectId, @Param("fileId") Integer fileId,
			@Param("flag") Integer flag);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
    
    /**
     * 根据外键组合修改非外键字段<br>
     * 
     * 外键组合：user_id（可选），app_id（可选），file_id（flag==0时必选），project_id（必选），flag（可选，0-数据报告，1-项目报告，Null－数据&项目）<br>
     * 修改（均可选）：period，readed，state，end_date，print_context，context
     * 
     * @param report
     * @return
     * @author lin
     * @date 2016年1月19日上午10:22:28
     */
	int updateReport(Report report);

    /**
     * 修改项目运行状态
     * 
     * @param projectId
     * @param state
     * @param flag
     * @param period
     * @return
     * @author leamo
     * @date 2016年1月14日 下午8:07:54
     */
    int updateReportPeriod(Report record);

    /**
     * 统计个人报告数量
     * 
     * @param userId
     * @param state
     * @param flag
     * @return
     * @author han
     * @date 2015年12月31日 上午10:29:12
     */
    public Integer countReport(@Param("userId") Integer userId,
            @Param("state") Integer state, @Param("flag") Integer flag,@Param("period") Integer period);

    /**
     * 按时间段统计个人报告数量
     * 
     * @param userId
     * @param time
     * @param state
     * @param flag
     * @return
     * @author han
     * @date 2015年12月31日 上午10:29:19
     */
    public List<Map<String, String>> countReportByTime(
            @Param("userId") Integer userId, @Param("time") String time,
            @Param("state") Integer state, @Param("flag") Integer flag,@Param("period") Integer period);

    /**
     * 报告检索
     * 
     * @param userId
     *            ：用户ID
     * @param pager
     *            ：分页类
     * @param condition
     *            ：检索条件
     * @param start
     *            ：开始时间
     * @param end
     *            ：结束时间
     * @param appId
     *            ：APPID
     * @return
     * @date 2016-1-5 下午3:36:01
     */
    public List<Map<String, Object>> getReportList(
            @Param("userId") Integer userId, Page pager,
            @Param("condition") String condition, @Param("start") String start,
            @Param("end") String end, @Param("appId") Integer appId,
            @Param("flag") Integer flag);

    /**
     * 纹计用户报告
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> countAppRunNumByUserId(
            @Param("userId") Integer userId);

    /**
     * 按周统计用户报告
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> countReportWeekByUserId(
            @Param("userId") Integer userId);

    public List<Map<String, String>> countReportMonthByUserId(
            @Param("userId") Integer userId);

    /**
     * 获取项目中正在运行的数据数量
     * 
     * @param projectId
     * @param state
     * @param flag
     * @return
     * @author leamo
     * @date 2016年1月15日 上午10:12:47
     */
    public Integer selectRunNumByPro(@Param("projectId") Integer projectId,
            @Param("state") Integer state, @Param("flag") Integer flag,
            @Param("period") Integer period);

    /**
     * 根据projectId获取project、app详细
     * 
     * @param projectId
     * @return
     * @author leamo
     * @date 2016年2月29日 下午7:31:00
     */
    public Map<String, Object> getAllReportInfoByProjectId(
            @Param("projectId") Integer projectId, @Param("flag") Integer flag);
}
