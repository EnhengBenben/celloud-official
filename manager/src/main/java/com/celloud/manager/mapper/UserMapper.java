package com.celloud.manager.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.User;

public interface UserMapper {
    public User checkLogin(User user);
    
    /**
     * 统计大客户下的用户数量
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 上午11:27:10
     */
    public int countUser(@Param("companyId")Integer companyId,@Param("state")Integer state);
    
    /**
     * 统计大客户下的医院数量
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月10日 下午1:36:17
     */
    public int countCompany(@Param("companyId")Integer companyId,@Param("state")Integer state);
}
