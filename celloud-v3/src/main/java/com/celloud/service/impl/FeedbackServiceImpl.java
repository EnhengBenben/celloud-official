package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.FeedbackConstants;
import com.celloud.mapper.FeedbackAttachmentMapper;
import com.celloud.mapper.FeedbackMapper;
import com.celloud.mapper.FeedbackReplyMapper;
import com.celloud.model.Feedback;
import com.celloud.model.FeedbackAttachment;
import com.celloud.model.FeedbackReply;
import com.celloud.model.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.FeedbackService;

/**
 * 投诉与建议service实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:04:23
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {
    private static final Page DEFAULT_PAGE = new Page(1, 5);
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private FeedbackAttachmentMapper attachmentMapper;
    @Resource
    private FeedbackReplyMapper replyMapper;

    @Override
    public int inserte(Feedback feedback) {
        User user = ConstantsData.getLoginUser();
        feedback.setCreateDate(new Date());
        feedback.setUserId(user.getUserId());
        feedback.setUsername(user.getUsername());
        feedback.setEmail(user.getEmail());
        return feedbackMapper.insertSelective(feedback);
    }

    @Override
    public PageList<Feedback> findFeedbacks(Page page) {
        page = page == null ? DEFAULT_PAGE : page;
        page.setPageSize(5);
        List<Feedback> list = feedbackMapper.selectByUserId(ConstantsData.getLoginUserId(), page);
        return new PageList<>(page, list);
    }

    @Override
    public Feedback selectFeedbackById(Integer id) {
        return feedbackMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FeedbackAttachment> findAttachments(Integer feedbackId) {
        return attachmentMapper.findByFeedbackId(feedbackId);
    }

    @Override
    public List<FeedbackReply> findReplies(Integer feedbackId) {
        return replyMapper.findByFeedbackId(feedbackId);
    }

    @Override
    public boolean reply(int feedbackId, String content) {
        FeedbackReply reply = new FeedbackReply();
        reply.setContent(content);
        reply.setFeedbackId(feedbackId);
        reply.setCreateTime(new Date());
        reply.setEmail(ConstantsData.getLoginEmail());
        reply.setUserId(ConstantsData.getLoginUserId());
        reply.setUserName(ConstantsData.getLoginUserName());
        return replyMapper.insertSelective(reply) > 0;
    }

    @Override
    public boolean solve(int feedbackId) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackId);
        feedback.setSolve(FeedbackConstants.SOLVED.byteValue());
        return feedbackMapper.updateByPrimaryKeySelective(feedback) > 0;
    }

}
