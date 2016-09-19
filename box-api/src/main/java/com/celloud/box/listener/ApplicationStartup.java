package com.celloud.box.listener;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.utils.UploadPath;

@Component
public class ApplicationStartup implements CommandLineRunner {
	@Resource
	private BoxConfig config;

	@Override
	public void run(String... arg0) throws Exception {
		UploadPath.setRootPath(config.getUploadPath());
	}

}
