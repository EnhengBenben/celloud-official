package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.Metadata;

public interface MetadataService {
	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年10月12日下午2:06:45
	 */
	int insertSelective(Metadata record);

	/**
	 * 单查
	 * 
	 * @param id
	 * @return
	 * @author lin
	 * @date 2016年10月12日下午2:06:28
	 */
	Metadata selectByPrimaryKey(Integer id);

	/**
	 * 修改
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年10月12日下午2:06:20
	 */
	int updateByPrimaryKeySelective(Metadata record);

	/**
	 * 获取某个APP某个种类的元数据
	 * 
	 * @param appId
	 * @param flag
	 * @return
	 * @author lin
	 * @date 2016年10月12日下午2:06:07
	 */
	List<Metadata> getMetadata(Integer appId, Integer flag);

}