package com.celloud.box.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api")
@Component
public class APIConfig {
	private String newfile;
	private String updatefile;
	private String reportHealth;
	private String ossConfig;
	private String splittxt;

	public String getNewfile() {
		return newfile;
	}

	public void setNewfile(String newfile) {
		this.newfile = newfile;
	}

	public String getUpdatefile() {
		return updatefile;
	}

	public void setUpdatefile(String updatefile) {
		this.updatefile = updatefile;
	}

	public String getReportHealth() {
		return reportHealth;
	}

	public void setReportHealth(String reportHealth) {
		this.reportHealth = reportHealth;
	}

	public String getOssConfig() {
		return ossConfig;
	}

	public void setOssConfig(String ossConfig) {
		this.ossConfig = ossConfig;
	}

	public String getSplittxt() {
		return splittxt;
	}

	public void setSplittxt(String splittxt) {
		this.splittxt = splittxt;
	}

}
