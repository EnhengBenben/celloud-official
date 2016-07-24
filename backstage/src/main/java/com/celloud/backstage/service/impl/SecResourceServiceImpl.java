package com.celloud.backstage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.DataState;
import com.celloud.backstage.mapper.SecResourceMapper;
import com.celloud.backstage.model.SecResource;
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

    /**
     * 
     * @author MQ
     * @date 2016年7月24日下午3:08:42
     * @description 方式一:在后台处理
     *
     */
    @Override
    public List<SecResource> list() {
        List<SecResource> list = resourceMapper.list();
        // 遍历集合, 根据parentId进行分组
        Map<Integer, List<SecResource>> map = new HashMap<Integer, List<SecResource>>();
        for (SecResource r : list) {
            if (map.containsKey(r.getParentId())) {
                map.get(r.getParentId()).add(r);
            } else {
                List<SecResource> temp = new ArrayList<SecResource>();
                temp.add(r);
                map.put(r.getParentId(), temp);
            }
        }
        List<SecResource> pageList = new ArrayList<SecResource>();
        recursionResource(map, pageList, 0);
        return pageList;

    }

    public void recursionResource(Map<Integer, List<SecResource>> map, List<SecResource> pageList, Integer parentId) {
        for (SecResource r : map.get(parentId)) {
            pageList.add(r);
            if (map.containsKey(r.getId())) {
                recursionResource(map, pageList, r.getId());
            }
        }
    }

    @Override
    public List<SecResource> findAllActive() {
        List<SecResource> list = resourceMapper.findAllActive(DataState.ACTIVE);
        // 遍历集合, 根据parentId进行分组
        Map<Integer, List<SecResource>> map = new HashMap<Integer, List<SecResource>>();
        for (SecResource r : list) {
            if (map.containsKey(r.getParentId())) {
                map.get(r.getParentId()).add(r);
            } else {
                List<SecResource> temp = new ArrayList<SecResource>();
                temp.add(r);
                map.put(r.getParentId(), temp);
            }
        }
        List<SecResource> pageList = new ArrayList<SecResource>();
        recursionResource(map, pageList, 0);
        return pageList;

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
    public int checkPriorityRepeat(Integer priority, Integer id, Integer parentId) {
        return resourceMapper.findByPriority(priority, id, parentId).size();
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
