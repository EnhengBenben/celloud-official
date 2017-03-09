package com.celloud.service;

import com.celloud.model.mysql.AccessKey;
import com.celloud.page.Page;
import com.celloud.page.PageList;

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

    /**
     * 
     * @description 根据用户id分页查询key
     * @author miaoqi
     * @date 2017年3月8日 下午5:09:42
     * @param userId
     * @param page
     * @return
     */
    PageList<AccessKey> listKeys(Integer userId, Page page);

    /**
     * 
     * @description 为用户增加一对key, secret
     * @author miaoqi
     * @date 2017年3月9日 上午10:51:59
     * @param userId
     * @param username
     * @return
     */
    Boolean save(Integer userId, String username);

    /**
     * 
     * @description 根据主键删除一对key, secret
     * @author miaoqi
     * @date 2017年3月9日 上午11:21:05
     * @param id
     * @return
     */
    Boolean remove(Integer id);
}