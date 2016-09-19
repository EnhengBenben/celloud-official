package com.celloud.box.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "box")
@Component
public class BoxConfig {
	/**
	 * 文件上传路径
	 */
	private String uploadPath;
	/**
	 * 当前的运行环境：<br />
	 * dev=开发环境
	 * test=测试环境
	 * prod=生产环境
	 */
	private String env;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

}
