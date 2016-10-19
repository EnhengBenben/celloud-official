package com.celloud.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.UserRole;
import com.celloud.manager.mapper.CompanyMapper;
import com.celloud.manager.mapper.DataFileMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.model.Company;
import com.celloud.manager.model.User;
import com.celloud.manager.service.DataService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("dataService")
public class DataServiceImpl implements DataService{

    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private CompanyMapper companyMapper;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public int totalDataNum(Integer companyId) {
        return dataFileMapper.countDataFile(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
    }

    @Override
    public Long totalDataSize(Integer companyId) {
        return dataFileMapper.countDataFileSize(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
    }

    @Override
    public Map<String,Object> getBigCustomerDataCountByMon() {
        List<Company> coms=companyMapper.getBigCustomerCompany(UserRole.BIG_CUSTOMER, DataState.ACTIVE);
        Map<String,Object> map=null;
        if(coms!=null&&coms.size()>0){
            List<String> seriesName=new ArrayList<String>();
            List<Object> list=new ArrayList<Object>();
            map=new HashMap<String,Object>();
            for(Company c:coms){
                List<Map<String,Object>> obj=dataFileMapper.countDataFileOfMonth(c.getCompanyId(), DataState.ACTIVE, PropertiesUtil.testAccountIds);
                list.add(obj);
                seriesName.add(c.getCompanyName());
            }
            map.put("seriesName", seriesName);
            map.put("dataMon", list);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByMon(Integer companyId,String order) {
        return dataFileMapper.countDataFileByMonth(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds,order);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByUser(Integer companyId) {
        return dataFileMapper.countDataFileByUser(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCountByCompany(Integer companyId) {
        return dataFileMapper.countDataFileByCompany(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getBigCustomerDataCount() {
        return dataFileMapper.countBigCustomerDataFile(DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<User> getUser(Integer companyId) {
        return userMapper.findUserByBigCustomer(companyId, DataState.ACTIVE, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getUserData(String userIds, String start, String end) {
        if(StringUtils.isBlank(userIds)){
            return null;
        }
        if(start!=null&&end!=null){
            return dataFileMapper.getExportData(userIds, start, end);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getWeekUserLogin(Integer companyId) {
        return dataFileMapper.getWeekUserLogin(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getWeekAppRun(Integer companyId) {
        return dataFileMapper.getWeekAppRun(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getWeekDataSize(Integer companyId) {
        return dataFileMapper.getWeekDataSize(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekUserLogin(Integer companyId) {
        return dataFileMapper.getHistoryWeekUserLogin(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekActiveUser(Integer companyId) {
        return dataFileMapper.getHistoryWeekActiveUser(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekAppRun(Integer companyId) {
        return dataFileMapper.getHistoryWeekAppRun(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekAppActive(Integer companyId) {
        return dataFileMapper.getHistoryWeekAppActive(companyId, PropertiesUtil.testAccountIds);
    }

    @Override
    public List<Map<String, Object>> getHistoryWeekDataSize(Integer companyId) {
        return dataFileMapper.getHistoryWeekDataSize(companyId, PropertiesUtil.testAccountIds);
    }

}
