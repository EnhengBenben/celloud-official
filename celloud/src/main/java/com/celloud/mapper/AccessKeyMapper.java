package com.celloud.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.AccessKey;

public interface AccessKeyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessKey record);

    int insertSelective(AccessKey record);

    AccessKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessKey record);

    int updateByPrimaryKey(AccessKey record);

	/**
	 * 单查
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:04:41
	 */
	AccessKey selectByUserId(@Param("userId") Integer userId);

	/**
	 * 单查
	 * 
	 * @param keyId
	 * @param keySecret
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:06:55
	 */
	AccessKey selectByIdAndSecret(@Param("keyId") String keyId, @Param("keySecret") String keySecret);
}