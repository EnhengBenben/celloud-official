package com.celloud.service.impl;

import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.celloud.listener.FileDownloadErrorEvent;
import com.celloud.listener.FileDownloadedEvent;
import com.celloud.model.BoxFile;
import com.celloud.service.BoxApiService;
import com.celloud.service.OSSConfigService;
import com.celloud.utils.OSSUtils;

@Service
public class BoxApiServiceImpl implements BoxApiService {
	private static Logger logger = LoggerFactory.getLogger(BoxApiServiceImpl.class);
	@Resource
	private ApplicationContext context;
	@Resource
	private OSSConfigService ossService;

	@Async
	@Override
	public Future<Boolean> downloadFromOSS(BoxFile file) {
		boolean isDownloaded = downloadFromOSS(file.getObjectKey(), file.getPath(), file.getMd5());
        logger.info("downloadFromOSS isDownloaded = {}, fileName = {}", isDownloaded, file.getFileName());
		context.publishEvent(isDownloaded ? new FileDownloadedEvent(file) : new FileDownloadErrorEvent(file));
		return new AsyncResult<Boolean>(isDownloaded);
	}

	@Override
	public Boolean downloadFromOSS(String objectKey, String path, String md5) {
		boolean isDownloaded = false;
		ossService.refreshConfig();
		for (int i = 0; i < 5; i++) {
			String temp = OSSUtils.download(objectKey, path);
			if (md5.equals(temp)) {
				isDownloaded = true;
				break;
			} else {
				ossService.refreshConfig();
				logger.info("从oss下载文件错误，进行第{}次重试：{}", i + 1, objectKey);
			}
		}
		return isDownloaded;
	}

}
