package com.celloud.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import com.celloud.constants.BoxUploadState;
import com.celloud.model.BoxFile;
import com.celloud.service.DataService;

public class FileDownloadErrorListener implements ApplicationListener<FileDownloadErrorEvent> {
	private static Logger logger = LoggerFactory.getLogger(FileDownloadedListener.class);
	@Resource
	private DataService service;

	@Override
	public void onApplicationEvent(FileDownloadErrorEvent event) {
		BoxFile boxFile = (BoxFile) event.getSource();
		logger.info("文件下载失败:{}", boxFile.getPath());
		service.updateUploadState(boxFile.getFileId(), boxFile.getObjectKey(), BoxUploadState.FAIL_TO_CELLOUD);
	}

}
