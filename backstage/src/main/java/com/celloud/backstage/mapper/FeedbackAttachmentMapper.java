package com.celloud.backstage.mapper;

import com.celloud.backstage.model.FeedbackAttachment;

public interface FeedbackAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackAttachment record);

    int insertSelective(FeedbackAttachment record);

    FeedbackAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackAttachment record);

    int updateByPrimaryKey(FeedbackAttachment record);
}