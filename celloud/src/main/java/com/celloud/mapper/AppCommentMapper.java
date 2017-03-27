package com.celloud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.celloud.model.mysql.AppComment;
import com.celloud.page.Page;

/**
 * 
 * @description App评论Mapper
 * @author miaoqi
 * @date 2017年3月23日 下午1:17:15
 */
public interface AppCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppComment record);

    int insertSelective(AppComment record);

    AppComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppComment record);

    int updateByPrimaryKey(AppComment record);

    /**
     * 
     * @description 分页查询app评论
     * @author miaoqi
     * @date 2017年3月23日 下午5:42:48
     * @param page
     * @param appComment
     * @return
     */
    List<Map<String, Object>> selectBySelective(Page page, @Param("appComment") AppComment appComment);

    /**
     * 
     * @description 查询app评论
     * @author miaoqi
     * @date 2017年3月23日 下午5:43:00
     * @param appComment
     * @return
     */
    List<Map<String, Object>> selectBySelectiveNoPage(AppComment appComment);

    /**
     * 
     * @description 统计appId下每个评分的数量
     * @author miaoqi
     * @date 2017年3月23日 下午5:42:29
     * @param appId
     * @return
     */
    @MapKey("score")
    Map<String, Map<String, Integer>> countScore(@Param("appId") Integer appId);
}