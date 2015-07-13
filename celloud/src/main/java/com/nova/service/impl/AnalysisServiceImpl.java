package com.nova.service.impl;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.nova.dao.IAnalysisDao;
import com.nova.service.IAnalysisService;

public class AnalysisServiceImpl implements IAnalysisService {
	@Inject
	private IAnalysisDao analysis;

	@Override
	public List<Map<String, String>> getUserList(Integer companyId) {
		return analysis.getUserList(companyId);
	}

	@Override
	public List<Map<String, String>> getUserDataList(Integer companyId) {
		return analysis.getUserDataList(companyId);
	}

	@Override
	public List<Map<String, String>> getUserData(Integer userId,
			Integer companyId) {
		return analysis.getUserData(userId, companyId);
	}

	@Override
	public List<Map<String, String>> getDataList(Integer userId, String mounth,
			Integer companyId) {
		return analysis.getDataList(userId, mounth, companyId);
	}
}