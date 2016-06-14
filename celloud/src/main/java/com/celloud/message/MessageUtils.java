package com.celloud.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.constants.Constants;
import com.celloud.exception.BusinessException;
import com.celloud.model.mysql.Notice;
import com.celloud.service.NoticeService;
import com.celloud.utils.SpringTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * websocket消息发送工具类<br>
 * <br>
 * 在调用.to()或者.toAll()方法时才会真正进行发送 <br>
 * <br>
 * MessageUtils.get().on(XXXX).send(XXXX).to(XXXX,XXXX,...);<br>
 * 在XXXX频道，发送XXXX数据给用户XXXX,XXXX和... <br>
 * <br>
 * MessageUtils.get().on(XXXX).send(XXXX).toAll();<br>
 * 在XXXX频道，发送XXXX数据给所有用户
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年4月14日 下午2:58:15
 */
@Component
public class MessageUtils {
    @Autowired
    private NoticeService noticeService;
    private Message message = new Message();
    private Notice notice;

    /**
     * 获取工具类实体
     * 
     * @return
     */
    public static MessageUtils get() {
        MessageUtils utils = (MessageUtils) SpringTool.getBean("messageUtils");
        return utils;
    }

    /**
     * 发送的数据,任意实体类，自动转换为json格式，方便前台接收
     * 
     * @param message
     * @return
     */
    public MessageUtils send(Notice notice) {
        this.message.setMessage(notice);
        this.notice = notice;
        return this;
    }

    /**
     * 发送给指定用户
     * 
     * @param usernames
     */
    @Async
    public void to(String... usernames) {
        if (usernames == null || usernames.length <= 0) {
            throw new BusinessException("发送消息没有指定接收者:" + message.toJson());
        }
        noticeService.insertNotice(this.notice, usernames);
        final Map<String, String> messages = new HashMap<>();
        String jsonMessage = this.message.toJson();
        for (String username : usernames) {
            messages.put(username, jsonMessage);
        }
        MessageSender.send(Constants.MESSAGE_USER_TOPIC, messages);
    }

    /**
     * 发送给所有用户
     */
    @Async
    public void toAll() {
        final Map<String, String> messages = new HashMap<>();
        messages.put(Constants.MESSAGE_ALLUSER_KEY, this.message.toJson());
        MessageSender.send(Constants.MESSAGE_USER_TOPIC, messages);
    }

    /**
     * 发送的频道
     * 
     * @param channel
     * @return
     */
    public MessageUtils on(String channel) {
        this.message.setChannel(channel);
        return this;
    }

    /**
     * 消息实体类，将channel和数据进行封装，并一起转换成json格式数据
     * 
     * @author <a href="sunwendong@celloud.cn">sun8wd</a>
     * @date 2016年4月14日 下午3:03:09
     */
    class Message {
        private String channel;
        private Object message;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public String toJson() {
            ObjectMapper mapper = new ObjectMapper();
            String m = null;
            try {
                m = mapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new BusinessException("转化消息到json失败！", e);
            }
            return m;
        }
    }

}
