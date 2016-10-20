package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.ClassifyMapper;
import com.celloud.backstage.model.Classify;
import com.celloud.backstage.service.ClassifyService;

@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService{
    
    @Resource
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> getLeaf() {
        return classifyMapper.getLeaf();
    }

}
