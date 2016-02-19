package com.celloud.backstage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.DataFileMapper;
import com.celloud.backstage.model.DataFile;
import com.celloud.backstage.service.DataService;


/**
 * 数据管理服务实现类
 * 
 * @author han
 * @date 2015年12月23日 下午6:20:22
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Resource
    DataFileMapper dataFileMapper;


    @Override
    public int addDataInfo(DataFile data) {
        dataFileMapper.addDataInfo(data);
        return data.getFileId();
    }

    @Override
    public int updateDataInfoByFileId(DataFile data) {
        return dataFileMapper.updateDataInfoByFileId(data);
    }
}
