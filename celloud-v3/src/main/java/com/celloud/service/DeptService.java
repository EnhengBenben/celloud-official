package com.celloud.service;

import com.celloud.model.Dept;

/**
 * 部门service
 * 
 * @author lin
 * @date 2016年1月18日 上午10:15:23
 */
public interface DeptService {
	/**
	 * 根据主键单查
	 * 
	 * @param deptId
	 * @return
	 * @author lin
	 * @date 2016年1月18日上午10:15:49
	 */
	Dept selectByPrimaryKey(Integer deptId);
}
