package com.celloud.backstage.mapper;

import java.util.List;

import com.celloud.backstage.model.FeedbackAttachment;

public interface FeedbackAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackAttachment record);

    int insertSelective(FeedbackAttachment record);

    FeedbackAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackAttachment record);

    int updateByPrimaryKey(FeedbackAttachment record);
    
    public List<FeedbackAttachment> findByFeedbackId(Integer feedbackId);
}