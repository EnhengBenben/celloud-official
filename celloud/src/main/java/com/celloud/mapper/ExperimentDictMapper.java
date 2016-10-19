package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.ExperimentDict;

public interface ExperimentDictMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ExperimentDict record);

	int insertSelective(ExperimentDict record);

	ExperimentDict selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ExperimentDict record);

	int updateByPrimaryKey(ExperimentDict record);

	List<ExperimentDict> getExperimentDictList(@Param("state") Integer state);
}