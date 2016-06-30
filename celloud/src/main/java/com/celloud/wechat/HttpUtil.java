package com.celloud.wechat;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;

public class HttpUtil {
    public static String sendWeChatMessage(String url, String param) {
		HttpClient client = new HttpClient();
		// 请求链接
		PostMethod post = new PostMethod(url);
		post.releaseConnection();
		post.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		// 设置策略，防止报cookie错误
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 给post设置参数
		post.setRequestBody(param);
		String result = "";
		try {
			client.executeMethod(post);
			result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		post.releaseConnection();
		return result;
	}
}
