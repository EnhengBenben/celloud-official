package com.celloud.service;

import java.util.List;

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
	 * 分页检索done状态的实验流程
	 * 
	 * @param userId
	 * @param page
	 * @param sampleId
	 * @param methodId
	 * @param stepId
	 * @param start
	 * @param end
	 * @return
	 * @author lin
	 * @date 2016年3月31日下午3:33:53
	 */
	public PageList<Experiment> getExpDonePageList(Integer userId, Page page, Integer sampleId, Integer methodId,
			Integer stepId, String start, String end);

	/**
	 * 修改非空字段
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年3月28日下午4:09:10
	 */
	int updateByPrimaryKeySelective(Experiment record);

	/**
	 * 新增
	 * 
	 * @param exp
	 * @return
	 * @author lin
	 * @date 2016年3月30日下午1:29:10
	 */
	int insertSelective(Experiment exp);

	/**
	 * 校验number是否重复
	 * 
	 * @param state
	 * @param num
	 * @return
	 * @author lin
	 * @date 2016年4月1日上午11:26:08
	 */
	Integer checkoutNumber(Integer userId, String num);

	/**
	 * 根据主键单查
	 * 
	 * @param id
	 * @return
	 * @author lin
	 * @date 2016年3月30日下午1:29:26
	 */
	Experiment selectByPrimaryKey(Integer id);

	/**
	 * 获取未绑定数据的实验流程列表
	 * 
	 * @param userId
	 * @param number
	 * @return
	 * @author lin
	 * @date 2016年4月5日上午5:05:43
	 */
	List<Experiment> getUnRelatList(Integer userId, String number);
	
	/**
	 * 获取已绑定数据的实验流程列表
	 * 
	 * @param userId
	 * @param number
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年4月5日上午5:10:32
	 */
	List<Experiment> getRelatList(Integer userId, String number, String dataKey);

	/**
	 * 获取已有报告的实验流程列表
	 * 
	 * @param userId
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年4月7日下午1:37:43
	 */
	List<Experiment> getReportList(Integer userId, String dataKey);
}
