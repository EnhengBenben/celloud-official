package com.celloud.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.ReportPeriod;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.CompanyMapper;
import com.celloud.manager.mapper.ReportMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Company;
import com.celloud.manager.service.CompanyService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private CompanyMapper companyMapper;
    
    @Resource
    private ReportMapper reportMapper;
    
    @Resource
    private AppMapper appMapper;
    
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

    @Override
    public List<Map<String, Object>> getCompanyReport(Integer companyId) {
        List<Map<String, Object>> list=reportMapper.countReportOfApp(companyId, DataState.ACTIVE,ReportPeriod.COMPLETE , PropertiesUtil.testAccountIds, AppOffline.ON);
        List<Company> cms=companyMapper.getCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
        Map<Integer,Object> groups=new HashMap<Integer,Object>();
        for(Company c:cms){
            groups.put(c.getCompanyId(), new ArrayList<Object>());
        }
        for(Map<String, Object> map:list){
            List<Map<String, Object>> groupData=(List<Map<String, Object>>) groups.get(map.get("companyId"));
            groupData.add(map);
        }
        List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
        for(Company c:cms){
            List<Map<String, Object>> groupData=(List<Map<String, Object>>) groups.get(c.getCompanyId());
            Map<String, Object> map=new HashMap<String,Object>();
            map.put("companyId", c.getCompanyId());
            map.put("companyName",c.getCompanyName());
            for(Map<String, Object> temp:groupData){
                map.put("appId"+temp.get("appId"), temp.get("reportNum"));
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public List<App> getAppOfBigCustomer(Integer companyId) {
        return appMapper.getAppOfBigCustomer(companyId, AppOffline.ON);
    }

}
