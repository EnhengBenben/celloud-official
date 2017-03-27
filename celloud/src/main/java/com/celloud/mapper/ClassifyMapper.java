package com.celloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Classify;

public interface ClassifyMapper {
    int deleteByPrimaryKey(Integer classifyId);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer classifyId);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);

    /**
     * 
     * @description 根据不为空的条件查询分类列表
     * @author miaoqi
     * @date 2017年3月23日 下午6:26:31
     * @param classify
     * @return
     */
    List<Classify> selectBySelective(Classify classify);

    /**
     * 
     * @description 根据appId查询app分类
     * @author miaoqi
     * @date 2017年3月24日 下午3:59:11
     * @param appId
     * @return
     */
    List<Classify> selectClassifyByAppId(@Param("appId") Integer appId);
}