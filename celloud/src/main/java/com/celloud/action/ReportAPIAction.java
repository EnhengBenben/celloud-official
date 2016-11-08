package com.celloud.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.model.mysql.Auth;
import com.celloud.service.ReportAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/report")
public class ReportAPIAction {
	Logger log = LoggerFactory.getLogger(AppAction.class);
	@Resource
	private ReportAPIService reportAPI;

	/**
	 * 获取token
	 * 
	 * @param keyId
	 * @param keySecret
	 * @return
	 * @author lin
	 * @date 2016年11月3日下午3:09:10
	 */
	@RequestMapping(value = "getToken")
	@ResponseBody
	public String getToken(String keyId, String keySecret) {
		Auth auth = reportAPI.getToken(keyId, keySecret);
		if (auth == null) {
			log.error("没有找到对应的keyId和keySecret，keyId=" + keyId + ",keySecret=" + keySecret);
		}
		return JSONObject.fromObject(auth).toString();
	}

	/**
	 * 刷新token超时时间
	 * 
	 * @param refreshToken
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午3:17:20
	 */
	@RequestMapping(value = "refreshToken")
	@ResponseBody
	public String refreshToken(String refreshToken) {
		Auth auth = reportAPI.refreshToken(refreshToken);
		if (auth == null) {
			log.error("refreshToken不存在或者已过期：" + refreshToken);
			return null;
		}
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(auth);
		} catch (JsonProcessingException e) {
			log.error("对象转json失败：" + auth.toString());
		}
		return null;
	}
}
