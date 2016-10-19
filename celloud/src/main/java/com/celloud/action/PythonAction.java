package com.celloud.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.service.PythonService;
import com.celloud.utils.ActionLog;

@Controller
@RequestMapping("api/python")
public class PythonAction {
	Logger log = LoggerFactory.getLogger(PythonAction.class);
	@Resource
	private PythonService pythonService;

	/**
	 * python 客户端登录
	 * 
	 * @return
	 */
    @ActionLog(value = "python 客户端登录", button = "客户端登录")
	@ResponseBody
	@RequestMapping(value = "/login/{username}/{pwd}")
	public String login(@PathVariable("username") String username, @PathVariable("pwd") String pwd) {
		return pythonService.login(username, pwd);
	}

	/**
	 * 获取用户上传文件的总大小
	 * 
	 * @return
	 */
    @ActionLog(value = "获取用户上传文件的总大小", button = "客户端上传")
	@ResponseBody
	@RequestMapping(value = "/getSize/{userId}")
	public Long getSize(@PathVariable("userId") Integer userId) {
		return pythonService.getSize(userId);
	}

	/**
	 * 获取用户上传文件的总个数
	 * 
	 * @return
	 */
    @ActionLog(value = "获取用户上传文件的总个数", button = "客户端上传")
	@ResponseBody
	@RequestMapping(value = "/getNumber/{userId}")
	public Long getNumber(@PathVariable("userId") Integer userId) {
		return pythonService.getNumber(userId);
	}

	/**
	 * 文件上传前获取 DataKey
	 * 
	 * @return
	 */
    @ActionLog(value = "文件上传前获取 DataKey", button = "客户端上传")
	@ResponseBody
	@RequestMapping(value = "/initDataKey/{userId}/{fileName}/{md5}")
	public String initDataKey(@PathVariable("userId") Integer userId, @PathVariable("fileName") String fileName,
			@PathVariable("md5") String md5) {
		return pythonService.getDataKey(userId, fileName, md5);
	}

	/**
	 * 文件上传结束后保存进数据库
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploaded/{dataKey}")
	public String uploaded(@PathVariable("dataKey") String dataKey) {
		return pythonService.uploaded(dataKey);
	}

	/**
	 * 获取客户端版本
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getClientVersion")
	public String getClientVersion() {
		return pythonService.getClientVersion();
	}

	/**
	 * 断点续传时获取文件大小
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDataSize/{userId}/{dataKey}")
	public long getDataSize(@PathVariable("userId") Integer userId, @PathVariable("dataKey") String dataKey) {
		return pythonService.getDataSize(userId, dataKey.split("\\.")[0]);
	}

	/**
	 * 保存已上传的文件大小
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDataSize/{dataKey}/{num}")
	public long saveDataSize(@PathVariable("dataKey") String dataKey, @PathVariable("num") Long num) {
		return pythonService.saveDataSize(dataKey.split("\\.")[0], num);
	}

	/**
	 * 文件上传完成发送邮件
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEmail/{userId}/{fileName}/{dataKey}")
	public String sendEmail(@PathVariable("userId") Integer userId, @PathVariable("fileName") String fileName,
			@PathVariable("dataKey") String dataKey) {
		pythonService.sendEmail(userId, fileName, dataKey);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/threadUpdate/{userId}/{dataKey}/{num}/{position}")
	public Integer threadUpdate(@PathVariable("userId") Integer userId, @PathVariable("dataKey") String dataKey,
			@PathVariable("num") Long num, @PathVariable("position") Long position) {
		return pythonService.threadUpdate(userId, dataKey, num, position);
	}

	@ResponseBody
	@RequestMapping(value = "/threadRead/{userId}/{dataKey}")
	public String threadRead(@PathVariable("userId") Integer userId, @PathVariable("dataKey") String dataKey) {
		return pythonService.threadRead(userId, dataKey);
	}
}
