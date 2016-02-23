package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ClassifyMapper;
import com.celloud.model.mysql.Classify;
import com.celloud.service.ClassifyService;

@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService {
    @Resource
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> getClassify(Integer pid) {
        return classifyMapper.getClassify(pid);
    }

    @Override
    public Classify getClassifyById(Integer id) {
        return classifyMapper.getClassifyById(id);
    }

}
