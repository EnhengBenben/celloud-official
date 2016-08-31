package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.TagMapper;
import com.celloud.model.mysql.Tag;
import com.celloud.service.TagService;

/**
 * 标签服务类
 * 
 * @author leamo
 * @date 2016年8月30日 上午11:45:04
 */
@Service("tagService")
public class TagServiceImpl implements TagService {
    @Resource
    TagMapper tagMapper;
    
    @Override
    public List<Tag> findTags(Integer userId) {
        return tagMapper.findTags(userId, DataState.ACTIVE);
    }

}
