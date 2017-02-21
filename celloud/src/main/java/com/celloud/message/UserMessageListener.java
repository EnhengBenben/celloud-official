package com.celloud.message;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.celloud.common.mq.AloneMessageListener;
import com.celloud.common.mq.entity.Message;
import com.celloud.constants.Constants;
import com.celloud.model.mysql.User;

public class UserMessageListener implements AloneMessageListener {
	private static Logger logger = LoggerFactory.getLogger(UserMessageListener.class);

	@Override
	public boolean consume(Message message) {
		String key = message.getKey();
		String value = new String(message.getBodyAsBytes());
		logger.debug("key = {} , value = {}", key, value);
		if (Constants.MESSAGE_ALLUSER_KEY.equals(key)) {
			sendMessage(value);
		} else {
			sendMessage(key, value);
		}
		return true;
	}

	/**
	 * 给指定用户发送消息
	 * 
	 * @param username
	 * @param message
	 */
	private void sendMessage(String username, String message) {
		Collection<WebSocketSession> sessions = SystemWebSocketHandler.getSessions(username);
		TextMessage tm = new TextMessage(message);
		for (WebSocketSession session : sessions) {
			sendMessage(session, tm);
		}
	}

	/**
	 * 给所有用户发送消息
	 * 
	 * @param message
	 */
	private void sendMessage(String message) {
		TextMessage tm = new TextMessage(message);
		for (WebSocketSession session : SystemWebSocketHandler.getSessions()) {
			sendMessage(session, tm);
		}
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
				logger.error("发送消息给【{}】失败：{}", getUsernameFromSession(session), message.getPayload(), e);
			}
		}
	}

	private String getUsernameFromSession(WebSocketSession session) {
		User user = (User) session.getAttributes().get(Constants.SESSION_LOGIN_USER);
		return user == null ? null : user.getUsername();
	}
}
