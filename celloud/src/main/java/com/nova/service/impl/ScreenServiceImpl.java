package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IScreenDao;
import com.nova.sdo.Screen;
import com.nova.service.IScreenService;

public class ScreenServiceImpl implements IScreenService {

	@Inject
	private IScreenDao screenDao;

	@Override
	public int createScreen(String screenName, int softwareId) {
		return screenDao.createScreen(screenName, softwareId);
	}

	@Override
	public int deleteScreen(int softwareId) {
		return screenDao.deleteScreen(softwareId);
	}

	@Override
	public List<Screen> getAllScreen(int softId) {
		return screenDao.getAllScreen(softId);
	}

}
