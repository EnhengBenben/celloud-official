package com.celloud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.mapper.AppCommentMapper;
import com.celloud.model.mysql.AppComment;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppCommentService;

/**
 * 
 * @description App评价Service实现类
 * @author miaoqi
 * @date 2017年3月23日 下午1:16:06
 */
@Service
public class AppCommentServiceImpl implements AppCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppCommentServiceImpl.class);

    @Autowired
    private AppCommentMapper appCommentMapper;

    @Override
    public PageList<Map<String, Object>> listAppCommentByAppId(Page page, Integer appId) {
        AppComment queryAppComment = new AppComment();
        queryAppComment.setAppId(appId);
        List<Map<String, Object>> datas = appCommentMapper.selectBySelective(page, queryAppComment);
        return new PageList<>(page, datas);
    }

    @Override
    public Boolean saveAppComment(Integer userId, Integer appId, Integer score, String comment) {
        AppComment queryAppComment = new AppComment();
        queryAppComment.setUserId(userId);
        queryAppComment.setAppId(appId);
        List<Map<String, Object>> list = appCommentMapper.selectBySelectiveNoPage(queryAppComment);
        if (list.size() > 0) {
            LOGGER.error("用户 {} 对appId = {}的app已经进行过评论不能保存", userId, appId);
            return false;
        }
        AppComment appComment = new AppComment();
        appComment.setUserId(userId);
        appComment.setAppId(appId);
        appComment.setCreateDate(new Date());
        appComment.setUpdateDate(appComment.getCreateDate());
        appComment.setScore(score);
        appComment.setComment(comment);
        Integer num = appCommentMapper.insertSelective(appComment);
        return num.intValue() == 1;
    }

    @Override
    public Boolean updateAppComment(Integer id, Integer userId, Integer score, String comment) {
        AppComment appComment = appCommentMapper.selectByPrimaryKey(id);
        if (appComment != null) {
            if (userId.intValue() != appComment.getUserId()) {
                LOGGER.error("用户 {} 修改不属于自己的评论, id = {}, appId = {}", userId, id, appComment.getAppId());
                return false;
            } else {
                appComment.setScore(score);
                appComment.setComment(comment);
                Integer num = appCommentMapper.updateByPrimaryKeySelective(appComment);
                return num.intValue() == 1;
            }
        }
        LOGGER.error("用户 {} 修改的评论不存在, id = {}", userId, id);
        return false;
    }

    @Override
    public Map<String, Map<String, Integer>> countScore(Integer appId) {
        return appCommentMapper.countScore(appId);
    }

    @Override
    public Map<String, Object> getAppComment(Integer userId, Integer appId) {
        AppComment queryAppComment = new AppComment();
        queryAppComment.setAppId(appId);
        queryAppComment.setUserId(userId);
        List<Map<String, Object>> datas = appCommentMapper.selectBySelectiveNoPage(queryAppComment);
        if (datas.size() == 1) {
            return datas.get(0);
        }
        return null;
    }

}
