package com.celloud.box.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
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
	 * 网卡名称
	 */
	@NotBlank(message = "网卡的名字不能为null")
	private String network;
	/**
	 * 盒子往oss上传时，最大可同时上传的文件数量
	 */
	@Min(1)
	@Max(10)
	private Integer maxUploading = 3;
	@Min(0)
	@Max(10)
	private Integer maxRetry = 2;
	@Min(1)
	@Max(365)
	private Integer retentionDays = 30;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Integer getMaxUploading() {
		return maxUploading;
	}

	public void setMaxUploading(Integer maxUploading) {
		this.maxUploading = maxUploading;
	}

	public Integer getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(Integer maxRetry) {
		this.maxRetry = maxRetry;
	}

	public Integer getRetentionDays() {
		return retentionDays;
	}

	public void setRetentionDays(Integer retentionDays) {
		this.retentionDays = retentionDays;
	}

}
