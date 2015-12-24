package com.celloud.service;

import com.celloud.model.Feedback;

/**
 * 投诉与建议等service接口
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月24日 上午10:03:19
 */

public interface FeedbackService {
    /**
     * 保存一个建议
     * @param feedback
     * @return
     */
    public int inserte(Feedback feedback);
}
