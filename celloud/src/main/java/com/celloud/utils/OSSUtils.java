package com.celloud.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.aliyun.oss.model.UploadPartCopyRequest;
import com.aliyun.oss.model.UploadPartCopyResult;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.OSSConfig;

public class OSSUtils {
	private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
	private static final long partSize = 1024 * 1024 * 200;

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
			client.getObject(new GetObjectRequest(ConstantsData.getOSSConfig().getBucket(), objectKey), localFile);
		} catch (Exception e) {
			logger.error("下载文件失败:{}", objectKey, e);
		}
		logger.debug("downloaded file 【{}】 to 【{}】", objectKey, path);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("download file {} size={} in {} ,avg spead:{}", objectKey, formatSize(localFile.length()),
				formatTime(time), formatSpead(localFile.length(), time));
		return localFile.exists() ? MD5Util.getFileMD5(localFile) : null;
	}

	/**
	 * 将本地文件上传到oss
	 * 
	 * @param objectKey
	 *            oss存储路径和文件名，如为空则默认为本地文件名称
	 * @param filePath
	 *            要上传文件的本地路径
	 * @return 标识Multipart上传的OSSObject的URL地址。
	 */
	public static String upload(String objectKey, String filePath) {
		File file = new File(filePath);
		if (objectKey == null || objectKey.trim().length() == 0) {
			objectKey = file.getName();
		}
		return upload(objectKey, file);
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
		UploadFileRequest uploadFileRequest = new UploadFileRequest(ConstantsData.getOSSConfig().getBucket(),
				objectKey);
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
			logger.error("文件上传失败：{},{}", localFile.getAbsolutePath(), objectKey, e);
			location = null;
		}
		logger.info("uploaded file 【{}】 as 【{}】", localFile.getAbsolutePath(), objectKey);
		client.shutdown();
		time = System.currentTimeMillis() - time;
		logger.info("upload file {} size={} in {} ,avg spead:{}", objectKey, formatSize(localFile.length()),
				formatTime(time), formatSpead(localFile.length(), time));
		return location;
	}

	public static String moveObject(String sourceKey, String targetKey, Map<String, String> userMetadata) {
		OSSClient client = getClient();
		String bucket = ConstantsData.getOSSConfig().getBucket();
		ObjectMetadata metadata = client.getObjectMetadata(bucket, sourceKey);
		Map<String, String> map = metadata.getUserMetadata();
		map = map == null ? new HashMap<String, String>() : map;
		map.putAll(userMetadata);
		// copyObjectRequest.setNewObjectMetadata(metadata);
		long contentLength = metadata.getContentLength();
		// 计算分块数目
		int partCount = (int) (contentLength / partSize);
		if (contentLength % partSize != 0) {
			partCount++;
		}
		// 初始化拷贝任务
		InitiateMultipartUploadRequest uploadRequest = new InitiateMultipartUploadRequest(bucket, targetKey);
		ObjectMetadata newMetadata = new ObjectMetadata();
		newMetadata.setUserMetadata(map);
		uploadRequest.setObjectMetadata(newMetadata);
		InitiateMultipartUploadResult uploadResult = client.initiateMultipartUpload(uploadRequest);
		String uploadId = uploadResult.getUploadId();
		// 分片拷贝
		List<PartETag> partETags = new ArrayList<PartETag>();
		for (int i = 0; i < partCount; i++) {
			// 计算每个分块的大小
			long skipBytes = partSize * i;
			long size = partSize < contentLength - skipBytes ? partSize : contentLength - skipBytes;

			// 创建UploadPartCopyRequest
			UploadPartCopyRequest copyRequest = new UploadPartCopyRequest(bucket, sourceKey, bucket, targetKey);
			copyRequest.setUploadId(uploadId);
			copyRequest.setPartSize(size);
			copyRequest.setBeginIndex(skipBytes);
			copyRequest.setPartNumber(i + 1);
			UploadPartCopyResult uploadPartCopyResult = client.uploadPartCopy(copyRequest);

			// 将返回的PartETag保存到List中
			partETags.add(uploadPartCopyResult.getPartETag());
		}
		// 提交分片拷贝任务
		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucket,
				targetKey, uploadId, partETags);
		CompleteMultipartUploadResult result = client.completeMultipartUpload(completeMultipartUploadRequest);
		client.deleteObject(bucket, sourceKey);
		return result.getETag();
	}

	/**
	 * 获取文件的用户自定义元信息
	 * 
	 * @param objectKey
	 * @return
	 */
	public static Map<String, String> getMetadata(String objectKey) {
		OSSClient client = getClient();
		ObjectMetadata metadata = client.getObjectMetadata(ConstantsData.getOSSConfig().getBucket(), objectKey);
		Map<String, String> result = new HashMap<String, String>(metadata.getUserMetadata());
		result.put("md5", metadata.getContentMD5());
		result.put("size", String.valueOf(metadata.getContentLength()));
		result.put("etag", metadata.getETag());
		return result;
	}

	private static OSSClient getClient() {
		OSSConfig config = ConstantsData.getOSSConfig();
		Validate.notNull(config, "没有获取到oss配置");
		return new OSSClient(config.getEndpoint(), config.getKeyId(), config.getKeySecret());
	}

	/**
	 * 获取文件在oss上存储的objectKey<br>
	 * 命名规则:{userId}/{yyyyMMdd}/{dataKey}.{extName}
	 * 
	 * @param userId
	 * @param dataKey
	 * @param ext
	 * @return
	 */
	public static String getObjectKey(Integer userId, String dataKey, String ext) {
		return userId + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + dataKey
				+ (ext.startsWith(".") ? ext : "." + ext);
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
