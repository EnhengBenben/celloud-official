package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.ExperimentDict;

public interface ExperimentDictService {
	
	/**
	 * 获取实验流程的动态菜单列表
	 * 
	 * @return
	 * @author lin
	 * @date 2016年3月30日上午10:12:08
	 */
	List<ExperimentDict> getExperimentDictList();

}
