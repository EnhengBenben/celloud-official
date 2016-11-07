package com.celloud.manager.service;

import java.util.List;
import java.util.Set;

import com.celloud.manager.model.SecRole;

public interface SecRoleService {
    /**
     * 查找用户的所有角色
     * 
     * @param userId
     * @return
     */
    Set<String> findRolesByUserId(Integer userId);

    /**
     * 查找用户的角色列表
     */
    public List<SecRole> findRoleListByUserId(Integer userId);

    public void deleteUserRoleByUserId(Integer userId);

    /**
     * 
     * @description 根据角色id数组, 查找角色
     * @author miaoqi
     * @date 2016年10月26日下午4:21:10
     *
     * @param rolsIds
     * @return
     */
    public List<SecRole> findRoleListByIds(Integer[] roleIds);
}
