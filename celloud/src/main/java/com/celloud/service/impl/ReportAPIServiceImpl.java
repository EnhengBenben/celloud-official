package com.celloud.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.model.mysql.AccessKey;
import com.celloud.model.mysql.Auth;
import com.celloud.service.AccessKeyService;
import com.celloud.service.AuthService;
import com.celloud.service.ReportAPIService;
import com.celloud.utils.APIUtils;

@Service
public class ReportAPIServiceImpl implements ReportAPIService {

	@Resource
	private AccessKeyService accessKeyService;
	@Resource
	private AuthService authService;

	@Override
	public AccessKey createAccount(Integer userId) {
		String keyId = APIUtils.getCelLoudId();
		String keySecret = APIUtils.getCelLoudSecret();
		AccessKey accessKey = new AccessKey();
		accessKey.setUserId(userId);
		accessKey.setCreateDate(new Date());
		accessKey.setKeyId(keyId);
		accessKey.setKeySecret(keySecret);
		accessKeyService.insertSelective(accessKey);
		return accessKey;
	}

	@Override
	public Auth getToken(String keyId, String keySecret) {
		AccessKey accessKey = accessKeyService.selectByIdAndSecret(keyId, keySecret);
		if (accessKey == null)
			return null;
		Calendar rightNow = Calendar.getInstance();
		Date createDate = rightNow.getTime();
		rightNow.add(Calendar.HOUR, 1);
		Date tokenExpireDate = rightNow.getTime();
		rightNow.add(Calendar.HOUR, 1);
		Date refreshTokenExpireDate = rightNow.getTime();

		String refreshToken = APIUtils.getRefreshToken();
		String token = APIUtils.getToken();

		Auth auth = new Auth();
		auth.setAccessKeyId(accessKey.getId());
		auth.setCreateDate(createDate);
		auth.setToken(token);
		auth.setTokenExpireDate(tokenExpireDate);
		auth.setRefreshToken(refreshToken);
		auth.setRefreshTokenExpireDate(refreshTokenExpireDate);
		auth.setUserId(accessKey.getUserId());
		authService.insertSelective(auth);
		return auth;
	}

	@Override
	public Auth refreshToken(String refreshToken) {
		Auth auth = authService.selectByRefreshToken(refreshToken);
		if (auth == null)
			return null;
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.HOUR, 1);
		Date tokenExpireDate = rightNow.getTime();
		rightNow.add(Calendar.HOUR, 1);
		Date refreshTokenExpireDate = rightNow.getTime();
		auth.setTokenExpireDate(tokenExpireDate);
		auth.setRefreshTokenExpireDate(refreshTokenExpireDate);
		authService.updateByPrimaryKeySelective(auth);
		return auth;
	}
}
