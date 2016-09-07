package com.celloud.box.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.box.constants.ApiResponse;
import com.celloud.box.model.Newfile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static ApiResponse get(String url, Map<String, Object> params) {
		ApiResponse res = ApiResponse.ERROR;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (entry.getValue() != null) {
						builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
			}
			uri = builder.build();
		} catch (URISyntaxException e) {
			logger.error("uri构建失败！", e);
			return res;
		}
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			logger.error("http get 请求失败！", e);
			return res;
		}
		res = parseResponse(response);
		close(httpClient, response);
		return res;
	}

	public static ApiResponse post(String url, Map<String, Object> params) {
		ApiResponse res = ApiResponse.ERROR;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					urlParameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			System.out.println(urlParameters.toString());
		} catch (UnsupportedEncodingException e) {
			logger.error("请求参数编码失败！", e);
			return res;
		}
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpPost);
		} catch (IOException e) {
			logger.error("http post 请求失败！", e);
			return res;
		}
		res = parseResponse(response);
		close(httpClient, response);
		return res;
	}

	private static ApiResponse parseResponse(CloseableHttpResponse response) {
		ApiResponse res = ApiResponse.ERROR;
		String result;
		try {
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ParseException | IOException e) {
			logger.error("解析entity失败！", e);
			return res;
		}

		try {
			res = new ObjectMapper().readValue(result, ApiResponse.class);
		} catch (IOException e) {
			logger.error("解析json失败！", e);
		}
		return res;
	}

	private static void close(CloseableHttpClient client, CloseableHttpResponse response) {
		try {
			if (client != null) {
				client.close();
			}
			if (response != null) {
				response.close();
			}
		} catch (IOException e) {

		}
	}

	public static void main(String[] args) {
		ApiResponse res = HttpClientUtil.get("http://localhost:8080/celloud/api/box/newfile", null);
		Newfile file = new Newfile(res.getData());
		System.out.println(file.getDataKey());
		System.out.println(file.getExt());
		System.out.println(file.getNewName());
		System.out.println(file.getFileId());
	}
}
