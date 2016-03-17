package com.celloud.manager.service;

import java.util.List;
import java.util.Map;

import com.celloud.manager.model.Company;

public interface CompanyService {
    public Map<String,Object> companyGuideCount(Integer companyId);
    public Map<String,Object> getCompanyGuideData(Integer companyId);
    public Map<String,Object> getBigCustomerUserCountByMon();
    
    public List<Company> getCompany(Integer companyId);
}
