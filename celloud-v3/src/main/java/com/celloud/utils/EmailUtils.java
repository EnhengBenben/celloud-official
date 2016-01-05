package com.celloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

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
 * <h5>1、静态方式使用：</h5> 
 * <code>
 * EmailUtils.send(content,emails);//email为邮件列表，String类型不定参
 * EmailUtils.sendWidthTitle(title,content,emails)//email为邮件列表，String类型不定参
 * </code>
 * <h5>2、非静态方式使用：</h5> 
 * <code>
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
public class EmailUtils {
    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);
    private static final String PROPERTY_PATH = "email.properties";
    private static String username;
    private static String password;
    private static String smtp;
    private static String defaultTitle;
    private static String emailName;
    private static boolean isInit = false;
    private static InternetAddress from;
    private Date sentDate;
    private Multipart multipart = new MimeMultipart();

    private static Session session = null;
    private MimeMessage message = null;

    private EmailUtils() {
    }

    /**
     * 加载配置文件
     * 
     * @return
     */
    private static boolean loadProperty() {
        Properties pro = new Properties();
        String path = EmailUtils.class.getClassLoader().getResource("") + PROPERTY_PATH;
        path = path.replace("file:", "");
        File ef = new File(path);
        if (!ef.exists()) {
            logger.warn("email配置文件不存在：{}" + PROPERTY_PATH);
            return false;
        }
        try {
            // 获取jar包外的资源文件
            InputStream is = new FileInputStream(ef);
            pro.load(is);
        } catch (IOException e) {
            logger.error("获取email.properties文件信息失败", e);
            return false;
        }
        // 增加转码处理，当properties中有中文时也可以正常处理
        Set<Object> keyset = pro.keySet();
        Iterator<Object> iter = keyset.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String newValue = null;
            try {
                // 属性配置文件自身的编码
                String propertiesFileEncode = "utf-8";
                newValue = new String(pro.getProperty(key).getBytes("ISO-8859-1"), propertiesFileEncode);
            } catch (UnsupportedEncodingException e) {
                newValue = pro.getProperty(key);
            }
            pro.setProperty(key, newValue);
        }
        username = pro.getProperty("username");
        password = pro.getProperty("password");
        smtp = pro.getProperty("smtp");
        defaultTitle = pro.getProperty("defaultTitle");
        emailName = pro.getProperty("emailName");
        Properties props = new Properties();// 获取系统环境
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };//// 进行邮件服务用户认证
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.auth", "true");
        // 设置session,和邮件服务器进行通讯
        session = Session.getDefaultInstance(props, auth);
        isInit = true;
        return true;
    }

    /**
     * 初始化实例
     * 
     * @return
     */
    public static EmailUtils getInstance() {
        if (!isInit && !loadProperty()) {
            return null;
        }
        EmailUtils utils = new EmailUtils();
        utils.message = new MimeMessage(session);
        try {
            if (from == null) {
                from = new InternetAddress(username);
            }
            if (emailName != null) {
                from.setPersonal(MimeUtility.encodeText(emailName));
            }
            utils.message.setFrom(from);
        } catch (Exception e) {
            logger.error("设置发信地址失败：username={},emailName={}", username, emailName, e);
        }
        if (defaultTitle != null) {
            utils.setTitle(defaultTitle);
        }
        return utils;
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
    public EmailUtils addTo(String... emails) {
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
    public EmailUtils addCc(String... emails) {
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
    public EmailUtils addBcc(String... emails) {
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
    public EmailUtils setTitle(String title) {
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
    public EmailUtils setContent(String content) {
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
    public EmailUtils attach(String... files) {
        for (String file : files) {
            MimeBodyPart fattach = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(file);
            try {
                fattach.setDataHandler(new DataHandler(fds));
                fattach.setFileName(MimeUtility.encodeWord(fds.getName(), "GB2312", null));
                multipart.addBodyPart(fattach);
            } catch (MessagingException | UnsupportedEncodingException e) {
                logger.error("添加附件失败：{}", file, e);
            }

        }
        return this;
    }

    /**
     * 设置发送时间
     * 
     * @param date
     * @return
     */
    public EmailUtils setSentDate(Date date) {
        this.sentDate = date;
        return this;
    }
}
