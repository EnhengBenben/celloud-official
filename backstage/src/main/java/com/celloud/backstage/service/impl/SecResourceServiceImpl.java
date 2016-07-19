package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.SecResourceMapper;
import com.celloud.backstage.model.SecResource;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.SecResourceService;

/** 
 * @author MQ: 
 * @date 2016年7月15日 下午1:53:07 
 * @description 
 */
@Service("resourceService")
public class SecResourceServiceImpl implements SecResourceService {

    @Resource
    private SecResourceMapper resourceMapper;

    @Override
    public int addResource(SecResource resource) {
        resource.setCreateDate(new Date());
        return resourceMapper.insert(resource);
    }

    @Override
    public int editResource(SecResource resource) {
        return resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    public PageList<SecResource> pageQuery(Page page, String keyword) {
        List<SecResource> datas = resourceMapper.pageQuery(page, keyword);
        return new PageList<>(page, datas);
    }

    @Override
    public List<SecResource> findAll() {
        return resourceMapper.findAll();
    }

    @Override
    public int checkNameRepeat(String name, Integer id) {
        return resourceMapper.findByName(name, id).size();
    }

    @Override
    public int checkPermissionRepeat(String permission, Integer id) {
        return resourceMapper.findByPermission(permission, id).size();
    }

    @Override
    public int checkPriorityRepeat(Integer priority, Integer id) {
        return resourceMapper.findByPriority(priority, id).size();
    }

    @Override
    public SecResource findById(Integer id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int moveUp(Integer id, Integer parentId, Integer priority) {
        return resourceMapper.moveUp(id, parentId, priority);
    }

    @Override
    public int moveDown(Integer id, Integer parentId, Integer priority) {
        return resourceMapper.moveDown(id, parentId, priority);
    }

}
