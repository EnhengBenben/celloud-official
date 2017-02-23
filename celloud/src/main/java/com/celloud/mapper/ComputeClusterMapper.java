package com.celloud.mapper;

import com.celloud.model.mysql.ComputeCluster;

public interface ComputeClusterMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ComputeCluster record);

	int insertSelective(ComputeCluster record);

	ComputeCluster selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ComputeCluster record);

	int updateByPrimaryKey(ComputeCluster record);

	ComputeCluster selectByAppId(Integer id);
}