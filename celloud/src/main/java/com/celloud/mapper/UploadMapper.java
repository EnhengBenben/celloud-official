package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Upload;

public interface UploadMapper {
	int deleteByPrimaryKey(Integer uploadId);

	int insert(Upload record);

	int insertSelective(Upload record);

	Upload selectByPrimaryKey(Integer uploadId);

	int updateByPrimaryKeySelective(Upload record);

	int updateByPrimaryKey(Upload record);

	/**
	 * 线程信息修改
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:33:34
	 */
	int uploadUpdate(Upload record);

	/**
	 * 获取某个数据的所有线程信息
	 * 
	 * @param userId
	 * @param dataKey
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:58:24
	 */
	List<Long> getUploadList(@Param("userId") Integer userId, @Param("dataKey") String dataKey);
}