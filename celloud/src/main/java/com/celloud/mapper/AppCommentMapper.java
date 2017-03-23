package com.celloud.mapper;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> selectBySelective(Page page, AppComment appComment);
}