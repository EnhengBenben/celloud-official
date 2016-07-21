package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
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
    public List<SecResource> findAllActive() {
        return resourceMapper.findAllActive(DataState.ACTIVE);
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
        SecResource preResource = resourceMapper.getPrePriority(parentId, priority);
        if (preResource != null) {
            SecResource currentResource = resourceMapper.selectByPrimaryKey(id);
            int temp = currentResource.getPriority();
            currentResource.setPriority(preResource.getPriority());
            preResource.setPriority(temp);
            resourceMapper.updateByPrimaryKeySelective(preResource);
            return resourceMapper.updateByPrimaryKeySelective(currentResource);
        } else {
            return 0;
        }
    }

    @Override
    public int moveDown(Integer id, Integer parentId, Integer priority) {
        SecResource nextResource = resourceMapper.getNextPriority(parentId, priority);
        if (nextResource != null) {
            SecResource currentResource = resourceMapper.selectByPrimaryKey(id);
            int temp = currentResource.getPriority();
            currentResource.setPriority(nextResource.getPriority());
            nextResource.setPriority(temp);
            resourceMapper.updateByPrimaryKeySelective(nextResource);
            return resourceMapper.updateByPrimaryKeySelective(currentResource);
        } else {
            return 0;
        }
    }

}
