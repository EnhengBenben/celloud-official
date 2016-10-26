package com.celloud.box.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
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
	/**
	 * 文件往oss上传失败时，同一次上传可重试的次数
	 */
	@Min(0)
	@Max(10)
	private Integer maxRetry = 2;
	/**
	 * 已上传的文件可保存的事件
	 */
	@Min(1)
	@Max(365)
	private Integer retentionDays = 30;

	@Min(1)
	@Max(20)
	private Integer maxSplitting = 5;
	/**
	 * 盒子的序列号
	 */
	@NotBlank
	private String serialNumber;
	@Value("${info.build.version}")
	private String version;
	@Value("${info.build.appName}")
	private String appName;
	@Value("${info.build.artifact}")
	private String artifact;
	@Value("${server.port}")
	private Integer port;
	@Value("${split.command}")
	private String splitCommand;

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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getSplitCommand() {
		return splitCommand;
	}

	public void setSplitCommand(String splitCommand) {
		this.splitCommand = splitCommand;
	}

	public Integer getMaxSplitting() {
		return maxSplitting;
	}

	public void setMaxSplitting(Integer maxSplitting) {
		this.maxSplitting = maxSplitting;
	}

}
