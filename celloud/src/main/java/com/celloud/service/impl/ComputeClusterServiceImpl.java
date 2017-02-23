package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ComputeClusterMapper;
import com.celloud.model.mysql.ComputeCluster;
import com.celloud.service.ComputeClusterService;

/**
 * 计算集群数据字典的service接口
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年2月21日下午3:47:41
 * @version Revision: 1.0
 */
@Service
public class ComputeClusterServiceImpl implements ComputeClusterService {
	@Resource
	private ComputeClusterMapper mapper;

	@Override
	public ComputeCluster selectByAppId(Integer id) {
		return mapper.selectByAppId(id);
	}

}
