package com.celloud.backstage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.backstage.model.App;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);
    /**
     * 获取app列表
     *
     * @param companyId 大客户编号
     * @param attribute 软件可添加权限 0-public（所有人可添加）  1-private（软件所有公司的用户可添加）
     * @return
     * @author han
     * @date 2016年1月29日 下午2:08:29
     */
    
    List<App> getAppListByCompany(@Param("companyId") int companyId,@Param("attribute") Integer attribute,@Param("offLine") Integer offLine);
    
    /**
     * 根据attribute获取非工具类appList
     *
     * @param attribute
     * @param runType 运行方式 0-直接运行 1-需添加然后运行 2-可直  接运行可添加
     * @return
     * @author han
     * @date 2016年1月29日 下午2:12:11
     */
    
    List<App> getAppListAdded(@Param("attribute") Integer attribute,@Param("offLine") Integer offLine);
}