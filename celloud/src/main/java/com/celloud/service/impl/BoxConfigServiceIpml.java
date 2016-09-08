package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.BoxConfigMapper;
import com.celloud.model.mysql.BoxConfig;
import com.celloud.service.BoxConfigService;

@Service
public class BoxConfigServiceIpml implements BoxConfigService {
	@Resource
	private BoxConfigMapper mapper;

	@Override
	public List<BoxConfig> selectByUserId(Integer userId) {
		return mapper.selectByUserId(userId);
	}

}
