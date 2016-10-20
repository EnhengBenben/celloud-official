package com.celloud.backstage.mapper;

import java.util.List;

import com.celloud.backstage.model.Classify;

public interface ClassifyMapper {
    int deleteByPrimaryKey(Integer classifyId);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer classifyId);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
    
    public List<Classify> getLeaf();
}