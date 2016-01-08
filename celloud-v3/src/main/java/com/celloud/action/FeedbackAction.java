package com.celloud.action;

import javax.annotation.Resource;

/**
 * 投诉与建议
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午2:57:50
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.model.Feedback;
import com.celloud.service.FeedbackService;
/**
 * 
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月28日 下午1:35:15
 */
@Controller
@RequestMapping("feedback")
public class FeedbackAction {
    @Resource
    private FeedbackService feedbackService;
    @RequestMapping("list")
    public String getAllFeedBack() {
        return "redirect:/login";
    }

    @RequestMapping(value = "save", method = RequestMethod.PUT)
    @ResponseBody
    public boolean save(Feedback feedback) {
        return feedbackService.inserte(feedback)>=0;
    }
}
