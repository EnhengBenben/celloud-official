package com.celloud.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.mapper.CompanyMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.Company;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private CompanyMapper companyMapper;
    
    @Override
    public Map<String, Object> companyGuideCount(Integer companyId) {
        Map<String,Object> map=new HashMap<String,Object>();
        int userNum=userMapper.countUser(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//用户数量
        int companyNum=userMapper.countCompany(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//医院数量
        List<Map<String,Object>> companyData=companyMapper.getCompanyNumOfMon(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds,"desc");
        map.put("userNum", userNum);
        map.put("companyNum", companyNum);
        map.put("companyData", companyData);
        return map;
    }

    @Override
    public Map<String, Object> getCompanyGuideData(Integer companyId) {
        Map<String,Object> map=new HashMap<String,Object>();
        List<Map<String,Integer>>provinceData=userMapper.countCompanyByProvince(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//医院地理分布统计
        List<Map<String,Object>> companyData=companyMapper.getCompanyNumOfMon(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds,"asc");
        map.put("provinceData", provinceData);
        map.put("companyData", companyData);
        return map;
    }

    @Override
    public Map<String, Object> getBigCustomerUserCountByMon() {
        List<Company> coms=companyMapper.getBigCustomerCompany(UserRole.BIG_CUSTOMER, DataState.ACTIVE);
        Map<String,Object> map=null;
        if(coms!=null&&coms.size()>0){
            List<String> seriesName=new ArrayList<String>();
            List<Object> list=new ArrayList<Object>();
            map=new HashMap<String,Object>();
            for(Company c:coms){
                List<Map<String,Object>> obj=userMapper.getUserNumOfMon(c.getCompanyId(), DataState.ACTIVE, PropertiesUtil.testAccountIds);
                list.add(obj);
                seriesName.add(c.getCompanyName());
            }
            map.put("seriesName", seriesName);
            map.put("userMon", list);
        }
        return map;
    }

    @Override
    public List<Company> getCompany(Integer companyId) {
        return companyMapper.getCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

}
