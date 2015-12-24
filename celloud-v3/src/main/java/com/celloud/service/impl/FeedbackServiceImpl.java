package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.FeedbackMapper;
import com.celloud.model.Feedback;
import com.celloud.service.FeedbackService;
/**
 * 投诉与建议service实现类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:04:23
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;
    @Override
    public int inserte(Feedback feedback) {
        return feedbackMapper.insertSelective(feedback);
    }

}
