package com.celloud.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.Mod;
import com.celloud.exception.BusinessException;

public class AppSubmitUtil {
	private static Logger logger = LoggerFactory.getLogger(AppSubmitUtil.class);
	private static Map<String, Map<String, String>> machines = ConstantsData.getMachines();
	private static String sparkhost = machines.get("spark").get(Mod.HOST);
	private static String sparkpwd = machines.get("spark").get(Mod.PWD);
	private static String sparkuserName = machines.get("spark").get(Mod.USERNAME);
	private static String sgeHost = machines.get("158").get(Mod.HOST);
	private static String sgePwd = machines.get("158").get(Mod.PWD);;
	private static String sgeUserName = machines.get("158").get(Mod.USERNAME);

	/**
	 * http方式投递任务
	 * 
	 * @param appId
	 * @param list
	 * @param path
	 * @param projectId
	 */
	public static void http(Integer appId, String list, String path, Integer projectId) {
		String url = ConstantsData.getBioinfoService(appId.toString());
		if (url == null) {
			throw new BusinessException("没有找到app=" + appId + "对应的service接口，不能投递任务：projectId=" + projectId);
		}
		List<NameValuePair> params = new ArrayList<>();
		if (appId == 118) {
			params.add(new BasicNameValuePair("state", "1"));
			params.add(new BasicNameValuePair("filePath", list));
		} else {
			params.add(new BasicNameValuePair("list", list));
		}
		params.add(new BasicNameValuePair("exposePath", path));
		params.add(new BasicNameValuePair("projectID", String.valueOf(projectId)));
		logger.info("url={}?filePath={}&exposePath={}&projectID={}", url, list, path, projectId);
		HttpURLUtils.httpPostRequest(url, params);
	}

	/**
	 * ssh方式投递任务
	 * 
	 * @param target
	 * @param command
	 * @param isWait
	 */
	public static void ssh(String target, String command, boolean isWait) {
		SSHUtil ssh = null;
		target = String.valueOf(target);
		if ("sge".equals(target.toLowerCase())) {
			ssh = new SSHUtil(sgeHost, sgeUserName, sgePwd);
		} else if ("spark".equals(target.toLowerCase())) {
			ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
		} else {
			throw new BusinessException("没有找到" + target + "对应的集群，不能投递任务：command=" + command);
		}
		ssh.sshSubmit(command, isWait);
	}
}
