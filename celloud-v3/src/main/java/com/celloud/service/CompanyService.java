package com.celloud.service;

import com.celloud.model.mysql.Company;

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

}
