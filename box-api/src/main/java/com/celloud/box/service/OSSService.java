package com.celloud.box.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.celloud.box.config.OSSConfig;
import com.celloud.box.utils.SpeedUtil;

@Component
public class OSSService {
	@Autowired
	private OSSConfig config;
	private static Logger logger = LoggerFactory.getLogger(OSSService.class);

	public void download(String objectKey, String path) {
		logger.debug("downloading file 【{}】 to 【{}】", objectKey, path);
		long time = System.currentTimeMillis();
		OSSClient client = getClient();
		File localFile = new File(path);
		if (localFile.exists()) {
			localFile.delete();
		}
		// 下载object到文件
		client.getObject(new GetObjectRequest(config.getBucketName(), objectKey), localFile);
		logger.debug("downloaded file 【{}】 to 【{}】", objectKey, path);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("download file {} size={} in {} ,avg spead:{}", objectKey, SpeedUtil.formatSize(localFile.length()),
				SpeedUtil.formatTime(time), SpeedUtil.formatSpead(localFile.length(), time));
	}

	public String upload(String objectKey, File localFile) {
		logger.debug("uploading file 【{}】 as 【{}】", localFile.getAbsolutePath(), objectKey);
		long time = System.currentTimeMillis();
		OSSClient client = getClient();
		UploadFileRequest uploadFileRequest = new UploadFileRequest(config.getBucketName(), objectKey);
		String location = null;
		uploadFileRequest.setUploadFile(localFile.getAbsolutePath());
		// 指定上传并发线程数
		uploadFileRequest.setTaskNum(5);
		// 指定上传的分片大小
		uploadFileRequest.setPartSize(1 * 1024 * 1024);
		// 开启断点续传
		uploadFileRequest.setEnableCheckpoint(true);
		// 断点续传上传
		try {
			UploadFileResult result = client.uploadFile(uploadFileRequest);
			location = result.getMultipartUploadResult().getLocation();
			logger.debug("location : {} ", location);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		logger.debug("uploaded file 【{}】 as 【{}】", localFile.getAbsolutePath(), objectKey);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("upload file {} size={} in {} ,avg spead:{}", objectKey, SpeedUtil.formatSize(localFile.length()),
				SpeedUtil.formatTime(time), SpeedUtil.formatSpead(localFile.length(), time));
		return location;
	}

	public void clearBucket() {
		OSSClient client = getClient();
		ObjectListing list = null;
		final int maxKeys = 100;
		String nextMarker = null;
		do {
			list = client.listObjects(
					new ListObjectsRequest(config.getBucketName()).withMarker(nextMarker).withMaxKeys(maxKeys));
			List<String> keys = new ArrayList<>();
			for (OSSObjectSummary summary : list.getObjectSummaries()) {
				keys.add(summary.getKey());
			}
			DeleteObjectsResult result = client
					.deleteObjects(new DeleteObjectsRequest(config.getBucketName()).withKeys(keys));
			for (String obj : result.getDeletedObjects()) {
				logger.info("deleted object : {}", obj);
			}
			nextMarker = list.getNextMarker();
		} while (list.isTruncated());

	}

	public String upload(String objectKey, String filePath) {
		File file = new File(filePath);
		if (objectKey == null || objectKey.trim().length() == 0) {
			objectKey = file.getName();
		}
		return upload(objectKey, file);
	}

	private OSSClient getClient() {
		return new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
	}

}
