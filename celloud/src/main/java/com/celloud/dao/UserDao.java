package com.celloud.dao;

import java.util.Map;

import com.celloud.dao.impl.UserDaoImpl;
import com.google.inject.ImplementedBy;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-9-28上午11:36:20
 * @version Revision: 1.0
 */
@ImplementedBy(UserDaoImpl.class)
public interface UserDao {
    /**
     * 获取用户详细信息（个人信息，所属部门信息，所属公司信息）
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> getUserAllInfo(Integer userId);
}
