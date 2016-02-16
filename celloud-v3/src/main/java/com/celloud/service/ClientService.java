package com.celloud.service;

import com.celloud.model.Client;

public interface ClientService {

	/**
	 * 获取最新的客户端版本
	 * 
	 * @return
	 * @author lin
	 * @date 2016年1月31日下午10:07:27
	 */
	public Client getLast();
}
