package com.celloud.service;

import com.celloud.model.mysql.Auth;

public interface AuthService {

	/**
	 * 新增
	 * 
	 * @param auth
	 * @return
	 * @author lin
	 * @date 2016年11月7日下午2:21:34
	 */
	int insertSelective(Auth auth);

	int updateByPrimaryKeySelective(Auth auth);

	Auth selectByRefreshToken(String refreshToken);

}