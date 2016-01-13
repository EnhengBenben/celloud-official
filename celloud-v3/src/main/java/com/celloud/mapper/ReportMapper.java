package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.Report;
import com.celloud.page.Page;

public interface ReportMapper {

	int deleteByPrimaryKey(Integer reportId);

	int insert(Report record);

	int insertSelective(Report record);

    int insertSelectiveReturnKey(Report record);

	Report selectByPrimaryKey(Integer reportId);

	int updateByPrimaryKeySelective(Report record);

	int updateByPrimaryKey(Report record);

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
	public Integer countReport(@Param("userId") Integer userId, @Param("state") Integer state, @Param("flag") Integer flag);

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
	public List<Map<String, String>> countReportByTime(@Param("userId") Integer userId, @Param("time") Integer time, @Param("state") Integer state, @Param("flag") Integer flag);

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
	public List<Map<String, Object>> getReportList(@Param("userId") Integer userId, Page pager, @Param("condition") String condition, @Param("start") String start,
			@Param("end") String end, @Param("appId") Integer appId);

	/**
	 * 纹计用户报告
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> countAppRunNumByUserId(@Param("userId") Integer userId);

	/**
	 * 按周统计用户报告
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> countReportWeekByUserId(@Param("userId") Integer userId);
	
	public List<Map<String,String>> countReportMonthByUserId(@Param("userId") Integer userId);
}
