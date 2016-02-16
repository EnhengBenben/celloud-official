package com.celloud.backstage.service;


import java.util.List;

import com.celloud.backstage.model.Dept;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * 部门接口
 *
 * @author han
 * @date 2016年1月27日 下午2:04:49
 */
public interface DeptService {
    
    public PageList<Dept> getDeptByPage(int companyId,Page page);
    
    public int updateDept(Dept dept);
    
    public int addDept(Dept dept);
    
    public int deleteDept(int deptId);
    
    public Dept getDeptById(Integer deptId);
    
    public List<Dept> getDeptByCompanyId(int companyId);
    
}
