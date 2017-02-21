package com.celloud.service;

import com.celloud.model.mysql.ComputeCluster;

/**
 * 计算集群数据字典的service接口
 * 
 * @author <a href="mailto:sunwendong@celloud.cn">sun8wd</a>
 * @date 2017年2月21日下午3:47:17
 * @version Revision: 1.0
 */
public interface ComputeClusterService {
	/**
	 * 获取app对应的计算集群
	 * 
	 * @param id
	 * @return
	 */
	ComputeCluster selectByAppId(Integer id);
}
