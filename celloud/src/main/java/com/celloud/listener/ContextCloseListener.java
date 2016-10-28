package com.celloud.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import com.celloud.message.MessageReceiver;

@Service
public class ContextCloseListener implements ApplicationListener<ContextClosedEvent> {
	Logger logger = LoggerFactory.getLogger(ContextCloseListener.class);

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		logger.info("清理所有的messageReceiver........");
		try {
			MessageReceiver.shutdownAll();
		} catch (Exception e) {
		}
	}

}
