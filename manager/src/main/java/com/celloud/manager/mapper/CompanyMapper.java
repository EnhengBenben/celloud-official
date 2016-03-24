package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.Company;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    
    public List<Company> getBigCustomerCompany(@Param("role")Integer role,@Param("state")Integer state);
    
    /**
     * 大客户的医院数量
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月16日 下午3:44:02
     */
    public List<Map<String,Object>> getCompanyNumOfMon(@Param("companyId")Integer companyId,@Param("state")Integer state,@Param("testAccountIds")String testAccountIds,@Param("order")String order);
    
    /**
     * 获取大客户下的医院列表
     *
     * @param companyId
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月17日 下午4:32:42
     */
    public List<Company> getCompany(@Param("companyId")Integer companyId,@Param("state")Integer state,@Param("testAccountIds")String testAccountIds);
    /**
     * 所有大客户的医院数量统计
     *
     * @param state
     * @param testAccountIds
     * @return
     * @author han
     * @date 2016年3月21日 上午11:00:31
     */
    public List<Map<String,Object>> getCompanyNumCount(@Param("state")Integer state,@Param("testAccountIds")String testAccountIds);
}