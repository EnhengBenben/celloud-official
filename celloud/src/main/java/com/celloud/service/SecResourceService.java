package com.celloud.service;

import java.util.Set;

public interface SecResourceService {
    /**
     * 查找用户已授权的所有资源
     * 
     * @param userId
     * @return
     */
    Set<String> findPermissionResourcesByUserId(Integer userId);

}
