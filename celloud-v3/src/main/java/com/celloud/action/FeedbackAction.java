package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

/**
 * 投诉与建议
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午2:57:50
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.model.Feedback;
import com.celloud.model.FeedbackAttachment;
import com.celloud.model.FeedbackReply;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.FeedbackService;
/**
 * 
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月28日 下午1:35:15
 */
import com.celloud.utils.Response;

@Controller
@RequestMapping("feedback")
public class FeedbackAction {
    @Resource
    private FeedbackService feedbackService;

    /**
     * 工单菜单
     * 
     * @return
     */
    @RequestMapping("main")
    public ModelAndView index() {
        return new ModelAndView("feedback/feedback_main");
    }

    /**
     * 工单列表
     * 
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(Page page) {
        PageList<Feedback> feedbacks = feedbackService.findFeedbacks(page);
        return new ModelAndView("feedback/feedback_list").addObject("feedbackList", feedbacks);
    }

    /**
     * 保存一个问题反馈（未登录）
     * 
     * @param feedback
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.PUT)
    @ResponseBody
    public boolean save(Feedback feedback) {
        return feedbackService.inserte(feedback) >= 0;
    }

    /**
     * 创建新工单(登录后)
     * 
     * @param feedback
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.PUT)
    @ResponseBody
    public Response create(Feedback feedback) {
        int result = feedbackService.inserte(feedback);
        if (result > 0) {
            return Response.SAVE_SUCCESS;
        }
        return Response.FAIL;
    }

    /**
     * 通过id获取工单
     * 
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable int id) {
        Feedback feedback = feedbackService.selectFeedbackById(id);
        List<FeedbackAttachment> attachments = null;
        if (feedback.hasAttachment()) {
            attachments = feedbackService.findAttachments(feedback.getId());
        }
        return new ModelAndView("feedback/feedback_detail").addObject("feedback", feedback).addObject("attachments",
                attachments);
    }

    /**
     * 通过id获取工单回复
     * 
     * @return
     */
    @RequestMapping(value = "replies/{id}", method = RequestMethod.GET)
    public ModelAndView listReplies(@PathVariable int id) {
        List<FeedbackReply> replies = feedbackService.findReplies(id);
        return new ModelAndView("feedback/feedback_replies").addObject("replies", replies);
    }

    /**
     * 回复工单
     * 
     * @param feedbackId
     * @param content
     * @return
     */
    @RequestMapping(value = "reply/{feedbackId}", method = RequestMethod.PUT)
    @ResponseBody
    public Response reply(@PathVariable int feedbackId, String content) {
        boolean result = feedbackService.reply(feedbackId, content);
        return result ? Response.SUCCESS.setData(feedbackId) : Response.FAIL;
    }

    /**
     * 关闭问题反馈
     * 
     * @param feedbackId
     * @return
     */
    @RequestMapping(value = "solve/{feedbackId}", method = RequestMethod.POST)
    @ResponseBody
    public Response solve(@PathVariable int feedbackId) {
        boolean result = feedbackService.solve(feedbackId);
        return result ? Response.SUCCESS.setData(feedbackId) : Response.FAIL;
    }
}
