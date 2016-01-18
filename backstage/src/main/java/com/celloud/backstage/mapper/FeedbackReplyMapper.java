package com.celloud.backstage.mapper;

import com.celloud.backstage.model.FeedbackReply;

public interface FeedbackReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackReply record);

    int insertSelective(FeedbackReply record);

    FeedbackReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackReply record);

    int updateByPrimaryKeyWithBLOBs(FeedbackReply record);

    int updateByPrimaryKey(FeedbackReply record);
}