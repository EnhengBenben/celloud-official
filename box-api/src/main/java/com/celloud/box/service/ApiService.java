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
	private static Logger logger = LoggerFactory.getLogger(ApiService.class);

	public Newfile newfile(Integer userId, String name, long size, String md5, Integer tagId, String batch) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("name", name);
		params.put("size", size);
		params.put("md5", md5);
		params.put("tagId", tagId);
		params.put("batch", batch);
		ApiResponse response = HttpClientUtil.post(api.getNewfile(), params);
		return response.isSuccess() ? new Newfile(response.getData()) : null;
	}

	public boolean updatefile(String objectKey, Integer fileId, Integer tagId, String batch, Integer needSplit) {
		Map<String, Object> params = new HashMap<>();
		params.put("objectKey", objectKey);
		params.put("fileId", fileId);
		params.put("tagId", tagId);
		params.put("batch", batch);
		params.put("needSplit", needSplit);
		ApiResponse response = HttpClientUtil.post(api.getUpdatefile(), params);
		return response.isSuccess();
	}

	@Async
	@Scheduled(cron = "0 0/5 * * * *")
	public void reportHealth() {
		Map<String, Object> params = new HashMap<>();
		params.put("version", config.getVersion());
		params.put("ip", LocalIpAddressUtil.getLocalArress(config.getNetwork()));
		params.put("serialNumber", config.getSerialNumber());
		ApiResponse response = HttpClientUtil.post(api.getReportHealth(), params);
		if (response.isSuccess()) {
			logger.info("成功。。。");
		} else {
			logger.info("失败。。。");
		}
	}
}
