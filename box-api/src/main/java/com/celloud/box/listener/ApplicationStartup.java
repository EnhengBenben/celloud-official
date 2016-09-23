package com.celloud.box.listener;

import java.net.InetAddress;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.utils.LocalIpAddressUtil;
import com.celloud.box.utils.UploadPath;

@Component
public class ApplicationStartup implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
	@Resource
	private BoxConfig config;

	@Override
	public void run(String... arg0) throws Exception {
		UploadPath.setRootPath(config.getUploadPath());
		logger.info("********************* System Properties *********************");
		logger.info("");
		logger.info("box.env        =  {}", config.getEnv());
		logger.info("box.uploadPath =  {}", config.getUploadPath());
		Map<InetAddress, String> map = LocalIpAddressUtil.resolveLocalNetworks();
		for (InetAddress address : map.keySet()) {
			logger.info("local ips      =  {} : {}", map.get(address), address.getHostAddress());
		}
		logger.info("");
		logger.info("*************************************************************");
	}

}
