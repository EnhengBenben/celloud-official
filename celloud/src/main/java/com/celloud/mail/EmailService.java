package com.celloud.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import com.celloud.utils.VelocityUtil;

public class EmailService {
    private static Logger logger = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender mailSender;// spring配置中定义
    private VelocityUtil velocityUtil;// spring配置中定义
    private String defaultFrom;
    private String defaultTitle;
    private String emailName;

    @Async
    public void send(Email email) {
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(defaultFrom, emailName);
            if (email.getTo() == null || email.getTo().isEmpty()) {
                return;
            }
            if (email.getTemplate() == null && email.getContent() == null) {
                return;
            }
            for (String mail : email.getTo()) {
                messageHelper.addTo(mail);
            }
            if (email.getCc() != null) {
                for (String mail : email.getCc()) {
                    messageHelper.addCc(mail);
                }
            }
            if (email.getBcc() != null) {
                for (String mail : email.getBcc()) {
                    messageHelper.addBcc(mail);
                }
            }
            if (email.getAttachments() != null) {
                for (String attach : email.getAttachments().keySet()) {
                    File file = new File(attach);
                    String filename = email.getAttachments().get(attach);
                    if (filename == null) {
                        filename = file.getName();
                    }
                    messageHelper.addAttachment(filename, new File(attach));
                }
            }
            messageHelper.setSubject(email.getSubject());
            String content = email.getContent();
            if (content == null) {
                content = velocityUtil.mergeMailTemplate(email.getTemplate(), email.getContext());
            }
            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            logger.error("发送邮件失败！", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("设置发信地址失败：defaultFrom={},emailName={}", defaultFrom, emailName, e);
        }
        mailSender.send(mimeMessage);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public VelocityUtil getVelocityUtil() {
        return velocityUtil;
    }

    public void setVelocityUtil(VelocityUtil velocityUtil) {
        this.velocityUtil = velocityUtil;
    }

    public String getDefaultFrom() {
        return defaultFrom;
    }

    public void setDefaultFrom(String defaultFrom) {
        this.defaultFrom = defaultFrom;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

}
