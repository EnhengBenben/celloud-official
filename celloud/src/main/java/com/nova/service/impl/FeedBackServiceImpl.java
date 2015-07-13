package com.nova.service.impl;

import com.google.inject.Inject;
import com.nova.dao.IFeedBackDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.FeedBack;
import com.nova.service.IFeedBackService;

public class FeedBackServiceImpl implements IFeedBackService {

	@Inject
	private IFeedBackDao fbDao;

	@Override
	public PageList<FeedBack> selectAllFeedBack(Page page) {
		return fbDao.selectAllFeedBack(page);
	}

	@Override
	public boolean saveFeedBack(FeedBack fb) {
		return fbDao.saveFeedBack(fb);
	}
}
