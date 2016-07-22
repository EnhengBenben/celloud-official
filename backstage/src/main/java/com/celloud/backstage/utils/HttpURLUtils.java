package com.celloud.backstage.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HTTP 工具类
 * 
 * @author lin
 * @date 2016-1-10 下午11:14:55
 */
public class HttpURLUtils {
	private static Logger log = Logger.getLogger(HttpURLUtils.class);

    /**
     * post请求http
     * 
     * @param url
     * @param params
     *            json格式数据
     * @return
     * @author leamo
     * @date 2016年7月4日 下午4:29:28
     */
    public static String httpPostRequest(String url, String params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {
            if (params != null) {
                StringEntity entity = new StringEntity(params, "utf-8");
                entity.setContentType("application/json");
                entity.setContentEncoding("utf-8");
                // 设置策略，防止报cookie错误
                DefaultHttpParams.getDefaultParams().setParameter(
                        "http.protocol.cookie-policy",
                        CookiePolicy.BROWSER_COMPATIBILITY);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
                result = EntityUtils.toString(response.getEntity());
            } else {
                result = String
                        .valueOf(response.getStatusLine().getStatusCode());
            }
        } catch (UnsupportedEncodingException e) {
            log.error("post请求异常：" + e);
        } catch (ClientProtocolException e) {
            log.error("post请求异常：" + e);
        } catch (IOException e) {
            log.error("post请求异常：" + e);
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }

	/**
	 * post方式请求HTTP
	 * 
	 * @param url：路径
	 * @param params：参数
	 * @return
	 * @author lin
	 * @date 2016年6月6日下午3:50:18
	 */
	public static String httpPostRequest(String url, List<NameValuePair> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String result = null;
		try {
			if (params != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
				result = EntityUtils.toString(response.getEntity());
			} else {
				result = String.valueOf(response.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
			log.error("post请求异常：" + e);
		} catch (ClientProtocolException e) {
			log.error("post请求异常：" + e);
		} catch (IOException e) {
			log.error("post请求异常：" + e);
		} finally {
			httpPost.releaseConnection();
		}
		return result;
	}

	public static String httpGetRequest(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		String result = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
				result = EntityUtils.toString(response.getEntity());
			} else {
				result = String.valueOf(response.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
			log.error("get请求异常：" + e);
		} catch (ClientProtocolException e) {
			log.error("get请求异常：" + e);
		} catch (IOException e) {
			log.error("get请求异常：" + e);
		} finally {
			httpGet.releaseConnection();
		}
		return result;
	}
}
