package com.celloud.manager.mapper;

import org.apache.ibatis.annotations.Param;

import com.celloud.manager.model.App;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKeyWithBLOBs(App record);

    int updateByPrimaryKey(App record);
    
    /**
     * 统计大客户下的app数量
     *
     * @param companyId
     * @param offLine
     * @return
     * @author han
     * @date 2016年3月10日 下午2:10:51
     */
    public int countApp(@Param("companyId") Integer companyId,@Param("offLine") int offLine);
}