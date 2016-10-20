package com.celloud.service;

import java.util.Set;

public interface SecRoleService {
    /**
     * 查找用户的所有角色
     * 
     * @param userId
     * @return
     */
    Set<String> findRolesByUserId(Integer userId);

}
