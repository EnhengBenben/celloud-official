package com.celloud.manager.service;

import java.util.Map;

public interface CompanyService {
    public Map<String,Object> companyGuideCount(Integer companyId);
    public Map<String,Object> getCompanyGuideData(Integer companyId);
    public Map<String,Object> getBigCustomerUserCountByMon();
}
