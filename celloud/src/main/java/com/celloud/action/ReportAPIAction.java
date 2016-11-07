package com.celloud.action;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.service.ReportAPIService;

@Controller
@RequestMapping("api/report")
public class ReportAPIAction {
	Logger log = LoggerFactory.getLogger(AppAction.class);
	@Resource
	private ReportAPIService reportAPI;

	//TODO create方法要删除
	@RequestMapping("create")
	@ResponseBody
	public Map<String, String> create() {
		return reportAPI.createAccount();
	}

	/**
	 * 获取token
	 * 
	 * @param id
	 * @param scret
	 * @return
	 * @author lin
	 * @date 2016年11月3日下午3:09:10
	 */
	@RequestMapping(value = "getToken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getToken(String id, String scret) {
		return null;
	}

	@RequestMapping(value = "refreshToken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> refreshToken(String token) {
		return null;
	}

}
