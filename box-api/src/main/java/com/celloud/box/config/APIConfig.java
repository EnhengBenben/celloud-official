package com.celloud.box.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api")
@Component
public class APIConfig {
	private String newfile;
	private String updatefile;

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

}
