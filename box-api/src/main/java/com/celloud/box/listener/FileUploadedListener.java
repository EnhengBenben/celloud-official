package com.celloud.box.listener;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.event.FileUploadedEvent;
import com.celloud.box.model.DataFile;
import com.celloud.box.service.BoxService;
import com.celloud.box.service.FileUploadQueue;

/**
 * 文件上传到oos的监听器，监听一个文件上传完成的事件，当某个文件上传完成时触发，执行下一个上传
 * 
 * @author sun8wd
 *
 */
@Component
public class FileUploadedListener implements ApplicationListener<FileUploadedEvent> {
	private static Logger logger = LoggerFactory.getLogger(FileAddedListener.class);
	@Resource
	private FileUploadQueue queue;
	@Resource
	private BoxService service;

	@Async
	@Override
	public void onApplicationEvent(FileUploadedEvent event) {
		logger.info("文件上传完成：{}", event.getSource());
		queue.upload();
		DataFile file = DataFile.load(new File(event.getSource() + ".json"));
		file.setUploaded(Boolean.TRUE);
		file.serialize();
		service.finish(file);
		service.updatefile(file);
	}

}
