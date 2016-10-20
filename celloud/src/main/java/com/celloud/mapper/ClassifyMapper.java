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
     * 查询分类列表
     * 
     * @param pid
     *            父级分类id,0-查询父分类列表
     * @return
     * @author han
     * @date 2016年1月6日 上午10:08:29
     */
    public List<Classify> getClassify(@Param("pid") Integer pid);

    /**
     * 获取分类信息
     *
     * @param id
     * @return
     * @author han
     * @date 2016年1月6日 上午10:26:23
     */
    public Classify getClassifyById(@Param("classifyId") Integer id);
}