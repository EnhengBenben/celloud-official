package com.celloud.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.backstage.model.Feedback;
import com.celloud.backstage.model.FeedbackReply;

/**
 * 
 *
 * @author han
 * @date 2016年3月2日 上午10:29:40
 */
public class EmailUtils {
    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);
    /**
     * 将送问题反馈组织成邮件正文并发送
     * 
     * @param feedback
     */
    public static void sendFeedbackReply(final Feedback feedback,final FeedbackReply reply) {
        final EmailSender sender = EmailSender.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (feedback.getEmail() == null) {
                    logger.warn("管理员回复了工单，正在发送工单信息邮件，但是没有找到邮件接收者！");
                    return;
                }
                sender.addTo(feedback.getEmail()).setTitle(EmailProperties.feedbackTitle)
                        .setTemplate("feedbackReply.vm").addObject("feedback", feedback).addObject("reply", reply).send();
            }
        }).start();
    }
}
