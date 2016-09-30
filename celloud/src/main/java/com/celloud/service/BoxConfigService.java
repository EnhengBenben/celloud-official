package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.BoxConfig;

public interface BoxConfigService {
	public List<BoxConfig> selectByUserId(Integer userId);
}
