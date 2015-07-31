package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.CompanyDaoImpl;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Company;

@ImplementedBy(CompanyDaoImpl.class)
public interface ICompanyDao {
    /**
     * 新增公司信息
     * 
     * @param company
     * @return
     */
    int addCompany(Company company);

    /**
     * 查询所有公司信息
     * 
     * @return
     */
    List<Company> getAllCompany();

    /**
     * 分页查询公司信息
     * 
     * @param page
     * @return
     */
    PageList<Company> getCompanyPage(Page page);

    /**
     * 修改公司信息
     * 
     * @param company
     * @return
     */
    int updateCompany(Company company);

    /**
     * 根据companyId查询公司信息
     * 
     * @param companyId
     * @return
     */
    Company getCompany(int companyId);

	/**
	 * 修改公司是否删除的状态
	 * 
	 * @param companyId
	 * @param state
	 * @return
	 */
	int updateCompany(int companyId, int state);

	/**
	 * 获取用户所属医院
	 * 
	 * @param userId
	 * @return
	 */
	Company getCompanyByUserId(int userId);
}
