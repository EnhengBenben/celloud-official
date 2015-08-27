package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.Dept;
import com.nova.service.impl.DeptServiceImpl;

@ImplementedBy(DeptServiceImpl.class)
public interface IDeptService {
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

    /**
     * 查询用户所属部门信息
     * 
     * @param userId
     * @return
     */
    Dept getDeptByUser(Integer userId);
}
