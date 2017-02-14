package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.Tag;

public interface TagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer tagId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> findTags(@Param("userId") Integer userId,
            @Param("state") Integer state);

    /**
     * 
     * @author MQ
     * @date 2016年9月1日上午11:20:20
     * @description 根据用户id查找产品标签
     * @param userId
     * @param state
     * @return
     *
     */
    List<Tag> selectProductTags(@Param("userId") Integer userId, @Param("state") Integer state);

    /**
     * 
     * @description 查询产品标签返回给select2
     * @author miaoqi
     * @date 2017年2月13日 下午2:50:13
     * @param userid
     * @param state
     * @return
     */
    List<Map<String, String>> listProductTagsToSelect(@Param("userId") Integer userId, @Param("state") Integer state);
    
}