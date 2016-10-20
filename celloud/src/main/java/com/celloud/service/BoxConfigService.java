package com.celloud.service;

import java.util.List;

import com.celloud.model.mysql.BoxConfig;

public interface BoxConfigService {
	/**
	 * 查找用户配置的盒子
	 * 
	 * @param userId
	 * @param ip
	 * @return
	 */
	public List<BoxConfig> selectByUserId(Integer userId, String ip);

	/**
	 * 更新盒子的健康状态
	 * 
	 * @param serialNumber
	 * @param version
	 * @param ip
	 * @param exIp
	 * @param port
	 * @return
	 */
	public boolean updateBoxHealth(String serialNumber, String version, String ip, String exIp, Integer port);

	/**
	 * 校验盒子的身份是否正确
	 * 
	 * @param serialNumber
	 * @param version
	 * @param ip
	 * @param extranet
	 * @param port
	 */
	public boolean checkConfig(String serialNumber, String version, String ip, String extranet, Integer port);
}
