package com.celloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.mapper.ClassifyMapper;
import com.celloud.model.mysql.Classify;
import com.celloud.service.ClassifyService;

@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> listClassifyByPid(Integer pid) {
        Classify queryClassify = new Classify();
        queryClassify.setClassifyPid(pid);
        return classifyMapper.selectBySelective(queryClassify);
    }

}
