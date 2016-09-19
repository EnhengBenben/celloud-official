package com.celloud.box.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.box.constants.ApiResponse;
import com.celloud.box.model.Newfile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * 发起GET方式请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static ApiResponse get(String url, Map<String, Object> params) {
		ApiResponse res = ApiResponse.ERROR;
		CloseableHttpClient httpClient = createClient(url);
		URI uri;
		try {
			URIBuilder builder = new URIBuilder(url);
			List<NameValuePair> urlParameters = parseParams(params);
			builder.addParameters(urlParameters);
			uri = builder.build();
			logger.info(uri.toString());
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

	/**
	 * 发起POST方式请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static ApiResponse post(String url, Map<String, Object> params) {
		ApiResponse res = ApiResponse.ERROR;
		CloseableHttpClient httpClient = createClient(url);
		HttpPost httpPost = new HttpPost(url);
		try {
			List<NameValuePair> urlParameters = parseParams(params);
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
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

	/**
	 * 将get/post方式请求的response转换成自定义的ApiResponse
	 * 
	 * @param response
	 * @return
	 */
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

	/**
	 * 将Map类型的请求参数转换成Name-Value形式
	 * 
	 * @param params
	 * @return
	 */
	private static List<NameValuePair> parseParams(Map<String, Object> params) {
		List<NameValuePair> pairs = new ArrayList<>();
		if (params == null) {
			return pairs;
		}
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();
			String name = entry.getKey();
			if (value == null) {
				continue;
			}
			if (value.getClass().isArray()) {// 处理数组
				Object[] objects = (Object[]) value;
				for (Object o : objects) {
					if (o == null) {
						continue;
					}
					pairs.add(new BasicNameValuePair(name, o.toString()));
				}
			} else if (value instanceof Collection) {// 处理集合
				@SuppressWarnings("unchecked")
				List<Object> set = new ArrayList<>((Collection<Object>) value);
				for (Object o : set) {
					if (o == null) {
						continue;
					}
					pairs.add(new BasicNameValuePair(name, o.toString()));
				}
			} else {
				pairs.add(new BasicNameValuePair(name, value.toString()));
			}
		}
		return pairs;
	}

	/**
	 * 关闭请求
	 * 
	 * @param client
	 * @param response
	 */
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

	public static CloseableHttpClient createClient(String url) {
		if (url == null || url.trim().length() <= 5 || !url.trim().toLowerCase().startsWith("https")) {
			return HttpClients.createDefault();
		}
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error("创建安全链接失败");
		}
		return HttpClients.createDefault();
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<>();
		params.put("b", new Byte[] { 1, 2, 3, 4, 5 });
		params.put("size", 123);
		params.put("name", "a.txt");
		params.put("userId", "12");
		params.put("tagId", "1");
		ApiResponse res = HttpClientUtil.get("http://localhost:8080/celloud/api/box/newfile", params);
		Newfile file = new Newfile(res.getData());
		System.out.println(file.getDataKey());
		System.out.println(file.getExt());
		System.out.println(file.getNewName());
		System.out.println(file.getFileId());
	}
}
