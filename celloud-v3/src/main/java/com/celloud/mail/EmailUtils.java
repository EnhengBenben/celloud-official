package com.celloud.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.FeedbackConstants;
import com.celloud.model.Feedback;
import com.celloud.model.FeedbackAttachment;
import com.celloud.utils.UserAgentUtil;

/**
 * 邮箱工具类
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月21日 下午1:32:23
 */
public class EmailUtils {
    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    /**
     * 发送已组织成邮件正文的错误日志
     * 
     * @param errorMessage
     */
    public static void sendError(String errorMessage) {
        EmailSender sender = EmailSender.getInstance();
        if (EmailProperties.errorsMailTo == null) {
            logger.warn("系统出现异常，正在发送异常信息邮件，但是没有找到邮件接收者！");
            return;
        }
        sender.addTo(EmailProperties.errorsMailTo).setTitle(EmailProperties.errorTitle).setContent(errorMessage).send();
    }

    /**
     * 将错误日志组织成邮件正文并发送
     * 
     * @param request
     * @param exception
     */
    public static void sendError(HttpServletRequest request, Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        StringBuffer buffer = new StringBuffer("");
        buffer.append("<h3>异常地址：</h3>");
        buffer.append(request.getRequestURL());
        if (request.getQueryString() != null) {
            buffer.append("?" + request.getQueryString());
        }
        buffer.append("<h3>用户信息：</h3>");
        buffer.append(UserAgentUtil.getActionLog(request).toResume());
        buffer.append("<h3>异常信息：</h3>");
        buffer.append(exception.toString());
        buffer.append("<h3>异常描述：</h3>");
        buffer.append("<pre>");
        buffer.append(sw.toString());
        buffer.append("</pre>");
        String content = buffer.toString();
        pw.close();
        sendError(content);
    }

    /**
     * 发送已组织成邮件正文的问题反馈
     * 
     * @param content
     */
    public static void sendFeedback(String content) {
        EmailSender utils = EmailSender.getInstance();
        if (EmailProperties.feedbackMailTo == null) {
            logger.warn("正在发送用户问题反馈邮件，但是没有找到邮件接收者！");
            return;
        }
        utils.addTo(EmailProperties.feedbackMailTo).setTitle(EmailProperties.feedbackTitle).setContent(content).send();
    }

    /**
     * 将送问题反馈组织成邮件正文并发送
     * 
     * @param feedback
     */
    public static void sendFeedback(Feedback feedback, List<FeedbackAttachment> attachments) {
        EmailSender utils = EmailSender.getInstance();
        if (EmailProperties.feedbackMailTo == null) {
            logger.warn("用户提交了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
            return;
        }
        StringBuffer buffer = new StringBuffer("");
        buffer.append("<strong>标题：</strong>" + feedback.getTitle());
        buffer.append("<br>");
        buffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(feedback.getCreateDate()));
        buffer.append("&nbsp;&nbsp;&nbsp;    &nbsp;&nbsp;&nbsp;");
        buffer.append(feedback.getUsername());
        buffer.append("(" + feedback.getEmail() + ")");
        buffer.append("<br>");
        buffer.append("<strong>内容：</strong>" + feedback.getContent());
        if (attachments != null && attachments.size() > 0) {
            for (FeedbackAttachment fa : attachments) {
                String file = FeedbackConstants.getAttachment(fa.getFilePath());
                utils.attach(file);
            }
        }
        utils.addTo(EmailProperties.feedbackMailTo).setTitle(EmailProperties.feedbackTitle)
                .setContent(buffer.toString()).send();
    }
}
