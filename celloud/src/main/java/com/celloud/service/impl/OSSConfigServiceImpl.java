package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

}
