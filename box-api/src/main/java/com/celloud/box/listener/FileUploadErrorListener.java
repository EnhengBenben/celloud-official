package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.constants.FileUploadErrorEvent;
import com.celloud.box.service.FileUploadQueue;

@Component
public class FileUploadErrorListener implements ApplicationListener<FileUploadErrorEvent> {
	private static Logger logger = LoggerFactory.getLogger(FileUploadErrorListener.class);
	@Resource
	private FileUploadQueue queue;

	@Async
	@Override
	public void onApplicationEvent(FileUploadErrorEvent event) {
		logger.info("文件上传失败，重新添加到队列：{}", event.getSource());
		queue.add((String) event.getSource());
	}

}
