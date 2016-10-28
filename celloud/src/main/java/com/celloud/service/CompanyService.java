package com.celloud.service;

import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 公司服务接口
 * 
 * @author lin
 * @date 2016年1月17日 上午2:00:46
 */
public interface CompanyService {

	/**
	 * 根据公司ID单查
	 * 
	 * @param companyId
	 * @return
	 * @author lin
	 * @date 2016年1月17日上午2:01:09
	 */
	Company selectByPrimaryKey(Integer companyId);

    /**
     * 
     * @author MQ
     * @date 2016年8月30日下午3:30:53
     * @description
     * @param company
     *
     */
    int updateCompanyIcon(Company company);

    /**
     * 
     * @description 根据companyId分页查询用户
     * @author miaoqi
     * @date 2016年10月27日上午11:05:54
     *
     * @param companyId
     * @param page
     * @return
     */
    PageList<User> pageQueryUser(Integer loginUserId, Integer companyId, Page page);

    /**
     * 
     * @description 根据主键修改不为空的字段
     * @author miaoqi
     * @date 2016年10月27日下午4:06:25
     *
     * @param updateUser
     * @return
     */
    Boolean updateBySelective(User updateUser);

}
