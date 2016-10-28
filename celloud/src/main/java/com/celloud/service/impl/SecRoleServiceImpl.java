package com.celloud.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.SecRoleMapper;
import com.celloud.model.mysql.SecRole;
import com.celloud.service.SecRoleService;

@Service("secRoleServiceImpl")
public class SecRoleServiceImpl implements SecRoleService {
    @Resource
    private SecRoleMapper roleMapper;

    @Override
    public Set<String> findRolesByUserId(Integer userId) {
        List<SecRole> roles = roleMapper.findRolesByUserId(userId);
        Set<String> roleNames = new HashSet<>();
        if (roles != null && !roles.isEmpty()) {
            for (SecRole role : roles) {
                roleNames.add(role.getCode());
            }
        }
        return roleNames;
    }

}
