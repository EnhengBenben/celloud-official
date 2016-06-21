package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.SampleType;
import com.celloud.mapper.SampleMapper;
import com.celloud.model.mysql.Sample;
import com.celloud.service.SampleService;

/**
 * 样本收集管理接口实现
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2016年6月20日 上午11:22:09
 */
@Service("sampleService")
public class SampleServiceImple implements SampleService {
    @Resource
    SampleMapper sampleMapper;

    @Override
    public Integer saveSample(String sampleName, Integer userId) {
        Sample s = new Sample();
        s.setSampleName(sampleName);
        s.setCreateDate(new Date());
        s.setUserId(userId);
        return sampleMapper.insertSelective(s);
    }

    @Override
    public Integer commitSamples(List<Integer> sampleIds) {
        return sampleMapper.updateAddTypeById(sampleIds, SampleType.ISADD,
                new Date());
    }

    @Override
    public List<Sample> allUnaddSample(Integer userId) {
        return sampleMapper.selectAllByUser(userId, SampleType.NOTADD,
                DataState.ACTIVE);
    }

    @Override
    public Boolean checkSample(String sampleName, Integer userId) {
        return sampleMapper.selectByName(userId, sampleName,
                DataState.ACTIVE) != null;
    }

    @Override
    public Integer delete(Integer sampleId) {
        return sampleMapper.deleteByPrimaryKey(sampleId);
    }

    @Override
    public Integer deleteList(List<Integer> sampleIds) {
        return sampleMapper.deleteList(sampleIds);
    }

}
