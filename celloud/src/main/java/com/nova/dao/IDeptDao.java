package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DeptDaoImpl;
import com.nova.sdo.Dept;

@ImplementedBy(DeptDaoImpl.class)
public interface IDeptDao {
	/**
	 * 根据公司id获取其下的所有部门
	 * 
	 * @param companyId
	 * @return
	 */
	List<Dept> getDeptAll(Integer companyId);

	/**
	 * 根据Id单查
	 * 
	 * @param deptId
	 * @return
	 */
	Dept getDept(Integer deptId);

	/**
	 * 新增
	 * 
	 * @param dept
	 * @return
	 */
	Integer add(Dept dept);

	/**
	 * 修改状态
	 * 
	 * @param dept
	 * @return
	 */
	Integer updateState(Dept dept);

	/**
	 * 修改部门
	 * 
	 * @param dept
	 * @return
	 */
	Integer updateDept(Dept dept);
}
