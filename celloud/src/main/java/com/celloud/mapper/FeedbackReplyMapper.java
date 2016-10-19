package com.celloud.mapper;

import java.util.List;

import com.celloud.model.mysql.FeedbackReply;

public interface FeedbackReplyMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(FeedbackReply record);

    public int insertSelective(FeedbackReply record);

    public FeedbackReply selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(FeedbackReply record);

    public int updateByPrimaryKey(FeedbackReply record);

    public List<FeedbackReply> findByFeedbackId(Integer feedbackId);
}