package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.FileUploadErrorEvent;
import com.celloud.box.service.FileUploadQueue;

/**
 * 文件上传到oss监听器，监听文件上传到oss失败的事件，文件上传失败后，将文件重新添加的队列最后。
 * 
 * @author sun8wd
 *
 */
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
