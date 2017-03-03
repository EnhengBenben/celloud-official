package com.celloud.box.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.celloud.box.config.APIConfig;
import com.celloud.box.config.BoxConfig;
import com.celloud.box.config.OSSConfig;
import com.celloud.box.constants.ApiResponse;
import com.celloud.box.model.Newfile;
import com.celloud.box.utils.HttpClientUtil;
import com.celloud.box.utils.LocalIpAddressUtil;

@Component
public class ApiService {
	@Autowired
	private APIConfig api;
	@Autowired
	private BoxConfig config;
	@Autowired
	private OSSConfig ossConfig;
	private static Logger logger = LoggerFactory.getLogger(ApiService.class);

    public Boolean fileRunOver(Integer r1Id, Integer r2Id) {
        Map<String, Object> params = new HashMap<>();
        params.put("r1Id", r1Id);
        params.put("r2Id", r2Id);
        ApiResponse response = HttpClientUtil.post(api.getFileRunOver(), params);
        return response.isSuccess();
    }

	/**
	 * 
	 * @author miaoqi
	 * @date 2017年1月20日下午4:16:07
	 * @description 向celloud请求split所需的.txt参数
	 *
	 * @return
	 */
	public Map<String, Object> splittxt(Integer userId, String pubName, String storageName, String batch) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("pubName", pubName);
		params.put("storageName", storageName);
		params.put("batch", batch);
        ApiResponse response = HttpClientUtil.post(api.getSplittxt(), params);
		if (response.isSuccess()) {
			// 在盒子内生成txt文件
			return response.getData();
		} else {
			return null;
		}
	}

	public Newfile newfile(Integer userId, String name, String anotherName, long size, String md5, Integer tagId,
			String batch) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("name", name);
		if (anotherName != null) {
			params.put("anotherName", anotherName);
		}
		params.put("size", size);
		params.put("md5", md5);
		params.put("tagId", tagId);
		params.put("batch", batch);
		ApiResponse response = HttpClientUtil.post(api.getNewfile(), params);
		return response.isSuccess() ? new Newfile(response.getData()) : null;
	}

	public boolean updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit,
			boolean splited) {
		Map<String, Object> params = new HashMap<>();
		params.put("objectKey", objectKey);
		params.put("fileId", fileId);
		params.put("tagId", tagId);
		params.put("batch", batch);
		if (needSplit != null) {
			params.put("needSplit", needSplit);
		}
		if (splited) {
			params.put("splited", splited);
		}
		ApiResponse response = HttpClientUtil.post(api.getUpdatefile(), params);
		return response.isSuccess();
	}

	@Async
	@Scheduled(fixedRateString = "${box.health-rate}")
	public void reportHealth() {
		Map<String, Object> params = new HashMap<>();
		params.put("version", config.getVersion());
		params.put("port", config.getPort());
		params.put("ip", LocalIpAddressUtil.getLocalArress(config.getNetwork()));
		params.put("serialNumber", config.getSerialNumber());
		ApiResponse response = HttpClientUtil.post(api.getReportHealth(), params);
		logger.info("状态更新{}", response.isSuccess() ? "成功" : "失败");
	}

	@Async
	@Scheduled(fixedRateString = "${box.ossConfig-rate}")
	public void fetchOSSConfig() {
		Map<String, Object> params = new HashMap<>();
		params.put("serialNumber", config.getSerialNumber());
		params.put("version", config.getVersion());
		params.put("port", config.getPort());
		params.put("ip", LocalIpAddressUtil.getLocalArress(config.getNetwork()));
		ApiResponse response = HttpClientUtil.post(api.getOssConfig(), params);
		if (response.isSuccess()) {
			ossConfig.setAccessKeyId((String) response.getData().get("keyId"));
			ossConfig.setAccessKeySecret((String) response.getData().get("keySecret"));
			ossConfig.setBucketName((String) response.getData().get("bucket"));
			ossConfig.setEndpoint((String) response.getData().get("endpoint"));
			logger.info("成功获取到oss的配置：{}", response.getData());
		} else {
			logger.info("获取oss配置失败：{}", response.getMessage());
		}
	}
}
