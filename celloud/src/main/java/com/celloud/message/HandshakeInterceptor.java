package com.celloud.message;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.celloud.constants.Constants;
import com.celloud.model.mysql.User;

@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
            Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
                logger.debug("handshake from {}", user.getUsername());
                map.put(Constants.SESSION_LOGIN_USER, user);
            }
        }
        return true;
    }
}
