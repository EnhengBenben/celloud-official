package com.celloud.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h4>发送邮件工具类</h4>
 * <h4>使用方式：</h4>
 * <h5>1、静态方式使用：</h5> <code>
 * EmailUtils.send(content,emails);//email为邮件列表，String类型不定参
 * EmailUtils.sendWidthTitle(title,content,emails)//email为邮件列表，String类型不定参
 * </code>
 * <h5>2、非静态方式使用：</h5> <code>
 * EmailUtils utils = EmailUtils.getInstance();<br>
 * utils.addTo(emails);//接收人列表，string类型不定参<br>
 * utils.addCc(emails);//抄送人列表，string类型不定参<br>
 * utils.addBcc(emails);//密抄人列表，string类型不定参<br>
 * utils.setTitle(title);//邮件标题<br>
 * utils.setContent(content);//邮件内容，支持html格式<br>
 * utils.setSentDate(date);//发送时间，定时发送<br>
 * utils.attach(files);//添加附件,附件路径列表，string类型不定参<br>
 * utils.send();//发送<br><br>
 *  //或者<br>
 * EmailUtils.getInstance().<br>
 *              addTo(emails).<br>
 *              addCc(emails).<br>
 *              addBcc(emails).<br>
 *              setTitle(title).<br>
 *              setContent(content).<br>
 *              attach(files).<br>
 *              setSentDate(date).<br>
 *              send();<br>
 * </code>
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月5日 下午1:30:21
 */
public class EmailSender {
    private static Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private static InternetAddress from;
    private Date sentDate;
    private Multipart multipart = new MimeMultipart();
    private static Session session = null;
    private MimeMessage message = null;

    private EmailSender() {
    }

    /**
     * 初始化实例
     * 
     * @return
     */
    public static EmailSender getInstance() {
        if (!EmailProperties.load()) {
            return null;
        }
        Properties props = new Properties();// 获取系统环境
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailProperties.username, EmailProperties.password);
            }
        };
        // 进行邮件服务用户认证
        props.put("mail.smtp.host", EmailProperties.smtp);
        props.put("mail.smtp.auth", "true");
        // 设置session,和邮件服务器进行通讯
        session = Session.getDefaultInstance(props, auth);
        EmailSender sender = new EmailSender();
        sender.message = new MimeMessage(session);
        try {
            if (from == null) {
                from = new InternetAddress(EmailProperties.username);
            }
            if (EmailProperties.emailName != null) {
                from.setPersonal(MimeUtility.encodeText(EmailProperties.emailName));
            }
            sender.message.setFrom(from);
        } catch (Exception e) {
            logger.error("设置发信地址失败：username={},emailName={}", EmailProperties.username, EmailProperties.emailName, e);
        }
        if (EmailProperties.defaultTitle != null) {
            sender.setTitle(EmailProperties.defaultTitle);
        }
        return sender;
    }

    /**
     * 发送邮件，使用默认主题，可发送多人
     * 
     * @param content
     * @param emails
     */
    public static void send(String content, String... emails) {
        getInstance().addTo(emails).setContent(content).send();
    }

    /**
     * 发送邮件，使用自定义主题，可发送多人
     * 
     * @param title
     * @param content
     * @param emails
     */
    public static void sendWithTitle(String title, String content, String... emails) {
        getInstance().addTo(emails).setTitle(title).setContent(content).send();
    }

    /**
     * 添加接收人
     * 
     * @param emails
     * @return
     */
    public EmailSender addTo(String... emails) {
        for (String email : emails) {
            try {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            } catch (MessagingException e) {
                logger.error("添加接收人失败：{}", email, e);
            }
        }
        return this;
    }

    /**
     * 添加抄送人
     * 
     * @param emails
     * @return
     */
    public EmailSender addCc(String... emails) {
        for (String email : emails) {
            try {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
            } catch (MessagingException e) {
                logger.error("添加抄送人失败：{}", email, e);
            }
        }
        return this;
    }

    /**
     * 添加密抄人
     * 
     * @param emails
     * @return
     */
    public EmailSender addBcc(String... emails) {
        for (String email : emails) {
            try {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(email));
            } catch (MessagingException e) {
                logger.error("添加密抄人失败：{}", email, e);
            }
        }
        return this;
    }

    /**
     * 设置邮件主题
     * 
     * @param title
     * @return
     */
    public EmailSender setTitle(String title) {
        try {
            message.setSubject(title);
        } catch (MessagingException e) {
            logger.error("设置主题失败：{}", title, e);
        }
        return this;
    }

    /**
     * 设置邮件内容
     * 
     * @param content
     * @return
     */
    public EmailSender setContent(String content) {
        try {
            // 构建一个消息内容块
            BodyPart mbpFile = new MimeBodyPart();
            mbpFile.setContent(content, "text/html;charset=UTF-8");
            multipart.addBodyPart(mbpFile);
        } catch (MessagingException e) {
            logger.error("设置邮件内容失败：content={}", content, e);
        }
        return this;
    }

    /**
     * 发送邮件
     */
    public void send() {
        try {
            message.setContent(multipart);
            message.saveChanges();
            message.setSentDate(sentDate);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("邮件发送失败！", e);
        }

    }

    /**
     * 添加附件
     * 
     * @param files
     * @return
     */
    public EmailSender attach(String... files) {
        for (String file : files) {
            attachWithFileName(null, file);
        }
        return this;
    }

    /**
     * 添加附件，自定义附件名称
     * 
     * @param name
     * @param file
     * @return
     */
    public EmailSender attachWithFileName(String name, String file) {
        MimeBodyPart fattach = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(file);
        try {
            fattach.setDataHandler(new DataHandler(fds));
            if (name == null || name.equals("")) {
                name = fds.getName();
            }
            fattach.setFileName(MimeUtility.encodeWord(name, "GB2312", null));
            multipart.addBodyPart(fattach);
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("添加附件失败：{}", file, e);
        }
        return this;
    }

    /**
     * 设置发送时间
     * 
     * @param date
     * @return
     */
    public EmailSender setSentDate(Date date) {
        this.sentDate = date;
        return this;
    }
}
