package com.celloud.box.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.celloud.box.config.APIConfig;
import com.celloud.box.config.BoxConfig;
import com.celloud.box.constants.Constants;
import com.celloud.box.utils.LocalIpAddressUtil;
import com.celloud.box.utils.UploadPath;

@Component
public class ApplicationStartup implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
	@Resource
	private BoxConfig boxConfig;
	@Resource
	private APIConfig apiConfig;
	@Value("${spring.profiles.active:dev}")
	private String env;

	@Override
	public void run(String... arg0) throws Exception {
		UploadPath.setRootPath(boxConfig.getUploadPath());
		Constants.env = env;
		logger.info(UploadPath.getUploadedPath(12));
		logger.info(UploadPath.getUploadingPath(12));
		logger.info("********************* System Properties *********************");
		logger.info("");
		logger.info("box.environment   =  {}", Constants.env);
		logger.info("box.uploadPath    =  {}", boxConfig.getUploadPath());
		logger.info("box.localIp       =  {}", LocalIpAddressUtil.getLocalArress(boxConfig.getNetwork()));
		logger.info("box.maxRetry      =  {}", boxConfig.getMaxRetry());
		logger.info("box.maxUploading  =  {}", boxConfig.getMaxUploading());
		logger.info("box.retentionDays =  {}", boxConfig.getRetentionDays());
		logger.info("");
		logger.info("api.newfile       =  {}", apiConfig.getNewfile());
		logger.info("api.updatefile    =  {}", apiConfig.getUpdatefile());
		logger.info("");
		logger.info("*************************************************************");
	}

}
