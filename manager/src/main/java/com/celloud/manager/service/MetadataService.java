package com.celloud.manager.service;

import java.util.List;

import com.celloud.manager.model.Metadata;

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
	 * 寻找上一个
	 * 
	 * @param meta
	 * @return
	 * @author lin
	 * @date 2016年10月13日下午2:28:53
	 */
	Metadata selectUp(Metadata meta);

	/**
	 * 寻找下一个
	 * 
	 * @param meta
	 * @return
	 * @author lin
	 * @date 2016年10月13日下午2:29:02
	 */
	Metadata selectDown(Metadata meta);

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

	/**
	 * 获取最大的序号
	 * 
	 * @param appId
	 * @param flag
	 * @return
	 * @author lin
	 * @date 2016年10月12日下午4:55:10
	 */
	Integer getMaxPriority(Integer appId, Integer flag);

	/**
	 * 移动
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @author lin
	 * @date 2016年10月13日上午10:50:04
	 */
	Integer updateMove(Metadata d1, Metadata d2);

}