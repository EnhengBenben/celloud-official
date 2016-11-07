package com.celloud.service;

import java.util.Map;

public interface ReportAPIService {
	/**
	 * 创建随机 celloudId、celloudSecret
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月3日下午3:00:50
	 */
	public Map<String, String> createAccount();
}
