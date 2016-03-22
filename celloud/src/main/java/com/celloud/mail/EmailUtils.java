package com.celloud.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
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
    @Resource
    private EmailService emailService;
    private String errorMailTo;
    private String[] errorMailTos;
    private String errorTitle;
    private String feedbackMailTo;
    private String[] feedbackMailTos;
    private String feedbackTitle;

    public void sendWithTitle(String title, String content, String... to) {
        Email email = new Email().addTo(to).setSubject(title).setContent(content);
        emailService.send(email);
    }

    public void send(String content, String... to) {
        Email email = new Email().addTo(to).setContent(content);
        emailService.send(email);
    }

    /**
     * 将错误日志组织成邮件正文并发送
     * 
     * @param request
     * @param exception
     */
    public void sendError(HttpServletRequest request, Exception exception) {
        if (this.errorMailTos == null) {
            logger.warn("系统出现异常，正在发送异常信息邮件，但是没有找到邮件接收者！");
            return;
        }
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
        // TODO 使用volecity模板渲染邮件内容
        String content = buffer.toString();
        pw.close();
        Email email = new Email().addTo(this.errorMailTos).setSubject(errorTitle).setContent(content);
        emailService.send(email);
    }

    /**
     * 将送问题反馈组织成邮件正文并发送
     * 
     * @param feedback
     */
    public void sendFeedback(Feedback feedback, List<FeedbackAttachment> attachments) {
        if (this.feedbackMailTos == null) {
            logger.warn("用户提交了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
            return;
        }
        Email email = new Email().addModel("feedback", feedback).addTo(this.feedbackMailTos).setSubject(feedbackTitle)
                .setTemplate("feedback.vm");
        if (attachments != null && attachments.size() > 0) {
            for (FeedbackAttachment fa : attachments) {
                String file = FeedbackConstants.getAttachment(fa.getFilePath());
                email.attach(file, null);
            }
        }
        emailService.send(email);
    }

    public String getErrorMailTo() {
        return errorMailTo;
    }

    public void setErrorMailTo(String errorMailTo) {
        this.errorMailTo = errorMailTo;
        this.errorMailTos = this.getMails(this.errorMailTo);
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getFeedbackMailTo() {
        return feedbackMailTo;
    }

    public void setFeedbackMailTo(String feedbackMailTo) {
        this.feedbackMailTo = feedbackMailTo;
        this.feedbackMailTos = this.getMails(this.feedbackMailTo);
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    private String[] getMails(String mails) {
        String[] result = null;
        if (mails != null && !mails.trim().equals("")) {
            result = mails.split(",");
            List<String> list = new ArrayList<>(new HashSet<>(Arrays.asList(result)));
            result = list.toArray(new String[list.size()]);
        }
        return result;
    }
}
