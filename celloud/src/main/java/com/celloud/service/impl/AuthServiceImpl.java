package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.AuthMapper;
import com.celloud.model.mysql.Auth;
import com.celloud.service.AuthService;

@Service("authServiceImpl")
public class AuthServiceImpl implements AuthService {

	@Resource
	private AuthMapper authMapper;

	@Override
	public int insertSelective(Auth auth) {
		return authMapper.insertSelective(auth);
	}

	@Override
	public int updateByPrimaryKeySelective(Auth auth) {
		return authMapper.updateByPrimaryKeySelective(auth);
	}

	@Override
	public Auth selectByRefreshToken(String refreshToken) {
		return authMapper.selectByRefreshToken(refreshToken);
	}

	@Override
	public Auth getByToken(String token) {
		return authMapper.getByToken(token);
	}

}
