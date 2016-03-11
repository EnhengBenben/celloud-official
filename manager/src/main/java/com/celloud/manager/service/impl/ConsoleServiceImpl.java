package com.celloud.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.DataState;
import com.celloud.manager.constants.ReportPeriod;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.DataFileMapper;
import com.celloud.manager.mapper.ReportMapper;
import com.celloud.manager.mapper.UserMapper;
import com.celloud.manager.service.ConsoleService;

@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private AppMapper appMapper;
    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private ReportMapper reportMapper;
    
    @Override
    public Map<String, Object> totalStatistics(Integer companyId) {
        Map<String,Object> map=new HashMap<String,Object>();
        int userNum=userMapper.countUser(companyId, DataState.ACTIVE);//大客户下的用户数量
        int companyNum=userMapper.countCompany(companyId, DataState.ACTIVE);//大客户下的医院数量
        int appNum=appMapper.countApp(companyId, AppOffline.ON);//大客户下的app数量
        int dataNum=dataFileMapper.countDataFile(companyId, DataState.ACTIVE);
        long dataSize=dataFileMapper.countDataFileSize(companyId, DataState.ACTIVE);
        int reportNum=reportMapper.countReport(companyId, DataState.ACTIVE,ReportPeriod.COMPLETE);
        map.put("userNum", userNum);
        map.put("companyNum", companyNum);
        map.put("appNum", appNum);
        map.put("dataNum", dataNum);
        map.put("dataSize", dataSize);
        map.put("reportNum", reportNum);
        return map;
    }
    
}
