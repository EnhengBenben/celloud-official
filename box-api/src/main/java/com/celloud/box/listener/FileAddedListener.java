package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.constants.FileAddedEvent;
import com.celloud.box.service.FileUploadQueue;

@Component
public class FileAddedListener implements ApplicationListener<FileAddedEvent> {
	private static Logger logger = LoggerFactory.getLogger(FileAddedListener.class);
	@Resource
	private FileUploadQueue queue;

	@Async
	@Override
	public void onApplicationEvent(FileAddedEvent event) {
		logger.info("队列添加了新文件：{}", event.getSource());
		queue.upload();
	}

}
