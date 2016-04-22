package com.celloud.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.celloud.constants.Constants;
import com.celloud.exception.BusinessException;
import com.celloud.model.mysql.User;
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
public class MessageUtils {
    private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);
    private Message message = new Message();

    private User getUserFromSession(WebSocketSession session) {
        User user = (User) session.getAttributes().get(Constants.SESSION_LOGIN_USER);
        return user;
    }

    /**
     * 获取工具类实体
     * 
     * @return
     */
    public static MessageUtils get() {
        return new MessageUtils();
    }

    /**
     * 发送的数据,任意实体类，自动转换为json格式，方便前台接收
     * 
     * @param message
     * @return
     */
    public MessageUtils send(Object message) {
        this.message.setMessage(message);
        return this;
    }

    /**
     * 发送给指定用户
     * 
     * @param usernames
     */
    @SuppressWarnings("unchecked")
    public void to(String... usernames) {
        if (usernames == null || usernames.length <= 0) {
            throw new BusinessException("发送消息没有指定接收者:" + message.toJson());
        }
        List<Object> sessions = new ArrayList<>();
        for (String username : usernames) {
            sessions.addAll(SystemWebSocketHandler.userSessions.getCollection(username));
        }
        TextMessage tm = new TextMessage(message.toJson());
        for (Object session : sessions) {
            sendMessage((WebSocketSession) session, tm);
        }
        return;
    }

    /**
     * 发送给所有用户
     */
    public void toAll() {
        TextMessage tm = new TextMessage(message.toJson());
        for (Object session : SystemWebSocketHandler.userSessions.values()) {
            sendMessage((WebSocketSession) session, tm);
        }
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
     * 给用户发送消息
     * 
     * @param session
     * @param message
     */
    private void sendMessage(WebSocketSession session, TextMessage message) {
        if (session.isOpen()) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                logger.error("发送消息给【{}】失败：{}", getUserFromSession(session).getUsername(), message.getPayload(), e);
            }
        }
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
                e.printStackTrace();
            }
            return m;
        }
    }

}
