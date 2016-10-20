package com.celloud.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.SecResourceMapper;
import com.celloud.model.mysql.SecResource;
import com.celloud.service.SecResourceService;

@Service("secResourceServiceImpl")
public class SecResourceServiceImpl implements SecResourceService {
    @Resource
    private SecResourceMapper resourceMapper;

    @Override
    public Set<String> findPermissionResourcesByUserId(Integer userId) {
        List<SecResource> resources = resourceMapper.findPermissionResourcesByUserId(userId);
        Set<String> permissions = new HashSet<>();
        if (resources != null && !resources.isEmpty()) {
            for (SecResource resource : resources) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

}
