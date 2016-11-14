package com.celloud.action;

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

import com.celloud.model.mysql.Auth;
import com.celloud.service.ReportAPIService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

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
	@ResponseBody
	@RequestMapping(value = "getToken", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "获取Token", httpMethod = "GET", response = Auth.class, notes = "请登录平台后获取 keyId 和 keySecret ")
	@ApiResponses({ @ApiResponse(code = 200, message = "操作成功！", response = Auth.class),
			@ApiResponse(code = 400, message = "操作失败！", response = Auth.class) })
	public ResponseEntity<Auth> getToken(
			@ApiParam(required = true, name = "keyId", value = "keyId") @RequestParam("keyId") String keyId,
			@ApiParam(required = true, name = "keySecret", value = "keySecret") @RequestParam("keySecret") String keySecret) {
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
	@ApiOperation(value = "刷新Token", httpMethod = "PUT", response = Auth.class, notes = "通过 refreshToken 刷新 Token 超时时间")
	@ApiResponses({ @ApiResponse(code = 200, message = "刷新成功！", response = Auth.class),
			@ApiResponse(code = 400, message = "操作失败！", response = Auth.class) })
	public ResponseEntity<Auth> refreshToken(
			@ApiParam(required = true, name = "refreshToken", value = "refreshToken") @RequestParam("refreshToken") String refreshToken) {
		Auth auth = reportAPI.refreshToken(refreshToken);
		if (auth == null) {
			String error = "refreshToken不存在或者已过期";
			log.error(error + "：" + refreshToken);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Auth(error));
		}
		return ResponseEntity.ok(auth);
	}
}
