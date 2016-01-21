package com.celloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.celloud.utils.EmailUtils;

/**
 * 投诉与建议service实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:04:23
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {
    private Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    private static final Page DEFAULT_PAGE = new Page(1, 5);
    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private FeedbackAttachmentMapper attachmentMapper;
    @Resource
    private FeedbackReplyMapper replyMapper;

    @Override
    public int inserte(Feedback feedback, List<String> attachments) {
        User user = ConstantsData.getLoginUser();
        feedback.setCreateDate(new Date());
        feedback.setHasAttachment(attachments != null && attachments.size() > 0
                ? FeedbackConstants.HASATTACHMENT.byteValue() : FeedbackConstants.NOT_HAS_ATTACHMENT.byteValue());
        feedback.setUserId(user.getUserId());
        feedback.setUsername(user.getUsername());
        feedback.setEmail(user.getEmail());
        int result = feedbackMapper.insertSelective(feedback);
        List<FeedbackAttachment> feedbackAttachments = new ArrayList<>();
        if (attachments != null) {
            for (String name : attachments) {
                FeedbackAttachment attachment = new FeedbackAttachment();
                attachment.setFeedbackId(feedback.getId());
                attachment.setFilePath(name);
                attachment.setFileType(name.substring(name.lastIndexOf(".") + 1));
                try {
                    FileUtils.moveFile(new File(FeedbackConstants.getAttachmentTempPath() + File.separator + name),
                            new File(FeedbackConstants.getAttachment(name)));
                    feedbackAttachments.add(attachment);
                } catch (IOException e) {
                    logger.error("复制附件失败：{}", name, e);
                }
            }
        }
        if (feedbackAttachments.size() > 0) {
            result = attachmentMapper.insertbatch(feedbackAttachments);
        }
        feedbackMapper.updateAttachState();
        if (feedbackAttachments.size() > 0 && result <= 0) {
            feedbackMapper.updateAttachState();
        }
        EmailUtils.sendFeedback(feedback, feedbackAttachments);
        cleanAttachment();
        return result;
    }

    /**
     * 清理工单附件的临时目录
     */
    private void cleanAttachment() {
        String path = FeedbackConstants.getAttachmentTempPath();
        File tempDir = new File(path);
        if (tempDir == null || !tempDir.exists()) {
            return;
        }
        if (tempDir.isFile()) {
            tempDir.delete();
            return;
        }
        File[] tempFiles = tempDir.listFiles();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date date = calendar.getTime();
        for (File file : tempFiles) {
            ObjectId id = new ObjectId(file.getName().substring(0, file.getName().indexOf(".")));
            if (date.after(id.getDate())) {
                file.delete();
            }
        }
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
    public boolean insertReply(int feedbackId, String content) {
        FeedbackReply reply = new FeedbackReply();
        reply.setContent(content);
        reply.setFeedbackId(feedbackId);
        reply.setCreateTime(new Date());
        reply.setEmail(ConstantsData.getLoginEmail());
        reply.setUserId(ConstantsData.getLoginUserId());
        reply.setUserName(ConstantsData.getLoginUserName());
        return replyMapper.insertSelective(reply) > 0;
    }

    public boolean updateAttachState() {
        return feedbackMapper.updateAttachState() > 0;
    }

    @Override
    public boolean solve(int feedbackId) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackId);
        feedback.setSolve(FeedbackConstants.SOLVED.byteValue());
        return feedbackMapper.updateByPrimaryKeySelective(feedback) > 0;
    }

}
