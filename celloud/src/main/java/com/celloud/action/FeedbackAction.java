package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.FeedbackConstants;
import com.celloud.model.mysql.Feedback;
import com.celloud.model.mysql.FeedbackAttachment;
import com.celloud.model.mysql.FeedbackReply;
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
    private static Logger logger = LoggerFactory.getLogger(FeedbackAction.class);
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
        return feedbackService.inserte(feedback, null) >= 0;
    }

    /**
     * 创建新工单(登录后)
     * 
     * @param feedback
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.PUT)
    @ResponseBody
    public Response create(Feedback feedback, String[] attachments) {
        List<String> list = attachments == null || attachments.length <= 0 ? null : Arrays.asList(attachments);
        int result = feedbackService.inserte(feedback, list);
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
        boolean result = feedbackService.insertReply(feedbackId, content);
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

    /**
     * 工单上传附件
     * 
     * @param file
     * @param session
     * @return
     */
    @RequestMapping(value = "attach", method = RequestMethod.POST)
    @ResponseBody
    public String attach(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(FeedbackConstants.getAttachmentTempPath(), new ObjectId().toString() + type);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("工单上传附件失败：{}", fileName, e);
        }
        return targetFile.getName();
    }

    /**
     * 获取工单附件
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "attach", method = RequestMethod.GET)
    public ResponseEntity<byte[]> attach(String file) throws IOException {
        String path = FeedbackConstants.getAttachment(file);
        File targetFile = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), headers, HttpStatus.OK);
    }

    /**
     * 获取工单附件（已上传未保存的临时文件）
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "attach/temp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> attachTemp(String file) throws IOException {
        String path = FeedbackConstants.getAttachmentTempPath() + File.separator + file;
        File targetFile = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), headers, HttpStatus.OK);
    }
}
