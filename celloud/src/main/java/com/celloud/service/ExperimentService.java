package com.celloud.service;

import com.celloud.model.mysql.Experiment;
import com.celloud.page.Page;
import com.celloud.page.PageList;

public interface ExperimentService {
	/**
	 * 分页检索doing状态的实验流程
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * @author lin
	 * @date 2016年3月28日下午4:08:54
	 */
	public PageList<Experiment> getExperimentPageList(Integer userId, Page page);
	/**
	 * 修改
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年3月28日下午4:09:10
	 */
	int updateByPrimaryKeySelective(Experiment record);
	
	int insertSelective(Experiment exp);
}
