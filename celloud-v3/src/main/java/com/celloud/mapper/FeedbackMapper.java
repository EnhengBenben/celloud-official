package com.celloud.mapper;

import com.celloud.model.Feedback;
import com.celloud.model.FeedbackWithBLOBs;

public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackWithBLOBs record);

    int insertSelective(FeedbackWithBLOBs record);

    FeedbackWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FeedbackWithBLOBs record);

    int updateByPrimaryKey(Feedback record);
}