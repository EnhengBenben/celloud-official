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
	 * 校验编号是否重复
	 * 
	 * @return
	 * @author lin
	 * @date 2016年4月1日上午11:20:58
	 */
	Integer checkoutNumber(@Param("userId") Integer userId, @Param("state") Integer state, @Param("num") String num);

}