package com.celloud.manager.mapper;

import java.util.List;
import java.util.Map;

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
    
    /**
     * 医院地理分布统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月11日 下午1:38:05
     */
    public List<Map<String,Integer>> countCompanyByProvince(@Param("companyId")Integer companyId,@Param("state")Integer state);
    
    /**
     * 用户登录统计
     *
     * @param companyId
     * @param state
     * @return
     * @author han
     * @date 2016年3月11日 下午2:06:08
     */
    public List<Map<String,Integer>> countLogin(@Param("companyId")Integer companyId,@Param("state")Integer state);
}
