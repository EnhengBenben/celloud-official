package com.celloud.backstage.service;

import java.util.List;

import com.celloud.backstage.model.Feedback;
import com.celloud.backstage.model.FeedbackAttachment;
import com.celloud.backstage.model.FeedbackReply;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;


/**
 * 工单接口
 *
 * @author han
 * @date 2016年2月29日 下午5:24:44
 */

public interface FeedbackService {


    /**
     * 获取问题反馈列表（工单）
     * 
     * @param page
     * @return
     */
    public PageList<Feedback> findFeedbacks(Page page,String sortFiled,String sortType);

    /**
     * 根据id获取问题反馈(工单)
     * 
     * @param id
     * @return
     */
    public Feedback selectFeedbackById(Integer id);

    /**
     * 查询工单附件
     * 
     * @param feedbackId
     * @return
     */
    public List<FeedbackAttachment> findAttachments(Integer feedbackId);

    /**
     * 查询工单回复
     * 
     * @param feedbackId
     * @return
     */
    public List<FeedbackReply> findReplies(Integer feedbackId);

    /**
     * 回复工单
     * 
     * @param feedbackId
     * @param content
     * @return
     */
    public boolean insertReply(int feedbackId, String content);

    /**
     * 关闭工单
     * 
     * @param feedbackId
     * @return
     */
    public boolean solve(int feedbackId);

}
