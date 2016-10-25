package com.celloud.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;

public class OSSUtils {
	private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
	private static String bucketName = "celloud-dev";
	private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	// accessKey请登录https://ak-console.aliyun.com/#/查看
	private static String accessKeyId = "UrKY3uHmTuwajVKU";
	private static String accessKeySecret = "zK4cU4cEHPEnQNxu5NnnmCSvBHXuOd";

	/**
	 * 从oss下载文件
	 * 
	 * @param objectKey
	 *            要下载的oss文件
	 * @param path
	 *            本地文件路径（包括文件名）
	 * @return 下载后的文件的md5
	 */
	public static String download(String objectKey, String path) {
		logger.info("downloading file 【{}】 to 【{}】", objectKey, path);
		long time = System.currentTimeMillis();
		OSSClient client = getClient();
		File localFile = new File(path);
		if (localFile.exists()) {
			localFile.delete();
		}
		localFile.getParentFile().mkdirs();
		// 下载object到文件
		try {
			client.getObject(new GetObjectRequest(bucketName, objectKey), localFile);
		} catch (Exception e) {
			logger.error("下载文件失败:{}", objectKey, e);
			return null;
		}
		logger.debug("downloaded file 【{}】 to 【{}】", objectKey, path);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("download file {} size={} in {} ,avg spead:{}", objectKey, formatSize(localFile.length()),
				formatTime(time), formatSpead(localFile.length(), time));
		return MD5Util.getFileMD5(localFile);
	}

	/**
	 * 上传文件到oss
	 * 
	 * @param objectKey
	 *            oss存储路径和文件名
	 * @param localFile
	 *            要上传的本地文件
	 * @return 标识Multipart上传的OSSObject的URL地址。
	 */
	public static String upload(String objectKey, File localFile) {
		logger.info("uploading file 【{}】 as 【{}】", localFile.getAbsolutePath(), objectKey);
		long time = System.currentTimeMillis();
		OSSClient client = getClient();
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, objectKey);
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
			logger.info("location : {} ", location);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		logger.info("uploaded file 【{}】 as 【{}】", localFile.getAbsolutePath(), objectKey);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("upload file {} size={} in {} ,avg spead:{}", objectKey, formatSize(localFile.length()),
				formatTime(time), formatSpead(localFile.length(), time));
		return location;
	}

	public static void clearBucket() {
		OSSClient client = getClient();
		ObjectListing list = null;
		final int maxKeys = 100;
		String nextMarker = null;
		do {
			list = client.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));
			List<String> keys = new ArrayList<>();
			for (OSSObjectSummary summary : list.getObjectSummaries()) {
				keys.add(summary.getKey());
			}
			DeleteObjectsResult result = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
			for (String obj : result.getDeletedObjects()) {
				logger.info("deleted object : {}", obj);
			}
			nextMarker = list.getNextMarker();
		} while (list.isTruncated());

	}

	public static String upload(String objectKey, String filePath) {
		File file = new File(filePath);
		if (objectKey == null || objectKey.trim().length() == 0) {
			objectKey = file.getName();
		}
		return upload(objectKey, file);
	}

	private static OSSClient getClient() {
		return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	private static String formatSpead(long size, long timeMillis) {
		return formatSize(size * 1000 / timeMillis) + "/s";
	}

	public static String formatSize(double size) {
		double result = size / 1;
		String unit = "b";
		if (result > 1024) {
			result = result / 1024;
			unit = "kb";
		}
		if (result > 1024) {
			result = result / 1024;
			unit = "mb";
		}
		if (result > 1024) {
			result = result / 1024;
			unit = "gb";
		}
		return new DecimalFormat("#.00").format(result) + "" + unit;
	}

	private static String formatTime(long timeMillis) {
		if (timeMillis < 1000) {
			return "< 1s";
		}
		int ss = 0, mm = 0, hh = 0;
		ss = (int) (timeMillis / 1000);
		int radix = 60;
		if (ss > radix) {
			ss = ss % radix;
			mm = ss / radix;
			if (mm > radix) {
				mm = mm % radix;
				hh = mm / radix;
			}
		}
		String result = "";
		if (hh > 0) {
			result = hh + "h";
		}
		if (mm > 0) {
			result = result + mm + "m";
		}
		if (ss > 0) {
			result = result + ss + "s";
		}
		return result;
	}

	public static void main(String[] args) {
		// String key = "1463463723668/apache-activemq-5.13.3-bin.tar.gz";
		// String path = "/share/data/file/23/20160517/16051702998827.tar.gz";
		// download(key, path);
		String file = "/Users/sun8wd/Documents/Shiro教程.pdf";
		upload(null, file);
	}
}
