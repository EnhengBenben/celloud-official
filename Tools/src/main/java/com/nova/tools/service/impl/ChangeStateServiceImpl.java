package com.nova.tools.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.nova.tools.utils.Encrypt;
import com.nova.tools.utils.PropertiesUtils;

/**
 * @Description:修改报告状态
 * @author lin
 * @date 2013-9-2 上午10:04:15
 */
public class ChangeStateServiceImpl {
	public static Logger log = Logger.getLogger(ChangeStateServiceImpl.class);
	private static String celloud = PropertiesUtils.celloud;

	/**
	 * 修改报告状态
	 * 
	 * @param appId
	 * @param appName
	 * @param projectId
	 * @param dataKey
	 * @param state
	 *            :0:未执行;1:执行但是没有报告产生;2:正在运行并且已经产生报告;3:执行完毕;
	 * @param userId
	 * @return
	 */
	public static void changeState(String appId, String appName,
			String projectId, String dataKey, int state, String userId,
			String context) {
		String url = null;
		// 数据报告
		if (projectId == null) {
			url = celloud + "updateReportByDataKeySoftId.action?dataKeys="
					+ dataKey + "&softwareId=" + appId + "&state=" + state
					+ "&appResult=" + userId + "," + appId + "," + appName
					+ "," + dataKey + ",0";
		}else{
			if (context != null) {
				context = Encrypt.encrypt(context);
			}
			url = celloud + "updateReportByProSoftId.action?projectId="
					+ projectId + "&softwareIds=" + appId + "&state=" + state
					+ "&userId=" + userId + "&appResult=" + userId + ","
					+ appId + "," + appName + "," + projectId + ",1&context="
					+ context;
		}
		remoteRequest(url);
	}

	/**
	 * 该方法用于运行项目时逐个修改其下的数据状态（ QC 和 miRNA）
	 * 
	 * @param appId
	 * @param dataKey
	 * @param state
	 * @return
	 */
	public static void changeDataState(String appId, String dataKey, int state) {
		String url = celloud + "addDatasReport.action?dataKeys=" + dataKey
				+ "&softwareId=" + appId + "&state=" + state;
		remoteRequest(url);
	}

	/**
	 * 远程请求
	 * 
	 * @param url
	 */
	private static void remoteRequest(String url) {
		log.info("remoteRequest:url=" + url);
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setConnectTimeout(120000);
			conn.setReadTimeout(240000);
			conn.connect();
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			StringBuffer buff = new StringBuffer();
			String lineStr = null;
			while ((lineStr = br.readLine()) != null) {
				buff.append(lineStr);
			}
			String returnValue = buff.toString();
			log.info("result from celloud:" + returnValue);
		} catch (MalformedURLException e) {
			log.error("remoteRequest:url=" + url + ",error:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("remoteRequest:url=" + url + ",error:" + e);
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					log.error("remoteRequest:url=" + url + ",error:" + e);
				}
		}
	}

	/**
	 * 保存CMP运行报告
	 * 
	 * @param map
	 */
	public static void saveCmpReport(String params) {
		String url = celloud + "cmpReport!addReport?" + params;
		remoteRequest(url);
	}
}