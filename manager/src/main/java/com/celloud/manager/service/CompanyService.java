package com.celloud.manager.service;

import java.util.List;
import java.util.Map;

import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;

public interface CompanyService {
    public Map<String, Object> companyGuideCount(Integer companyId);

    public Map<String, Object> getCompanyGuideData(Integer companyId);

    public Map<String, Object> getBigCustomerUserCountByMon();

    public List<Company> getCompany(Integer companyId);

    public List<Map<String, Object>> getCompanyReport(Integer companyId);

    public List<App> getAppOfBigCustomer(Integer companyId);

    public List<Map<String, Object>> bigCustomerDataCount();

    public List<Map<String, Object>> getCompanyNumCount();

    public Company getCompanyById(Integer companyId);

    public PageList<Company> getCompanyByPage(Page page, String keyword);

    public Company getCompanyByIdAndState(Integer companyId);

    /**
     * 获取所有未删除的公司
     *
     * @return
     * @author han
     * @date 2016年1月27日 下午2:49:23
     */
    public List<Company> getAllCompany();

    /**
     * 获取符合select2格式的map
     */
    public List<Map<String, String>> getAllToSelect();

    /**
     * 增加公司
     */
    public int addCompany(Company company);
}
