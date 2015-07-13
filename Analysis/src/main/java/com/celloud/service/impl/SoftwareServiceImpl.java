package com.celloud.service.impl;

import com.celloud.dao.SoftwareDao;
import com.celloud.service.SoftwareService;
import com.google.inject.Inject;

public class SoftwareServiceImpl implements SoftwareService {
	@Inject
	private SoftwareDao softwareDao;
	@Override
	public Object getBigUserAPPNum(Integer companyId) {
		return softwareDao.getBigUserAPPNum(companyId);
	}

}
