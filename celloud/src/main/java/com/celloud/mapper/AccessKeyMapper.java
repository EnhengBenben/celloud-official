package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.AccessKey;
import com.celloud.page.Page;

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

    /**
     * 
     * @description 根据用户id分页查询key
     * @author miaoqi
     * @date 2017年3月8日 下午5:11:45
     * @param userId
     * @param page
     * @return
     */
    List<AccessKey> selectKeysByPage(@Param("userId") Integer userId, @Param("page") Page page);
}