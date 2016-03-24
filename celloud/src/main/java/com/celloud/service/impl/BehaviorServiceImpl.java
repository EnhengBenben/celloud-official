package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.celloud.dao.ReportDao;
import com.celloud.model.mongo.Behavior;
import com.celloud.service.BehaviorService;

@Service("behaviorService")
public class BehaviorServiceImpl implements BehaviorService{
	@Resource
	ReportDao reportDao;

	@Override
	@Async
	public void saveUserBehavior(Behavior ub) {
		reportDao.saveData(ub);;
	}

}
