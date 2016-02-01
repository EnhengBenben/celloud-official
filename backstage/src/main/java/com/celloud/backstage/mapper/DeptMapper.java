package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.Dept;
import com.celloud.backstage.page.Page;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
    
    /**
     * 获取分页部门列表
     *
     * @param state
     * @param page
     * @return
     * @author han
     * @date 2016年1月27日 下午2:08:11
     */
    List<Dept> getDeptByPage(@Param("state") int state,@Param("companyId")int companyId,Page page);
    
    public int updateDept(Dept dept);
    
    public int addDept(Dept dept);
    
    public int updateDeptState(@Param("deptId")int deptId,@Param("state") int state);
    
    public Dept getDeptById(@Param("deptId") int deptId,@Param("state") int state);
    
    public List<Dept> getDeptByCompanyId(@Param("state") int state,@Param("companyId") int companyId);
}