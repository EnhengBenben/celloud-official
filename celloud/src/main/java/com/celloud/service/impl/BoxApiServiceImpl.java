package com.celloud.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.listener.FileDownloadErrorEvent;
import com.celloud.listener.FileDownloadedEvent;
import com.celloud.model.BoxFile;
import com.celloud.service.BoxApiService;
import com.celloud.utils.OSSUtils;

@Service
public class BoxApiServiceImpl implements BoxApiService {
	private static Logger logger = LoggerFactory.getLogger(BoxApiServiceImpl.class);
	@Resource
	private ApplicationContext context;

	@Async
	@Override
	public void downloadFromOSS(BoxFile file) {
		boolean isDownloaded = false;
		for (int i = 0; i < 5; i++) {
			String temp = OSSUtils.download(file.getObjectKey(), file.getPath());
			if (file.getMd5().equals(temp)) {
				isDownloaded = true;
				break;
			} else {
				logger.info("从oss下载文件错误，进行第{}次重试：{}", i + 1, file.getObjectKey());
			}
		}
		context.publishEvent(isDownloaded ? new FileDownloadedEvent(file) : new FileDownloadErrorEvent(file));
	}

}
