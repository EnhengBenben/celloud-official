package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.DataFileMapper;
import com.celloud.model.DataFile;
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
        dataFileMapper.addDataInfo(data);
        return data.getFileId();
    }

    @Override
    public int updateDataInfoByFileId(DataFile data) {
        return dataFileMapper.updateDataInfoByFileId(data);
    }

    @Override
    public List<Map<String, String>> sumData(Integer userId, Integer time) {
        return dataFileMapper.sumDataByTime(userId, time, DataState.ACTIVE);
    }
}
