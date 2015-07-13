package com.nova.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.nova.constants.Constant;
import com.nova.dao.IAppDao;
import com.nova.sdo.App;
import com.nova.service.IAppService;

public class AppServiceImpl implements IAppService {
	@Inject
	private IAppDao appDao;

	@Override
	public List<App> getAll(int userId) {
		return appDao.getAll(userId);
	}

	@Override
	public List<List<App>> getApp(int userId) {
		List<List<App>> list = new ArrayList<List<App>>();
		for (int i = 1; i <= Constant.dESK_NUM; i++) {
			list.add(appDao.getAll(userId, i));
		}
		return list;
	}

	@Override
	public boolean updateDesk(int userId, int deskNo, int oldDeskNo, int appId) {
		return appDao.updateDesk(userId, deskNo, oldDeskNo, appId);
	}

	@Override
	public boolean deleteAppFromDesk(int userId, int deskNo, int appId) {
		return appDao.deleteAppFromDesk(userId, deskNo, appId);
	}

}
