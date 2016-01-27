package com.celloud.backstage.service;


import java.util.List;

import com.celloud.backstage.model.Company;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * 公司接口
 *
 * @author han
 * @date 2016年1月25日 下午1:48:16
 */
public interface CompanyService {
    
    public PageList<Company> getCompanyByPage(Page page);
    
    public int updateCompany(Company company);
    
    public int addCompany(Company company);
    
    public int deleteCompany(int companyId);
    
    public Company getCompanyById(Integer companyId);
    /**
     * 获取所有未删除的公司
     *
     * @return
     * @author han
     * @date 2016年1月27日 下午2:49:23
     */
    public List<Company> getAllCompany();
    
}
