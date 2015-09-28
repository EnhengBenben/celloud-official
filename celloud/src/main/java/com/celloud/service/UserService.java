package com.celloud.service;

import java.util.Map;

import com.celloud.service.impl.UserServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28下午1:37:40
 * @version Revision: 1.0
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
    /**
     * 获取用户详细信息（个人信息，所属部门信息，所属公司信息）
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> getUserAllInfo(Integer userId);
}
