package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.AccessKeyMapper;
import com.celloud.model.mysql.AccessKey;
import com.celloud.service.AccessKeyService;

@Service("accessKeyServiceImpl")
public class AccessKeyServiceImpl implements AccessKeyService {
	@Resource
	private AccessKeyMapper akm;

	@Override
	public int insertSelective(AccessKey record) {
		return akm.insert(record);
	}

	@Override
	public AccessKey selectByUserId(Integer userId) {
		return akm.selectByUserId(userId);
	}

	@Override
	public AccessKey selectByIdAndSecret(String keyId, String keySecret) {
		return akm.selectByIdAndSecret(keyId, keySecret);
	}

	@Override
	public int updateByPrimaryKeySelective(AccessKey record) {
		return akm.updateByPrimaryKeySelective(record);
	}

}
