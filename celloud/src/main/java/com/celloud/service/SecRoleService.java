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

	/**
	 * 查询authFrom授予userId的所有权限
	 * 
	 * @param userId
	 * @param authFrom
	 * @return
	 * @author lin
	 * @date 2017年1月9日上午10:52:27
	 */
	List<SecRole> getRoles(Integer userId, Integer authFrom);

	int insertUserRoles(Integer userId, Integer[] roleIds, Integer authFrom);

	int deleteByAuthFrom(Integer userId, Integer[] roleIds, Integer authFrom);
}
