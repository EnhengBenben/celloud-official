package com.celloud.box.listener;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.celloud.box.constants.FileUploadedEvent;
import com.celloud.box.model.DataFile;
import com.celloud.box.service.BoxService;
import com.celloud.box.service.FileUploadQueue;

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
		service.uploaded(file);
		service.updatefile(file);
	}

}
