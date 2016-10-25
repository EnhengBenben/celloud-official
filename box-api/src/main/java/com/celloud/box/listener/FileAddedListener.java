package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.FileAddedEvent;
import com.celloud.box.service.FileUploadQueue;

/**
 * 文件上传到oss队列的监听器，当文件添加到队列的时候执行文件上传操作
 * 
 * @author sun8wd
 *
 */
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
