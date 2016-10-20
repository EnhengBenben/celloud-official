package com.celloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.BoxConfigMapper;
import com.celloud.model.mysql.BoxConfig;
import com.celloud.service.BoxConfigService;
import com.celloud.utils.CustomStringUtils;

@Service
public class BoxConfigServiceIpml implements BoxConfigService {
	@Resource
	private BoxConfigMapper mapper;

	@Override
	public List<BoxConfig> selectByUserId(Integer userId, String ip) {
		return mapper.selectByUserId(userId, ip);
	}

	@Override
	public boolean updateBoxHealth(String serialNumber, String version, String ip, String exIp) {
		BoxConfig config = mapper.selectBySerialNumber(serialNumber);
		if (config == null) {
			return false;
		}
		if (CustomStringUtils.equals(version, config.getVersion())
				&& CustomStringUtils.equals(exIp, config.getExtranetAddress())
				&& CustomStringUtils.equals(ip, config.getIntranetAddress())) {
			// 参数一致则不修改数据库，减轻数据库压力
			return true;
		}
		config.setExtranetAddress(exIp);
		config.setIntranetAddress(ip);
		config.setLastAlive(new Date());
		config.setSerialNumber(serialNumber);
		config.setVersion(version);
		return mapper.updateBoxHealth(config) > 0;
	}

}
