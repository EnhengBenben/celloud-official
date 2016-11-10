package com.celloud.service;

import com.celloud.model.mysql.AccessKey;

/**
 * 用户和id、secret绑定关系
 * 
 * @author lin
 * @date 2016年11月7日 下午2:01:38
 */
public interface AccessKeyService {

	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:03:14
	 */
    int insertSelective(AccessKey record);

	/**
	 * 单查
	 * 
	 * @param userId
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:04:41
	 */
	AccessKey selectByUserId(Integer userId);

	/**
	 * 单查
	 * 
	 * @param keyId
	 * @param keySecret
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:06:55
	 */
	AccessKey selectByIdAndSecret(String keyId, String keySecret);

	/**
	 * 修改
	 * 
	 * @param record
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:05:08
	 */
    int updateByPrimaryKeySelective(AccessKey record);

}