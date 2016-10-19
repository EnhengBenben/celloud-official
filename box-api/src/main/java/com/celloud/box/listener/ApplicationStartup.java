package com.celloud.box.listener;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.celloud.box.config.APIConfig;
import com.celloud.box.config.BoxConfig;
import com.celloud.box.constants.Constants;
import com.celloud.box.service.ApiService;
import com.celloud.box.service.BoxService;
import com.celloud.box.service.CleanService;
import com.celloud.box.utils.LocalIpAddressUtil;
import com.celloud.box.utils.UploadPath;

@Component
public class ApplicationStartup implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
	@Resource
	private BoxConfig boxConfig;
	@Resource
	private APIConfig apiConfig;
	@Resource
	private CleanService cleanService;
	@Resource
	private BoxService boxService;
	@Resource
	private ApiService apiService;
	@Value("${spring.profiles.active:dev}")
	private String env;

	@Override
	public void run(String... arg0) throws Exception {
		UploadPath.setRootPath(boxConfig.getUploadPath());
		Constants.env = env;
		String ip = LocalIpAddressUtil.getLocalArress(boxConfig.getNetwork());
		if (ip == null) {
			logger.error("********************* (╯﹏╰) *********************");
			Map<InetAddress, NetworkInterface> interfaces = LocalIpAddressUtil.resolveLocalNetworks();
			logger.error("没有获取到本机ip地址，请根据以下信息指定一个网卡名称：");
			logger.error("+++++++++++++++++++++++++++++++++++++++++++++++++++");
			for (Map.Entry<InetAddress, NetworkInterface> entry : interfaces.entrySet()) {
				InetAddress address = entry.getKey();
				NetworkInterface n = entry.getValue();
				logger.error("{} : {}", n.getName(), address.getHostAddress());
				logger.error("        {}", n.getDisplayName());
				logger.error("↑使用启动参数：--network={} 指定这个网卡", n.getName());
				logger.error("+++++++++++++++++++++++++++++++++++++++++++++++++++");
			}
			logger.error("***************************************************");
			System.exit(0);
		}
		logger.info("********************* System Properties *********************");
		logger.info("");
		logger.info("box.environment   =  {}", Constants.env);
		logger.info("box.uploadPath    =  {}", boxConfig.getUploadPath());
		logger.info("box.localIp       =  {}", ip);
		logger.info("box.maxRetry      =  {}", boxConfig.getMaxRetry());
		logger.info("box.maxUploading  =  {}", boxConfig.getMaxUploading());
		logger.info("box.retentionDays =  {}", boxConfig.getRetentionDays());
		logger.info("");
		logger.info("api.newfile       =  {}", apiConfig.getNewfile());
		logger.info("api.updatefile    =  {}", apiConfig.getUpdatefile());
		logger.info("api.reportHealth  =  {}", apiConfig.getReportHealth());
		logger.info("");
		logger.info("*************************************************************");
		cleanService.clean();
		boxService.loadUnUploadedFiles();
		apiService.reportHealth();
	}

}
