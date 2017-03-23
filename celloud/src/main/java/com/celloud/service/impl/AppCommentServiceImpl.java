package com.celloud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

}
