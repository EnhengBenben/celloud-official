package com.celloud.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮箱接收服务
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月21日 下午1:01:50
 */
public class EmailReceiver {
    private static Logger logger = LoggerFactory.getLogger(EmailReceiver.class);
    private static Session session = null;
    private static final String Protocal = "pop3";
    private static final String FolderName = "INBOX";
    // 与邮件服务器连接后得到的邮箱
    private Store store;
    // 收件箱
    private Folder folder;

    private EmailReceiver() {
    }

    public static EmailReceiver getInstance() {
        if (!EmailProperties.load()) {
            return null;
        }
        EmailReceiver receiver = new EmailReceiver();
        Properties props = new Properties();// 获取系统环境
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailProperties.username, EmailProperties.password);
            }
        };
        // 进行邮件服务用户认证
        props.put("mail.pop3.host", EmailProperties.pop3);
        props.put("mail.pop3.auth", "true");
        session = Session.getDefaultInstance(props, auth);
        if (!receiver.connectToServer()) {
            return null;
        }
        if (!receiver.openInBoxFolder()) {
            return null;
        }
        return receiver;
    }

    public void receive() {
        try {
            receive(1, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MimeMessage[] receive(int start, int end) {
        Message[] messages = null;
        try {
            messages = folder.getMessages(start, end);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return (MimeMessage[]) messages;
    }

    public Message[] recerveNews() {
        try {
            System.out.println(folder.getNewMessageCount());
            System.out.println(folder.getUnreadMessageCount());
            Message[] messages = folder.getMessages(1, folder.getUnreadMessageCount());
            for (Message message : messages) {
                parseMessage(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void receiveFeedback() {

    }

    public void parseMessage(Message message) {
        try {
            System.out.println("message:" + message.getSubject());
            System.out.println("message:" + message.getFrom()[0].toString());
            System.out.println("message:" + message.getContent());
            System.out.println("message:"+message.getSentDate());
            System.out.println("message:"+getMessageId(message));
            Flags flags = message.getFlags();
            System.out.println(flags.contains(Flag.SEEN));
            System.out.println("----------------------------------");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getMessageId(Message message) {
        String id = null;
        try {
            id = ((MimeMessage) message).getMessageID();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getAddress(Address address) {
        InternetAddress internetAddress = (InternetAddress) address;
        String mail = internetAddress.getAddress();
        mail = mail == null ? "" : mail;
        String personal = internetAddress.getPersonal();
        personal = personal == null ? "" : personal;
        return personal + "<" + mail + ">";
    }

    public String getAddress(Message mimeMessage) throws Exception {
        InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
        String fromAddr = "";
        for (InternetAddress addr : address) {
            fromAddr += getAddress(addr) + ",";
        }
        fromAddr = fromAddr.substring(0, fromAddr.lastIndexOf(","));
        return fromAddr;
    }

    /**
     * 登陆邮件服务器
     */
    private boolean connectToServer() {
        // 创建store,建立连接
        try {
            this.store = session.getStore(Protocal);
        } catch (NoSuchProviderException e) {
            logger.error("连接服务器失败！", e);
            return false;
        }
        try {
            this.store.connect();
        } catch (MessagingException e) {
            logger.error("连接服务器失败！", e);
            return false;
        }
        logger.debug("连接服务器成功！");
        return true;
    }

    /**
     * 打开收件箱
     */
    private boolean openInBoxFolder() {
        try {
            this.folder = store.getFolder(FolderName);
            // 只读
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException e) {
            logger.error("打开收件箱失败！", e);
            return false;
        }
        return true;
    }

    /**
     * 断开与邮件服务器的连接
     */
    private boolean closeConnection() {
        try {
            if (this.folder.isOpen()) {
                this.folder.close(true);
            }
            this.store.close();
            logger.debug("成功关闭与邮件服务器的连接！");
        } catch (Exception e) {
            logger.error("关闭和邮件服务器之间连接时出错！", e);
            return false;
        }
        return true;
    }
}
