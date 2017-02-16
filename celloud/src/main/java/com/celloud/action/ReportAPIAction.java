package com.celloud.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mongo.BSI;
import com.celloud.model.mongo.Rocky;
import com.celloud.model.mysql.Auth;
import com.celloud.model.mysql.Report;
import com.celloud.service.AppService;
import com.celloud.service.AuthService;
import com.celloud.service.ReportAPIService;
import com.celloud.service.ReportService;

@Controller
@RequestMapping("api/report")
public class ReportAPIAction {
	Logger log = LoggerFactory.getLogger(AppAction.class);
	@Resource
	private ReportAPIService reportAPI;
	@Resource
	private ReportService reportService;
	@Resource
	private AuthService authService;
	@Resource
	private AppService appService;

	/**
	 * 获取token
	 * 
	 * @param keyId
	 * @param keySecret
	 * @return
	 * @author lin
	 * @date 2016年11月3日下午3:09:10
	 */
	@ResponseBody
	@RequestMapping(value = "token", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Auth> token(@RequestParam("keyId") String keyId,
			@RequestParam("keySecret") String keySecret) {
		Auth auth = reportAPI.getToken(keyId, keySecret);
		if (auth == null) {
			String error = "没有找到对应的keyId和keySecret";
			log.error(error + "，keyId=" + keyId + ",keySecret=" + keySecret);
			auth = new Auth(error);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(auth);
		}
		return ResponseEntity.ok(auth);
	}

	/**
	 * 刷新token超时时间
	 * 
	 * @param refreshToken
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午3:17:20
	 */
	@RequestMapping(value = "refreshToken", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
	public ResponseEntity<Auth> refreshToken(@RequestParam("refreshToken") String refreshToken) {
		Auth auth = reportAPI.refreshToken(refreshToken);
		if (auth == null) {
			String error = "refreshToken不存在或者已过期";
			log.error(error + "：" + refreshToken);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Auth(error));
		}
		return ResponseEntity.ok(auth);
	}

	@RequestMapping(value = "rocky", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> rocky(
			@RequestParam("dataKey") String dataKey, @RequestParam("projectId") Integer projectId,
			@RequestParam("appId") Integer appId) {
		Rocky rocky = reportService.getRockyReport(dataKey, projectId, appId);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("rocky", rocky);
        context.put("rockyResult", "/rockyResult/");
		context.put("significances", ConstantsData.significances());
		return context;
	}

	@RequestMapping(value = "bsi", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> bsi(@RequestParam("token") String token,
			@RequestParam("dataKey") String dataKey) {
		Map<String, Object> context = new HashMap<String, Object>();
		Auth auth = authService.getByToken(token);
		if (auth == null) {
			context.put("message", "无效的token");
			return context;
		}
		Integer userId = auth.getUserId();
		Report report = reportService.getLastDataReport(dataKey, userId, 118);
		if (report == null) {
			context.put("message", "没有此报告");
			return context;
		}
		BSI bsi = reportService.getBSIReport(dataKey, report.getProjectId(), report.getAppId());
		context.put("bsi", bsi);
		return context;
	}
}
