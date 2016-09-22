package com.celloud.box.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.celloud.box.config.APIConfig;
import com.celloud.box.constants.ApiResponse;
import com.celloud.box.model.Newfile;
import com.celloud.box.utils.HttpClientUtil;

@Component
public class ApiService {
	@Autowired
	private APIConfig api;

	public Newfile newfile(Integer userId, String name, long size, String md5, Integer tagId, String batch) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("name", name);
		params.put("size", size);
		params.put("md5", md5);
		params.put("tagId", tagId);
		params.put("batch", batch);
		ApiResponse response = HttpClientUtil.post(api.getNewfile(), params);
		return new Newfile(response.getData());
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
}
