package com.celloud.box.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;

public class OSSProgressListener implements ProgressListener {
	private Logger logger = LoggerFactory.getLogger(OSSProgressListener.class);
	private long bytesWritten = 0;
	private long totalBytes = -1;
	private boolean succeed = false;
	private Integer userId;
	private String fileName;
	private String dataKey;

	public OSSProgressListener(Integer userId, String fileName, String dataKey) {
		this.userId = userId;
		this.fileName = fileName;
		this.dataKey = dataKey;
	}

	@Override
	public void progressChanged(ProgressEvent progressEvent) {
		long bytes = progressEvent.getBytes();
		ProgressEventType eventType = progressEvent.getEventType();
		switch (eventType) {
		case TRANSFER_STARTED_EVENT:
			logger.info("开始上传文件 : userId={},dataKey={},filename={}", userId, dataKey, fileName);
			break;
		case REQUEST_CONTENT_LENGTH_EVENT:
			this.totalBytes = bytes;
			break;
		case REQUEST_BYTE_TRANSFER_EVENT:
			this.bytesWritten += bytes;
			if (this.totalBytes != -1) {
				int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
				logger.info("文件已上传{}%({}b/{}b) : userId={},dataKey={},filename={}", percent, bytesWritten, totalBytes,
						userId, dataKey, fileName);
			} else {
				logger.info("文件已上传{} b : userId={},dataKey={},filename={}", bytesWritten, userId, dataKey, fileName);
			}
			break;
		case TRANSFER_COMPLETED_EVENT:
			this.succeed = true;
			logger.info("文件已上传成功 : userId={},dataKey={},filename={}", userId, dataKey, fileName);
			break;
		case TRANSFER_FAILED_EVENT:
			logger.info("文件已上传失败({}b) : userId={},dataKey={},filename={}", bytesWritten, userId, dataKey, fileName);
			break;
		default:
			break;
		}
	}

	public boolean isSucceed() {
		return succeed;
	}
}
