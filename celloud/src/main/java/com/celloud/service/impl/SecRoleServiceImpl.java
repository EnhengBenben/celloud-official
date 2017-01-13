package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.UserSecRole;
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

	@Override
	public List<SecRole> getRolesByUserId(Integer userId) {
		List<SecRole> roles = roleMapper.findRolesByUserId(userId);
		Map<String, SecRole> map = new HashMap<>();
		for (SecRole secRole : roles) {
			if (UserSecRole.HOSPITALMANAGER.equals(secRole.getId())) {
				continue;
			}
			if (UserSecRole.PLATFORM.equals(secRole.getId())) {
				continue;
			}
			map.put(secRole.getCode(), secRole);
		}
		roles = new ArrayList<>();
		for (Entry<String, SecRole> role : map.entrySet()) {
			roles.add(role.getValue());
		}
		return roles;
	}

	@Override
	public int insertUserRoles(Integer userId, Integer[] roleIds, Integer authFrom) {
		return roleMapper.insertUserRoles(userId, roleIds, authFrom);
	}

	@Override
	public int deleteByAuthFrom(Integer userId, Integer[] roleIds, Integer authFrom) {
		return roleMapper.deleteByAuthFrom(userId, roleIds, authFrom);
	}

	@Override
	public List<SecRole> getRoles(Integer userId, Integer authFrom) {
		return roleMapper.findRoles(userId, authFrom);
	}

}
