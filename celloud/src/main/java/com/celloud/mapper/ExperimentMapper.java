package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Experiment;
import com.celloud.page.Page;

public interface ExperimentMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Experiment record);

	int insertSelective(Experiment record);

	Experiment selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Experiment record);

	int updateByPrimaryKey(Experiment record);

	List<Experiment> getExperimentPageList(@Param("userId") Integer userId, @Param("state") Integer state, Page page);

	List<Experiment> getExpDonePageList(@Param("userId") Integer userId, @Param("state") Integer state, Page page,
			@Param("sampleId") Integer sampleId, @Param("methodId") Integer methodId, @Param("stepId") Integer stepId,
			@Param("start") String start, @Param("end") String end);

	/**
	 * 获取未绑定数据的实验流程列表
	 * 
	 * @param userId
	 * @param state
	 * @param number
	 * @param stepId
	 * @param fileId
	 * @return
	 * @author lin
	 * @date 2016年4月5日上午4:14:33
	 */
	List<Experiment> getUnRelatList(@Param("userId") Integer userId, @Param("state") Integer state,
			@Param("number") String number, @Param("stepId") Integer stepId, @Param("fileId") Integer fileId);
	
	/**
	 * 获取已绑定数据的实验流程列表
	 * 
	 * @param userId
	 * @param state
	 * @param number
	 * @param stepId
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年4月5日上午5:07:03
	 */
	List<Experiment> getRelatList(@Param("userId") Integer userId, @Param("state") Integer state,
			@Param("number") String number, @Param("stepId") Integer stepId, @Param("dataKey") String dataKey);

	/**
	 * 校验编号是否重复
	 * 
	 * @return
	 * @author lin
	 * @date 2016年4月1日上午11:20:58
	 */
	Integer checkoutNumber(@Param("userId") Integer userId, @Param("state") Integer state, @Param("num") String num);

}