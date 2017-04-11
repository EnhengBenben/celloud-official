package com.celloud.service;

import java.util.Map;

import com.celloud.page.Page;
import com.celloud.page.PageList;

/**
 * 
 * @description App评价Service
 * @author miaoqi
 * @date 2017年3月23日 下午1:15:24
 */
public interface AppCommentService {

    /**
     * 
     * @description 根据userId和appId查询一条评论
     * @author miaoqi
     * @date 2017年3月24日 上午9:51:35
     * @param userId
     * @param appId
     * @return
     */
    Map<String, Object> getAppComment(Integer userId, Integer appId);

    /**
     * 
     * @description 根据appId分页查询评论列表
     * @author miaoqi
     * @date 2017年3月23日 下午1:58:45
     * @param page
     * @param appId
     * @return
     */
    PageList<Map<String, Object>> listAppCommentByAppId(Page page, Integer appId);

    /**
     * 
     * @description 保存用户评论
     * @author miaoqi
     * @date 2017年3月23日 下午3:08:51
     * @return
     */
    Boolean saveAppComment(Integer userId, Integer appId, Integer score, String comment);

    /**
     * 
     * @description 根据id更新app的评论
     * @author miaoqi
     * @date 2017年3月23日 下午4:33:10
     * @param id
     * @param userId
     * @param score
     * @param comment
     * @return
     */
    Boolean updateAppComment(Integer id, Integer userId, Integer score, String comment);

    /**
     * 
     * @description 根据appId获取每个评分的数量
     * @author miaoqi
     * @date 2017年3月23日 下午5:28:41
     * @param appid
     * @return
     */
    Map<String, Map<String, Long>> countScore(Integer appId);

    /**
     * 
     * @description 获取app的平均评分
     * @author miaoqi
     * @date 2017年3月29日 上午11:25:39
     * @param appId
     * @return
     */
    String avgScore(Integer appId);

}
