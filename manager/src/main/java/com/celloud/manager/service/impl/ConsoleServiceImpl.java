package com.celloud.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.ReportPeriod;
import com.celloud.manager.constants.ReportType;
import com.celloud.manager.mapper.ActionLogMapper;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.DataFileMapper;
import com.celloud.manager.mapper.ReportMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.service.ConsoleService;
import com.celloud.manager.utils.PropertiesUtil;

@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService{
    Logger logger=LoggerFactory.getLogger(ConsoleServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private AppMapper appMapper;
    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private ActionLogMapper logMapper;
    
    @Override
    public Map<String, Object> totalStatistics(Integer companyId) {
        Map<String,Object> map=new HashMap<String,Object>();
        int userNum=userMapper.countUser(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//大客户下的用户数量
        int companyNum=userMapper.countCompany(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//大客户下的医院数量
        int appNum=appMapper.countApp(companyId, AppOffline.ON);//大客户下的app数量
        int dataNum=dataFileMapper.countDataFile(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
        Long dataSize=dataFileMapper.countDataFileSize(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
        Integer reportNum=reportMapper.countReport(companyId, DataState.ACTIVE,ReportPeriod.COMPLETE,PropertiesUtil.testAccountIds);
        map.put("userNum", userNum);
        map.put("companyNum", companyNum);
        map.put("appNum", appNum);
        map.put("dataNum", dataNum);
        map.put("dataSize", dataSize);
        map.put("reportNum", reportNum);
        return map;
    }

    @Override
    public Map<String, Object> getStatisticsData(Integer companyId) {
        Map<String,Object> map=new HashMap<String,Object>();
        List<Map<String,Integer>>provinceData=userMapper.countCompanyByProvince(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);//医院地理分布统计
        List<Map<String,Integer>>loginData=userMapper.countLogin(companyId, DataState.ACTIVE,PropertiesUtil.testAccountIds);
        List<Map<String,Integer>>appRunData=appMapper.countAppRunNum(companyId, AppOffline.ON, ReportType.PROJECT, ReportPeriod.COMPLETE,PropertiesUtil.testAccountIds);
        List<Map<String,Integer>>userRunData=appMapper.countAppRunNumByUser(companyId, AppOffline.ON, ReportType.PROJECT, ReportPeriod.COMPLETE,PropertiesUtil.testAccountIds);
        List<Map<String,Integer>>browserData=logMapper.countBrowser(PropertiesUtil.testAccountIds);
        map.put("provinceData", provinceData);
        map.put("loginData", loginData);
        map.put("appRunData", appRunData);
        map.put("userRunData", userRunData);
        map.put("browserData", browserData);
        return map;
    }

    @Override
    public List<Map<String, Integer>> getBrowserData() {
        logger.info("测试账号：{}",PropertiesUtil.testAccountIds);
        return logMapper.countBrowser(PropertiesUtil.testAccountIds);
    }
    
}
