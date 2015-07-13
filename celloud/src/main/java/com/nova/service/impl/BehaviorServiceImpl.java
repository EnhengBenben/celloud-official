package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IBehaviorDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Behavior;
import com.nova.service.IBehaviorService;

public class BehaviorServiceImpl implements IBehaviorService {
	@Inject
	private IBehaviorDao behaviorDao;
	
	@Override
	public int logLoginInfo(Behavior behavior) {
		return behaviorDao.logLoginInfo(behavior);
	}
	
	@Override
	public PageList<Behavior> getUserLogInfo(Page page) {
		return behaviorDao.getUserLogInfo(page);
	}

	@Override
	public List<Behavior> getBehaviorList(String date) {
		return behaviorDao.getBehaviorList(date);
	}
}
