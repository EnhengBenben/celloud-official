package com.celloud.backstage.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.constants.FeedbackConstants;
import com.celloud.backstage.model.Feedback;
import com.celloud.backstage.model.FeedbackAttachment;
import com.celloud.backstage.model.FeedbackReply;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.FeedbackService;

@Controller
public class FeedbackAction {
    private static Logger logger = LoggerFactory.getLogger(FeedbackAction.class);
    @Resource
    private FeedbackService feedbackService;

    /**
     * 工单列表
     * 
     * @param page
     * @return
     */
    @RequestMapping("feedback/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,String sortFiled,String sortType) {
        Page page=new Page(currentPage, size);
        PageList<Feedback> feedbacks = feedbackService.findFeedbacks(page,sortFiled,sortType);
        return new ModelAndView("feedback/feedback_list").addObject("pageList", feedbacks).addObject("sortFiled",sortFiled).addObject("sortType",sortType);
    }



    /**
     * 通过id获取工单
     * 
     * @return
     */
    @RequestMapping(value = "feedback/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "feedback/replies/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "feedback/reply/{feedbackId}", method = RequestMethod.PUT)
    @ResponseBody
    public boolean reply(@PathVariable int feedbackId, String content) {
        return feedbackService.insertReply(feedbackId, content);
    }

    /**
     * 关闭问题反馈
     * 
     * @param feedbackId
     * @return
     */
    @RequestMapping(value = "feedback/solve/{feedbackId}", method = RequestMethod.POST)
    @ResponseBody
    public boolean solve(@PathVariable int feedbackId) {
        return feedbackService.solve(feedbackId);
    }


    /**
     * 获取工单附件
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "feedback/attach", method = RequestMethod.GET)
    public ResponseEntity<byte[]> attach(String file,Integer userId) throws IOException {
        String path = FeedbackConstants.getAttachment(file,userId);
        File targetFile = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), headers, HttpStatus.OK);
    }

}
