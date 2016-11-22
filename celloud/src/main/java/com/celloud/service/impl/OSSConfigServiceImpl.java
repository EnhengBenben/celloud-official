package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.celloud.constants.ConstantsData;
import com.celloud.mapper.OSSConfigMapper;
import com.celloud.model.mysql.OSSConfig;
import com.celloud.service.OSSConfigService;

/**
 * oss配置的服务接口实现
 * 
 * @author sun8wd
 *
 */
@Service
public class OSSConfigServiceImpl implements OSSConfigService {
	@Resource
	private OSSConfigMapper mapper;

	@Override
	public OSSConfig getLatest() {
		return mapper.getLatest();
	}

	@Async
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	@Override
	public void refreshConfig() {
		ConstantsData.setOSSConfig(getLatest());
	}

}
