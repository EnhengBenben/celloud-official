package com.celloud.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = getUsernameFromSession(session);
        logger.info("【{}】关闭了websocket链接 : status = {}", username, status.getCode());
        userSessions.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = getUsernameFromSession(session);
        logger.info("【{}】成功连接了websocket", username);
        userSessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = getUsernameFromSession(session);
        if (message instanceof PongMessage) {
            logger.debug("过滤来自{}的心跳消息", username);
            return;
        }
        logger.debug("接收来自【{}】的消息: {} ", username, message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
        String username = getUsernameFromSession(session);
        logger.error("【{}】websocket异常，已被关闭!", username, e);
        if (session.isOpen()) {
            session.close();
        }
        userSessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static List<WebSocketSession> getSessions(String username) {
        List<WebSocketSession> sessions = new ArrayList<>();
        for (WebSocketSession wss : userSessions.values()) {
            if (username.equals(getUsernameFromSession(wss))) {
                sessions.add(wss);
            }
        }
        return sessions;
    }

    public static Collection<WebSocketSession> getSessions() {
        return userSessions.values();
    }

    private static String getUsernameFromSession(WebSocketSession session) {
        User user = (User) session.getAttributes().get(Constants.SESSION_LOGIN_USER);
        return user == null ? null : user.getUsername();
    }
}