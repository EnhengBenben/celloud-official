package com.celloud.backstage.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.DataFileMapper;
import com.celloud.backstage.mapper.ProjectMapper;
import com.celloud.backstage.mapper.ReportMapper;
import com.celloud.backstage.model.DataFile;
import com.celloud.backstage.service.DataService;
import com.celloud.backstage.utils.PropertiesUtil;


/**
 * 数据管理服务实现类
 * 
 * @author han
 * @date 2015年12月23日 下午6:20:22
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
    Logger logger=LoggerFactory.getLogger(DataServiceImpl.class);
    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ReportMapper reportMapper;


    @Override
    public int addDataInfo(DataFile data) {
        dataFileMapper.addDataInfo(data);
        return data.getFileId();
    }

    @Override
    public int updateDataInfoByFileId(DataFile data) {
        return dataFileMapper.updateDataInfoByFileId(data);
    }

    @Override
    public boolean removeData(Integer userId) {
        boolean flag=false;
        try {
            if(userId!=null){
                List<DataFile> list=dataFileMapper.selectByUserId(userId);
                dataFileMapper.deletefileProjectRelatByUserId(userId);
                dataFileMapper.deleteDataFileByUserId(userId);
                for(DataFile data:list){
                    File file=new File(data.getPath());
                    if(file.exists()&&file.isFile()){
                        file.delete();
                    }
                }
                projectMapper.deleteProjectByUserId(userId);
                projectMapper.deleteProjectShareByFrom(userId);
                reportMapper.deleteByUserId(userId);
                flag=true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return flag;
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
