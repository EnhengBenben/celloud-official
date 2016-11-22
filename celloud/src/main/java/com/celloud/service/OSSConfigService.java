package com.celloud.service;

import com.celloud.model.mysql.OSSConfig;

/**
 * oss配置的服务接口
 * 
 * @author sun8wd
 *
 */
public interface OSSConfigService {
	/**
	 * 获取最新的一条可用配置
	 * 
	 * @return
	 */
	OSSConfig getLatest();

	/**
	 * 自动更新当前可用的oss配置
	 */
	void refreshConfig();
}
