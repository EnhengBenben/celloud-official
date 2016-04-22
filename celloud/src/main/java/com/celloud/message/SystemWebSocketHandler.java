package com.celloud.message;

import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.celloud.constants.Constants;
import com.celloud.model.mysql.User;

@Component
public class SystemWebSocketHandler implements WebSocketHandler {
    public static final MultiValueMap userSessions = new MultiValueMap();
    private static Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    public User getUserFromSession(WebSocketSession session) {
        User user = (User) session.getAttributes().get(Constants.SESSION_LOGIN_USER);
        return user;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = getUserFromSession(session).getUsername();
        logger.info("【{}】关闭了websocket链接 : status = {}", username, status.getCode());
        userSessions.remove(username, session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = getUserFromSession(session).getUsername();
        logger.info("【{}】成功连接了websocket", username);
        userSessions.put(username, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = getUserFromSession(session).getUsername();
        if (message instanceof PongMessage) {
            logger.debug("过滤来自{}的心跳消息", username);
            return;
        }
        logger.debug("接收来自【{}】的消息: {} ", username, message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
        String username = getUserFromSession(session).getUsername();
        logger.error("【{}】websocket异常，已被关闭!", username, e);
        if (session.isOpen()) {
            session.close();
        }
        userSessions.remove(username, session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}