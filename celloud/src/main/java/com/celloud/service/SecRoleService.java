package com.celloud.service;

import java.util.List;
import java.util.Set;

import com.celloud.model.mysql.SecRole;

public interface SecRoleService {
    /**
     * 查找用户的所有角色
     * 
     * @param userId
     * @return
     */
    Set<String> findRolesByUserId(Integer userId);

	List<SecRole> getRolesByUserId(Integer userId);

	int insertUserRoles(Integer userId, Integer[] roleIds, Integer authFrom);

	int deleteByAuthFrom(Integer userId, Integer[] roleIds, Integer authFrom);
}
