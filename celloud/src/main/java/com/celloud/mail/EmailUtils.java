package com.celloud.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.FeedbackConstants;
import com.celloud.model.mysql.Feedback;
import com.celloud.model.mysql.FeedbackAttachment;
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
    public static void sendError(final String errorMessage) {
        final EmailSender sender = EmailSender.getInstance();
        if (EmailProperties.errorMailTo == null) {
            logger.warn("系统出现异常，正在发送异常信息邮件，但是没有找到邮件接收者！");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                sender.addTo(EmailProperties.errorMailTo).setTitle(EmailProperties.errorTitle).setContent(errorMessage)
                        .send();
            }
        }).start();

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
        buffer.append(request.getMethod() + "   ");
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
    public static void sendFeedback(final String content) {
        final EmailSender sender = EmailSender.getInstance();
        if (EmailProperties.feedbackMailTo == null) {
            logger.warn("正在发送用户问题反馈邮件，但是没有找到邮件接收者！");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                sender.addTo(EmailProperties.feedbackMailTo).setTitle(EmailProperties.feedbackTitle).setContent(content)
                        .send();
            }
        }).start();

    }

    /**
     * 将送问题反馈组织成邮件正文并发送
     * 
     * @param feedback
     */
    public static void sendFeedback(final Feedback feedback, final List<FeedbackAttachment> attachments) {
        final EmailSender sender = EmailSender.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (EmailProperties.feedbackMailTo == null) {
                    logger.warn("用户提交了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
                    return;
                }
                if (attachments != null && attachments.size() > 0) {
                    for (FeedbackAttachment fa : attachments) {
                        String file = FeedbackConstants.getAttachment(fa.getFilePath());
                        // TODO fa.getFileName() 用户上传的文件名
                        sender.attachWithFileName(null, file);
                    }
                }
                sender.addTo(EmailProperties.feedbackMailTo).setTitle(EmailProperties.feedbackTitle)
                        .setTemplate("feedback.vm").addObject("feedback", feedback).send();
            }
        }).start();
    }
}
