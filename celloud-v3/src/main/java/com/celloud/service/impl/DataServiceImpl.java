package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.mapper.DataFileMapper;
import com.celloud.model.DataFile;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.DataService;

/**
 * 数据管理服务实现类 重构
 * 
 * @author han
 * @date 2015年12月23日 下午6:20:22
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Resource
    DataFileMapper dataFileMapper;

    @Override
    public Integer countData(Integer userId) {
        return dataFileMapper.countData(userId, DataState.ACTIVE);
    }

    @Override
    public Long sumData(Integer userId) {
        return dataFileMapper.sumData(userId, DataState.ACTIVE);
    }

    @Override
    public List<Map<String, String>> countData(Integer userId, Integer time) {
        return dataFileMapper.countDataByTime(userId, time, DataState.ACTIVE);
    }

    @Override
    public int addDataInfo(DataFile data) {
        return dataFileMapper.addDataInfo(data);
    }

    @Override
    public int updateDataInfoByFileId(DataFile data) {
        return dataFileMapper.updateDataInfoByFileId(data);
    }

    @Override
    public List<Map<String, String>> sumData(Integer userId, Integer time) {
        // TODO Auto-generated method stub
        return dataFileMapper.sumDataByTime(userId, time, DataState.ACTIVE);
    }

    @Override
    public PageList<DataFile> dataAllList(Page page, Integer userId) {
        List<DataFile> lists = dataFileMapper.findAllDataLists(page, userId,
                DataState.ACTIVE, ReportType.DATA, ReportPeriod.COMPLETE);
        return new PageList<>(page, lists);
    }

    @Override
    public PageList<DataFile> dataLists(Page page, Integer userId,
            String condition, int sort, String sortDateType, String sortNameType) {
        List<DataFile> lists = dataFileMapper.findDataLists(page, userId,
                condition, sort, sortDateType, sortNameType, DataState.ACTIVE,
                ReportType.DATA,
                ReportPeriod.COMPLETE);
        return new PageList<>(page, lists);
    }
}
