package com.celloud.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.constants.ConstantsData;
import com.celloud.backstage.constants.FeedbackConstants;
import com.celloud.backstage.mapper.FeedbackAttachmentMapper;
import com.celloud.backstage.mapper.FeedbackMapper;
import com.celloud.backstage.mapper.FeedbackReplyMapper;
import com.celloud.backstage.model.Feedback;
import com.celloud.backstage.model.FeedbackAttachment;
import com.celloud.backstage.model.FeedbackReply;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.FeedbackService;
import com.celloud.mail.EmailUtils;


/**
 * 
 *
 * @author han
 * @date 2016年2月29日 下午5:26:14
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private FeedbackAttachmentMapper attachmentMapper;
    @Resource
    private FeedbackReplyMapper replyMapper;


    @Override
    public PageList<Feedback> findFeedbacks(Page page,String sortFiled,String sortType) {
        List<Feedback> list = feedbackMapper.selectByPage(page, sortFiled, sortType);
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
    public boolean insertReply(int feedbackId, String content) {
        Feedback fb=feedbackMapper.selectByPrimaryKey(feedbackId);
        FeedbackReply reply = new FeedbackReply();
        reply.setContent(content);
        reply.setFeedbackId(feedbackId);
        reply.setCreateTime(new Date());
        reply.setEmail(ConstantsData.getLoginEmail()==null?"":ConstantsData.getLoginEmail());
        reply.setUserId(ConstantsData.getLoginUserId());
        reply.setUserName(ConstantsData.getLoginUserName());
        int result=replyMapper.insertSelective(reply);
        if(result>0&&fb.getEmail()!=null){
            EmailUtils.sendFeedbackReply(fb, reply);
        }
        return  result> 0;
    }


    @Override
    public boolean solve(int feedbackId) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackId);
        feedback.setSolve(FeedbackConstants.SOLVED.byteValue());
        return feedbackMapper.updateByPrimaryKeySelective(feedback) > 0;
    }

}
