package com.celloud.service;

import com.celloud.model.mysql.AccessKey;
import com.celloud.model.mysql.Auth;

public interface ReportAPIService {
	/**
	 * 创建随机 celloudId、celloudSecret
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月3日下午3:00:50
	 */
	public AccessKey createAccount(Integer userId);

	/**
	 * 获取token
	 * 
	 * @param keyId
	 * @param keySecret
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午4:12:44
	 */
	public Auth getToken(String keyId, String keySecret);

	/**
	 * 刷新token
	 * 
	 * @param refreshToken
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午4:12:55
	 */
	public Auth refreshToken(String refreshToken);
}
