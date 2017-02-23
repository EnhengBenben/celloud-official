package com.celloud.service;

import java.util.List;
import java.util.Map;

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

    /**
     * 
     * @description 根据tagId和flag查询元数据返回给Select2
     * @author miaoqi
     * @date 2017年2月13日 下午4:21:30
     * @param appId
     * @param flagId
     * @return
     */
    List<Map<String, String>> getMetadataToSelectByTagIdAndFlag(Integer tagId, Integer flag);

    /**
     * 
     * @description name为key,seq为value获取map
     * @author miaoqi
     * @date 2017年2月23日 下午4:18:09
     * @return
     */
    Map<String, Map<String, String>> getNameSeqMap(Integer flag);

}