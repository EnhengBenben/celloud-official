package com.celloud.manager.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.mapper.SecRoleMapper;
import com.celloud.manager.model.SecRole;
import com.celloud.manager.service.SecRoleService;

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
                roleNames.add(role.getName());
            }
        }
        return roleNames;
    }

    @Override
    public List<SecRole> findRoleListByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    @Override
    public void deleteUserRoleByUserId(Integer userId) {
        roleMapper.deleteByUserId(userId);
    }

    @Override
    public List<SecRole> findRoleListByIds(Integer[] roleIds) {
        return roleMapper.findRoleByIds(roleIds);
    }

}
